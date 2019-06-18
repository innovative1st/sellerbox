package com.seller.box.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.seller.box.config.EdiConfig;
import com.seller.box.core.OC;
import com.seller.box.core.OCItem;
import com.seller.box.core.OutboundMessage;
import com.seller.box.core.ServiceResponse;
import com.seller.box.dao.EdiConfigDao;
import com.seller.box.dao.EdiShipmentDao;
import com.seller.box.dao.EdiShipmentOCDoa;
import com.seller.box.dao.EdiShipmentOcrDao;
import com.seller.box.dao.ProductSearchDao;
import com.seller.box.entities.EdiShipmentHdr;
import com.seller.box.entities.EdiShipmentItems;
import com.seller.box.entities.EdiShipmentOc;
import com.seller.box.entities.EdiShipmentOcItems;
import com.seller.box.entities.EdiShipmentOcr;
import com.seller.box.entities.ProductDetails;
import com.seller.box.form.EdiInventoryIanForm;
import com.seller.box.service.InventoryService;
import com.seller.box.service.OrderCancellationService;
import com.seller.box.utils.KafkaUtils;
import com.seller.box.utils.SBConstant;
import com.seller.box.utils.SBUtils;

@Service
public class OrderCancellationServiceImpl implements OrderCancellationService {
	private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);
	@Autowired
	KafkaUtils kafkaUtils;
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	EdiConfigDao ediConfigDao;
	@Autowired
	ProductSearchDao productSearchDao;
	@Autowired
	EdiShipmentOCDoa ediShipmentOCDoa;
	@Autowired
	EdiShipmentDao ediShipmentDoa;
	@Autowired
	EdiShipmentOcrDao ediShipmentOcrDao;
	@Autowired
	InventoryService inventoryService;
	
	
	@Override
	@Async
	public void createOrderCancellationAsync(String requestId, OC oc, String vendorPartyId, int etailorId, String user) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrderCancellationAsync(String requestId, OC oc, String orderType, int etailorId, String user)---START");
		ServiceResponse response = processOC(requestId, oc, vendorPartyId, etailorId, user);
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrderCancellationAsync()---->"+response.toString());
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrderCancellationAsync(String requestId, OC oc, String orderType, int etailorId, String user)---END");
	}

	@Override
	public ServiceResponse createOrderCancellation(String requestId, OC oc, String vendorPartyId, int etailorId, String user) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrderCancellation(String requestId, OC oc, String vendorPartyId, int etailorId, String user)---START");
		ServiceResponse response = processOC(requestId, oc, vendorPartyId, etailorId, user);
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrderCancellation(String requestId, OC oc, String vendorPartyId, int etailorId, String user)---END");
		return response;
	}

	private ServiceResponse processOC(String requestId, OC oc, String vendorPartyId, int etailorId, String user) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "processOC(String requestId, OC oc, String vendorPartyId, int etailorId, String user)---START");
		ServiceResponse response = new ServiceResponse();
		response.setRequestId(requestId);
		EdiShipmentOc xoc = null;
		if(oc != null) {
			try {
				xoc = new EdiShipmentOc();
				//xoc.setOcId(ocId);
				xoc.setEretailorId(etailorId);
				xoc.setCustomerOrderNumber(oc.getCustomerOrderNumber());
				xoc.setPurchaseOrderNumber(oc.getPurchaseOrderNumber());
				xoc.setCancelRequestType(oc.getCancelRequestType());
				xoc.setWarehouseLocationId(oc.getWarehouseLocationId());
				xoc.setVendorPartyType("BUYER_ASSIGNED");
				xoc.setVendorPartyId(vendorPartyId);
				//xoc.setEdiOrderId(ediOrderId);
				xoc.setIsCancelDone(0);
				xoc.setIsIanDone(0);
				xoc.setIsOcrDone(0);
				xoc.setOcFilePath(oc.getOcFilePath());
				//xoc.setResponseCondition(responseCondition);
				//xoc.setResultCode(resultCode);
				xoc.setCreatedBy(user);
				xoc.setCreatedDate(new Date());
				xoc.setProcessInstanceId(requestId);
				if (oc.getOcItems() != null) {
					if(oc.getOcItems().size() > 0) {
						Set<EdiShipmentOcItems> ocItems = new HashSet<EdiShipmentOcItems>();
						for(OCItem oci : oc.getOcItems()) {
							EdiShipmentOcItems item = new EdiShipmentOcItems();
							String value = oci.getSkuCode();
							if(SBUtils.isNull(value) && SBUtils.isNotNull(oci.getEan())) {
								value = oci.getEan();
							}
							if(SBUtils.isNull(value) && SBUtils.isNotNull(oci.getFnSku())) {
								value = oci.getFnSku();
							}
							if(SBUtils.isNull(value) && SBUtils.isNotNull(oci.getItemId())) {
								value = oci.getItemId();
							}
							if(SBUtils.isNotNull(value)) {
								ProductDetails product = productSearchDao.findProduct(etailorId, value);
								if(product != null) {
									item.setSkuCode(product.getSkuCode());
									if (SBUtils.isNotNull(product.getEan())) {
										try {
											item.setEan(Long.parseLong(product.getEan()));
										} catch (Exception e) {
											//TODO
										}
									}
									item.setFnSku(product.getFnsku());
									item.setItemId(product.getItemId());
									item.setProductName(product.getProductName());
									item.setThumbnailUrl(product.getThumbnailUrl());
								} else {
									item.setSkuCode(oci.getSkuCode());
									if (SBUtils.isNotNull(oci.getEan())) {
										try {
											item.setEan(Long.parseLong(oci.getEan()));
										} catch (Exception e) {
											//TODO
										}
									}
									item.setFnSku(oci.getFnSku());
									item.setItemId(oci.getItemId());
									item.setProductName(oci.getProductName());
									item.setThumbnailUrl(oci.getThumbnailUrl());
								} 
							}
							if (SBUtils.isNotNull(oci.getLineItemSequenceNumber())) {
								try {
									item.setLineItemSeq(Integer.parseInt(oci.getLineItemSequenceNumber()));
								} catch (Exception e) {
									//TODO
								}
							}
							item.setItemType(oci.getItemType());
							item.setQuantity(oci.getQuantityToCancel());
							item.setWarehouseLocationId(oc.getWarehouseLocationId());
							ocItems.add(item);
						}
						xoc.setEdiShipmentOcItems(ocItems);
					}
					
				}
				xoc = ediShipmentOCDoa.save(xoc);
				response.setStatus(SBConstant.TXN_STATUS_SUCCESS);
				response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_SUCCESS);
				response.setUniqueKey(xoc.getOcId().toString());
				response.setResponseMessage("Order Cancellation for Shipment Id '"+oc.getPurchaseOrderNumber()+"' is created successfully.");
			} catch (Exception e) {
				response.setStatus(SBConstant.TXN_STATUS_EXCEPTION);
				response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_EXCEPTION);
				response.setUniqueKey(null);
				response.setErrorDesc("Order Cancellation creation failed, General exception occured. \n"+e.getMessage());
				logger.error(requestId + SBConstant.LOG_SEPRATOR + "Exception :: createOrderCancellation(String requestId, OC oc, String vendorPartyId, int etailorId, String user)", e);
			} finally {
				if(response.getStatus().equalsIgnoreCase(SBConstant.TXN_STATUS_SUCCESS)) {
					processOcrAgainstOc(requestId, oc, etailorId, user, xoc);
				}
			}
			
		} else {
			response.setStatus(SBConstant.TXN_STATUS_FAILURE);
			response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_FAILURE);
			response.setErrorDesc("Order cancellation creation failed, Order cancellation metadata is null.");
		}
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "processOC(String requestId, OC oc, String vendorPartyId, int etailorId, String user)---END");
		return response;
	}

	private void processOcrAgainstOc(String requestId, OC oc, int etailorId, String user, EdiShipmentOc xoc) {
		if(xoc != null) {
			if (SBUtils.getPropertyValue("manifest.etailor.id.list").contains(String.valueOf(etailorId))) {
				//PROCESS OCR with respect to OC 
				EdiShipmentHdr shipment = ediShipmentDoa.findByShipmentIdAndWarehouseCode(oc.getPurchaseOrderNumber(), oc.getWarehouseLocationId());
				if(shipment != null) {
					xoc.setCustomerOrderNumber(shipment.getOrderId());
					xoc.setEdiOrderId(shipment.getEdiOrderId());
					int	isIanReq  = 3;
					int isOcrDone = 2;
					int isCancelled=2;
					String ocrRespCond = null; 
					String ocrResultCd = null; 
					if(shipment.getOrderStatus() < 2) { 			//0=Unassigned, 1=New
						shipment.setOrderStatus(4);	
						shipment.setIsCanceled(1);
						for(EdiShipmentOcItems itm : xoc.getEdiShipmentOcItems()) {
							itm.setIsIan(3);//Not Required
							itm.setResponseCondition("SUCCESS");
							itm.setResultCode("89");
							itm.setResultDescription("INVENTORY MOVED TO PRIME");	
							for(EdiShipmentItems si : shipment.getEdiShipmentItems()) {
								if(si.getItemId().equalsIgnoreCase(itm.getItemId())) {
									itm.setEdiItemId(si.getEdiItemId());
								}
							}
						}
						ocrRespCond = "SUCCESS"; 
						ocrResultCd = "00"; 
					} else if(shipment.getOrderStatus() == 2 || shipment.getOrderStatus() == 5) {//2=Accept, 5=Side line
						if(shipment.getFulfilmentType() == null) { 	//Inventory picked not created
							shipment.setOrderStatus(4);	
							shipment.setIsCanceled(1);
							for(EdiShipmentOcItems itm : xoc.getEdiShipmentOcItems()) {
								itm.setIsIan(3);//Not Required
								itm.setResponseCondition("SUCCESS");
								itm.setResultCode("89");
								itm.setResultDescription("INVENTORY MOVED TO PRIME");		
								for(EdiShipmentItems si : shipment.getEdiShipmentItems()) {
									if(si.getItemId().equalsIgnoreCase(itm.getItemId())) {
										itm.setEdiItemId(si.getEdiItemId());
									}
								}
							}
							ocrRespCond = "SUCCESS"; 
							ocrResultCd = "00"; 
						} else {									//Inventory picked already created
							shipment.setOrderStatus(4);	
							shipment.setIsCanceled(1);
							for(EdiShipmentOcItems itm : xoc.getEdiShipmentOcItems()) {
								itm.setResponseCondition("SUCCESS");
								itm.setResultCode("88");
								itm.setResultDescription("PENDING-CANCELLED");	
								ServiceResponse ocIanResp = createIanAgainstOcItem(requestId, etailorId, oc.getWarehouseLocationId(), oc.getPurchaseOrderNumber(), itm, user);
								if(ocIanResp.getStatus().equalsIgnoreCase(SBConstant.TXN_STATUS_SUCCESS)) {
									if (ocIanResp.getUniqueKey() != null) {
										itm.setIanId(Long.parseLong(ocIanResp.getUniqueKey()));
										itm.setIsIan(2);
									} 
								} else {
									itm.setIsIan(5);
								}
								for(EdiShipmentItems si : shipment.getEdiShipmentItems()) {
									if(si.getItemId().equalsIgnoreCase(itm.getItemId())) {
										itm.setEdiItemId(si.getEdiItemId());
									}
								}
							}
							isIanReq 	= 2;
							ocrRespCond = "SUCCESS"; 
							ocrResultCd = "01"; 
						}
					} else if(shipment.getOrderStatus() == 4) { 	//4=Cancelled
						isCancelled = 4;
					} else if(shipment.getOrderStatus() == 9) { 	//9=Packed
						for(EdiShipmentOcItems itm : xoc.getEdiShipmentOcItems()) {
							itm.setIsIan(3);
							itm.setResponseCondition("FAILURE");
							itm.setResultCode("03");
							itm.setResultDescription("Item is packed");	
							for(EdiShipmentItems si : shipment.getEdiShipmentItems()) {
								if(si.getItemId().equalsIgnoreCase(itm.getItemId())) {
									itm.setEdiItemId(si.getEdiItemId());
								}
							}
						}
						isCancelled = 3;
						ocrRespCond = "FAILURE"; 
						ocrResultCd = "03";
					} else if(shipment.getOrderStatus() > 9) {		//10=Delivery Created, 11=Shipped, 12=Completed
						for(EdiShipmentOcItems itm : xoc.getEdiShipmentOcItems()) {
							itm.setIsIan(3);
							itm.setResponseCondition("FAILURE");
							itm.setResultCode("03");
							itm.setResultDescription("Item is shipped");	
							for(EdiShipmentItems si : shipment.getEdiShipmentItems()) {
								if(si.getItemId().equalsIgnoreCase(itm.getItemId())) {
									itm.setEdiItemId(si.getEdiItemId());
								}
							}
						}
						isCancelled = 3;
						ocrRespCond = "FAILURE"; 
						ocrResultCd = "03";
					}
					
					try {
						ediShipmentDoa.save(shipment);
					} catch (Exception e) {
						isCancelled = 5;
					}
					boolean isOcr = isOcrExist(requestId, etailorId, oc.getWarehouseLocationId(), oc.getPurchaseOrderNumber());
					if(isOcr) {
						isOcrDone = 4;
					} 
					xoc.setIsCancelDone(isCancelled);
					xoc.setIsIanDone(isIanReq);
					xoc.setIsOcrDone(isOcrDone);
					xoc.setResponseCondition(ocrRespCond);
					xoc.setResultCode(ocrResultCd);
					xoc = ediShipmentOCDoa.save(xoc);
					
					if(isOcrDone == 2) {
						logger.info(requestId + SBConstant.LOG_SEPRATOR + "OCR for OC '" + xoc.getOcId() + "' " + SBConstant.LOG_SEPRATOR + "START");
						ServiceResponse ocResp = createOcrAgainstOc(requestId, xoc);
						logger.info(requestId + SBConstant.LOG_SEPRATOR + "OCR Response >>>"+ocResp.toString());
						logger.info(requestId + SBConstant.LOG_SEPRATOR + "OCR for OC '" + xoc.getOcId() + "' " + SBConstant.LOG_SEPRATOR + "END");
					}

					if (isIanReq == 2) {
						createIanAgainstOc(requestId, xoc);
						//TODO MAKE INVENTORY RETURNS
					}
					
				} else {
					xoc.setIsCancelDone(4);
					xoc = ediShipmentOCDoa.save(xoc);
				}
			}
		}
	}
	
	private void createIanAgainstOc(String requestId, EdiShipmentOc xoc) {
		logger.info(requestId+SBConstant.LOG_SEPRATOR+"createIanAgainstOc(String requestId, EdiShipmentOc xoc)"+SBConstant.LOG_SEPRATOR_WITH_START);
		if(xoc != null) {
			if(xoc.getEdiShipmentOcItems() != null) {
				for(EdiShipmentOcItems item : xoc.getEdiShipmentOcItems()) {
					try {
						EdiInventoryIanForm input = new EdiInventoryIanForm();
						input.setRequestId(requestId);
						input.setWarehouseCode(xoc.getWarehouseLocationId());;
						input.setEtailorId(xoc.getEretailorId());
						input.setSkuCode(item.getSkuCode());
						input.setFnsku(item.getFnSku());
						input.setQuantity(item.getQuantity());
						input.setAdjustmentType(SBConstant.IAN_ADUSTMENT_TYPE_STATUS_CHANGE);
						input.setPurchaseOrderNumber(xoc.getPurchaseOrderNumber());
						input.setInventorySourceLocation(SBConstant.IAN_LOCATION_TYPE_PENDING_CANCELLED);
						input.setInventoryDestinationLocation(SBConstant.IAN_LOCATION_TYPE_PRIME);
						input.setCreatedBy(SBUtils.getPropertyValue("sb.api.owner"));
						ServiceResponse response = inventoryService.makeIAN(input);
						logger.info("IAN Response >>>> " + response.toString());
					} catch (Exception e) {
						logger.info(requestId+SBConstant.LOG_SEPRATOR+"createIanAgainstOc(String requestId, EdiShipmentOc xoc)", e);
					}
				}
			}
		}
		logger.info(requestId+SBConstant.LOG_SEPRATOR+"createIanAgainstOc(String requestId, EdiShipmentOc xoc)"+SBConstant.LOG_SEPRATOR_WITH_END);
	}

	@Override
	public Long isOrderCancellationExist(String requestId, int etailorId, String warehouseCode, String shipmentId) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "isOrderCancellationExist(String requestId, int etailorId, String warehouseCode, String shipmentId)---START");
		Long uniqueId = 0L;
		try {
			String sql = "SELECT COUNT(1) FROM SELLER.EDI_SHIPMENT_OC WHERE PURCHASE_ORDER_NUMBER = '"+shipmentId+"' AND ERETAILOR_ID = " + etailorId + " AND WAREHOUSE_LOCATION_ID = '"+warehouseCode+"'";
			Long orders = jdbcTemplate.queryForObject(sql, Long.class);
			if (orders == 1) {
				sql = "SELECT OC_ID FROM SELLER.EDI_SHIPMENT_OC WHERE PURCHASE_ORDER_NUMBER = '"+shipmentId+"' AND ERETAILOR_ID = " + etailorId + " AND WAREHOUSE_LOCATION_ID = '"+warehouseCode+"'";
				uniqueId = jdbcTemplate.queryForObject(sql, Long.class);
			} else if(orders > 1) {
				uniqueId = 0L;
			} else {
				uniqueId = -1L; //No OC found
			}
		} catch (DataAccessException e) {
			logger.error(requestId + SBConstant.LOG_SEPRATOR + "isOrderCancellationExist(String requestId, int etailorId, String warehouseCode, String shipmentId)", e);
		}
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "isOrderCancellationExist(String requestId, int etailorId, String warehouseCode, String shipmentId)---END");
		return uniqueId;
	}
	@Override
	public boolean isOcrExist(String requestId, int etailorId, String warehouseCode, String shipmentId) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "isOcrExist(String requestId, int etailorId, String warehouseCode, String shipmentId)---START");
		try {
			String sql = "SELECT COUNT(1) FROM SELLER.EDI_SHIPMENT_OCR WHERE PURCHASE_ORDER_NUMBER = '"+shipmentId+"' AND ERETAILOR_ID = " + etailorId + " AND WAREHOUSE_LOCATION_ID = '"+warehouseCode+"'";
			Long orders = jdbcTemplate.queryForObject(sql, Long.class);
			if (orders == 1) {
				return true;
			} 
		} catch (DataAccessException e) {
			logger.error(requestId + SBConstant.LOG_SEPRATOR + "isOcrExist(String requestId, int etailorId, String warehouseCode, String shipmentId)", e);
		}
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "isOcrExist(String requestId, int etailorId, String warehouseCode, String shipmentId)---END");
		return false;
	}
	
	public ServiceResponse createIanAgainstOcItem(String requestId, int etailorId, String warehouseCode, String shipmentId, EdiShipmentOcItems oci, String user) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createIanAgainstOcItem(String requestId, int etailorId, String warehouseCode, String shipmentId, EdiShipmentOcItems oci, String user)---START");
		ServiceResponse response = new ServiceResponse();
		try {
			EdiInventoryIanForm input = new EdiInventoryIanForm();
			input.setRequestId(requestId);
			input.setWarehouseCode(warehouseCode);
			input.setEtailorId(etailorId);
			input.setSkuCode(oci.getSkuCode());
			input.setFnsku(oci.getFnSku());
			input.setQuantity(oci.getQuantity());
			input.setAdjustmentType(SBConstant.IAN_ADUSTMENT_TYPE_STATUS_CHANGE);
			input.setPurchaseOrderNumber(shipmentId);
			input.setCreatedBy(user);
			input.setInventorySourceLocation(SBConstant.IAN_LOCATION_TYPE_PENDING_CANCELLED);
			input.setInventoryDestinationLocation(SBConstant.IAN_LOCATION_TYPE_UNAVAILABLE);
			response = inventoryService.makeIAN(input);
			logger.info(requestId + SBConstant.LOG_SEPRATOR + "IAN Response"+SBConstant.LOG_SEPRATOR+response.toString());
		} catch (Exception e) {
			response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_EXCEPTION);
			response.setStatus(SBConstant.TXN_STATUS_EXCEPTION);
			response.setResponseMessage("General exception occured, Unable to create ian for for ocItemId = "+ oci.getOcItemId());
			response.setUniqueKey(null);
			response.setErrorDesc(e.getMessage());
			logger.error(requestId + SBConstant.LOG_SEPRATOR + "createIanAgainstOcItem(String requestId, int etailorId, String warehouseCode, String shipmentId, EdiShipmentOcItems oci, String user)", e);
		}
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createIanAgainstOcItem(String requestId, int etailorId, String warehouseCode, String shipmentId, EdiShipmentOcItems oci, String user)---END");
		return response;
	}
	
	@Override
	public ServiceResponse createOcrAgainstOc(String requestId, EdiShipmentOc xoc) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOcrAgainstOc(String requestId, EdiShipmentOc xoc)---START");
		ServiceResponse response = new ServiceResponse();
		EdiShipmentOcr ocr = null;
		response.setRequestId(requestId);
		if (xoc != null) {
			ocr = ediShipmentOcrDao.findByOcId(xoc.getOcId());
			if (ocr != null) {
				logger.warn(requestId + SBConstant.LOG_SEPRATOR + "createOcrAgainstOc(String requestId, EdiShipmentOc xoc)---Duplicate Request");
				response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_DUPLICATE);
				response.setStatus(SBConstant.TXN_STATUS_DUPLICATE);
				response.setUniqueKey(xoc.getOcId().toString());
				response.setResponseMessage("OCR against ocId = "+xoc.getOcId()+" is already created.");
			} else {
				try {
					EdiConfig config = new EdiConfig(requestId, ediConfigDao, xoc.getWarehouseLocationId(), xoc.getEretailorId(), SBConstant.MESSAGE_TYPE_OCR);
					ocr = new EdiShipmentOcr();
					ocr.setOcId(xoc.getOcId());
					ocr.setEretailorId(xoc.getEretailorId());
					ocr.setTransCreationDate(config.getTransCreationDate());
					ocr.setTransIsTest(config.getTransIsTest());
					ocr.setTransControlNumber(config.getTransControlNumber());
					ocr.setMessageCount(config.getMessageCount());
					ocr.setTransStructureVersion(config.getTransStructureVersion());
					ocr.setTransReceivingPartyId(config.getTransReceivingPartyId());
					ocr.setTransSendingPartyId(config.getTransSendingPartyId());
					ocr.setMessageType(config.getMessageType());
					ocr.setMessageIsTest(config.getMessageIsTest());
					ocr.setMessageSendingPartyId(config.getMessageSendingPartyId());
					ocr.setMessageReceivingPartyId(config.getMessageReceivingPartyId());
					ocr.setMessageStructureVersion(config.getMessageStructureVersion());
					ocr.setMessageCreationDate(config.getMessageCreationDate());
					ocr.setMessageControlNumber(config.getMessageControlNumber());
					ocr.setVendorPartyType(config.getVendorPartyType());
					ocr.setVendorPartyId(config.getVendorPartyId());
					ocr.setWarehouseLocationId(config.getWarehouseCode());
					ocr.setPurchaseOrderNumber(xoc.getPurchaseOrderNumber());
					ocr.setCustomerOrderNumber(xoc.getCustomerOrderNumber());
					ocr.setCancelRequestType(xoc.getCancelRequestType());
					ocr.setResponseCondition(xoc.getResponseCondition());
					ocr.setResultCode(xoc.getResultCode());
					//ocr.setResultDescription(xoc.getResultDescription());
					ocr.setRunStatus(0);
					ocr.setTxnStatus(SBConstant.TXN_STATUS_INIT);
					ocr.setProcessInstanceId(requestId);
					ocr.setOcrFilePath(config.getArchiveFilepath());
					ocr.setTxnErrorMsg(null);
					ocr.setCreatedDate(new Date());
					ocr.setCreatedBy(xoc.getCreatedBy());
					ocr = ediShipmentOcrDao.save(ocr);
					logger.info(requestId + SBConstant.LOG_SEPRATOR + "createAsn(String requestId, Long ediOrderId)---SUCCESS");
					response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_SUCCESS);
					response.setStatus(SBConstant.TXN_STATUS_SUCCESS);
					response.setUniqueKey(ocr.getOcrId().toString());
					response.setResponseMessage("OCR against ocId = "+xoc.getOcId()+" created succesfully.");
				} catch (Exception e) {
					response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_EXCEPTION);
					response.setStatus(SBConstant.TXN_STATUS_EXCEPTION);
					response.setUniqueKey(null);
					response.setResponseMessage("OCR against ocId = "+xoc.getOcId()+" created failed.");
					response.setErrorDesc(e.getMessage());
				} finally {
					if (ocr != null && response.getStatus().equalsIgnoreCase(SBConstant.TXN_STATUS_SUCCESS)) {
						String topic = SBConstant.MESSAGE_TYPE_OCR;
						OutboundMessage message = new OutboundMessage();
						message.setRequestId(requestId);
						message.setMessageType(topic);
						message.setOcr(ocr);
						message.setStatus(SBConstant.TXN_STATUS_INIT);
						logger.info("Kafka Request >>>> " + message.toString());
						boolean status = kafkaUtils.send(requestId, topic, message);
						logger.info(requestId + SBConstant.LOG_SEPRATOR + "OCR #"+ocr.getOcrId()+SBConstant.LOG_SEPRATOR+"Kafka produce "+SBConstant.LOG_SEPRATOR+(status == true ? "SUCCESS" : "FAILED"));
					} else {
						logger.error(requestId + SBConstant.LOG_SEPRATOR + "OCR #"+ocr.getOcrId()+SBConstant.LOG_SEPRATOR+"not Kafka produce due to OCR create response = "+response.getStatus());
					}
				}
			} 
		}
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOcrAgainstOc(String requestId, EdiShipmentOc xoc)---END");
		
		
		
		return response;
	}
}
