package com.seller.box.service.impl;

import java.text.DecimalFormat;
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
import com.seller.box.core.OF;
import com.seller.box.core.OFItem;
import com.seller.box.core.OutboundMessage;
import com.seller.box.core.ServiceResponse;
import com.seller.box.dao.EdiBoxTypeDao;
import com.seller.box.dao.EdiConfigDao;
import com.seller.box.dao.EdiShipmentAsnDao;
import com.seller.box.dao.EdiShipmentDao;
import com.seller.box.dao.EdiShipmentOfrDao;
import com.seller.box.dao.ProductMeasurementDao;
import com.seller.box.dao.ProductSearchDao;
import com.seller.box.entities.EdiBoxType;
import com.seller.box.entities.EdiShipmentAsn;
import com.seller.box.entities.EdiShipmentHdr;
import com.seller.box.entities.EdiShipmentItems;
import com.seller.box.entities.EdiShipmentOfr;
import com.seller.box.entities.ProductDetails;
import com.seller.box.entities.ProductMeasurements;
import com.seller.box.service.InventoryService;
import com.seller.box.service.OrderService;
import com.seller.box.utils.KafkaUtils;
import com.seller.box.utils.SBConstant;
import com.seller.box.utils.SBUtils;
@Service
public class OrderServiceImpl implements OrderService {
	private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);
	@Autowired
	KafkaUtils kafkaUtils;
	@Autowired
	EdiConfigDao ediConfigDao;
	@Autowired
	EdiShipmentDao ediShipmentDao;
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	InventoryService inventoryService;
	@Autowired
	ProductMeasurementDao productMeasurementDao;
	@Autowired
	EdiBoxTypeDao ediBoxTypeDao;
	@Autowired
	EdiShipmentOfrDao ediShipmentOfrDao;
	@Autowired
	EdiShipmentAsnDao ediShipmentAsnDao;
	@Autowired
	ProductSearchDao productSearchDao;
	
	@Override
	@Async
	public void createOrderAsync(String requestId, OF of, String orderType, int etailorId, Long ediOrderId, String user) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrderAsync(String requestId, OF of, int etailorId, Long ediOrderId, String user)---START");
		createOrder(requestId, of, orderType, etailorId, ediOrderId, user);
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrderAsync(String requestId, OF of, int etailorId, Long ediOrderId, String user)---END");
	}

	@Override
	public ServiceResponse createOrder(String requestId, OF of, String orderType, int etailorId, Long ediOrderId, String user) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrder(String requestId, OF of, int etailorId, Long ediOrderId, String user)---START");
		ServiceResponse response = new ServiceResponse();
		if(of != null) {
			try {
				EdiShipmentHdr shipment = new EdiShipmentHdr();
				response.setUniqueKey(ediOrderId.toString());
				response.setRequestId(requestId);
				
				shipment.setEdiOrderId(ediOrderId);
				shipment.setOrderType(orderType.toUpperCase());
				shipment.setEtailorId(etailorId);
				shipment.setProcessInstanceId(requestId);
				shipment.setShipmentId(of.getPurchaseOrder());
				shipment.setOrderId(of.getCustomerOrder());
				shipment.setBilltoEntityId(of.getBillToEntityId());
				shipment.setWarehouseCode(of.getWarehouseLocationId());
				shipment.setOrderDate(of.getOrdereDate());
				shipment.setExsdDate(of.getExpectedShipDate());
				shipment.setBoxType(of.getBoxType());
				shipment.setIsGift(of.getIsGift());
				shipment.setIsPriorityShipment(of.getIsPriorityShipment() != null ? of.getIsPriorityShipment() : 0);
				shipment.setIsCod(of.getIsCod() != null ? of.getIsCod() : 0);
				shipment.setIsInsertsRequired(of.getIsInsertsRequired() != null ? of.getIsInsertsRequired() : 0);
				shipment.setPaymentType(of.getPaymentType());
				shipment.setCurrencyCode(of.getCurrencyCode());
				shipment.setTaxRate(of.getTaxRate().doubleValue());
				shipment.setTaxAmount(of.getTaxAmount().doubleValue());
				shipment.setShipChargeAmount(of.getShipChargeAmount().doubleValue());
				shipment.setSubTotal(of.getSubTotal().doubleValue());
				shipment.setOrderTotal(of.getOrderTotal().doubleValue());
				shipment.setBalanceDue(of.getBalanceDue().doubleValue());
				shipment.setBuyerName(of.getBuyerName());
				shipment.setBuyerAttentionLine(of.getBuyerAttentionLine());
				shipment.setBuyerAddressLine1(of.getBuyerAddressLine1());
				shipment.setBuyerCity(of.getBuyerCity());
				shipment.setBuyerState(of.getBuyerState());
				shipment.setBuyerCountryCode(of.getBuyerCountryCode());
				shipment.setBuyerPostalCode(of.getBuyerPostalCode());
				shipment.setOrderSiteId(of.getOrderSiteId().intValue());
				shipment.setShipToName(of.getShipToName());
				shipment.setShipToAddressLine1(of.getShipToAddressLine1());
				shipment.setShipToAddressLine2(of.getShipToAddressLine2());
				shipment.setShipToCity(of.getShipToCity());
				shipment.setShipToState(of.getShipToState());
				shipment.setShipToCountryCode(of.getShipToCountryCode());
				shipment.setShipToCountryName(of.getShipToCountryName());
				shipment.setShipPostalCode(of.getShipPostalCode());
				shipment.setShipToContactPhone(of.getShipToContactPhone());
				shipment.setShipMethod(of.getShipMethod());
				shipment.setBillToName(of.getBillToName());
				shipment.setBillToAddressLine1(of.getBillToAddressLine1());
				shipment.setBillToAddressLine2(of.getBillToAddressLine2());
				shipment.setBillToCity(of.getBillToCity());
				shipment.setBillToState(of.getBillToState());
				shipment.setBillToCountryCode(of.getBillToCountryCode());
				shipment.setBillToCountryName(of.getBillToCountryName());
				shipment.setBillPostalCode(of.getBillPostalCode());
				shipment.setBillToContactPhone(of.getBillToContactPhone());
				shipment.setPackageId(Integer.parseInt(SBUtils.getPropertyValue("amazon.of.package.id")));
				//shipment.setTrackingId(of.gettrackingId);
				//shipment.setBarcode(of.getbarcode);
				
				//shipment.setCanManifest(of.getcanManifest);
				//shipment.setManifestId(of.getmanifestId);
				//shipment.setCarrierName(of.getcarrierName);
				shipment.setLocalTimeZone(SBUtils.getPropertyValue("local.time.zone"));
				//shipment.setPickupDate(of.getpickupDate);
				//shipment.setLabelContent(of.getlabelContent);
				shipment.setLabelFormatType(SBUtils.getPropertyValue("label.file.format"));
				shipment.setLabelWidthValue(4);
				shipment.setLabelWidthUnit("IN");
				shipment.setLabelLengthValue(6);
				shipment.setLabelLengthUnit("IN");
				/**
				shipment.setPackageLengthValue(of.getpackageLengthValue);
				shipment.setPackageLengthUnit(of.getpackageLengthUnit);
				shipment.setPackageWidthValue(of.getpackageWidthValue);
				shipment.setPackageWidthUnit(of.getpackageWidthUnit);
				shipment.setPackageHeightValue(of.getpackageHeightValue);
				shipment.setPackageHeightUnit(of.getpackageHeightUnit);
				shipment.setPackageWeightValue(of.getpackageWeightValue);
				shipment.setPackageWeightUnit(of.getpackageWeightUnit);
				shipment.setIsGiftWrap(of.getisGiftWrap);
				//shipment.setFulfilmentType(of.getfulfilmentType);//either fulfill from inventory or production or mixed case
				**/
				shipment.setBraaPpType(of.getBraaPpType());
				shipment.setBraaPpTypeIdentifier(of.getBraaPpTypeIdentifier());
				shipment.setBraaPpQuantity(of.getBraaPpQuantity() != null ? of.getBraaPpQuantity().intValue() : 0);
				shipment.setOrderStatus(1);
				shipment.setOrderFilePath(of.getOrderFilePath());
				shipment.setCreatedBy(user);
				shipment.setCreatedDate(new Date());
				shipment.setIsAccepted(0);
				shipment.setIsCanceled(0);
				shipment.setIsAsnSend(0);
				shipment.setIsOmsUpload(0);
				shipment.setIsMeasureDone(0);

				//shipment.setPicklistNumber(of.getpicklistNumber);
				//shipment.setShipLabelFilepath(of.getshipLabelFilepath);
				//shipment.setManifestDate(of.getmanifestDate);
				//shipment.setPackedBy(of.getpackedBy);
				//shipment.setOmsUploadErrorMsg(of.getomsUploadErrorMsg);
				//shipment.setBatchId(of.getbatchId);
				//shipment.setRefOrderId(of.getrefOrderId);
				if (of.getShipmentItems() != null) {
					shipment.setNoOfItems(of.getShipmentItems().size());
					if(of.getShipmentItems().size() > 0) {
						Set<EdiShipmentItems> ediShipmentItems = new HashSet<EdiShipmentItems>();
						for (OFItem i : of.getShipmentItems()) {
							EdiShipmentItems item = new EdiShipmentItems();
							String value = i.getSkuCode();
							if(SBUtils.isNull(value) && SBUtils.isNotNull(i.getEan())) {
								value = i.getEan();
							}
							if(SBUtils.isNull(value) && SBUtils.isNotNull(i.getFnSku())) {
								value = i.getFnSku();
							}
							if(SBUtils.isNull(value) && SBUtils.isNotNull(i.getItemId())) {
								value = i.getItemId();
							}
							if(SBUtils.isNotNull(value)) {
								ProductDetails product = productSearchDao.findProduct(etailorId, value);
								if(product != null) {
									item.setSkuCode(product.getSkuCode());
									if (SBUtils.isNotNull(product.getEan())) {
										item.setEan(Long.parseLong(product.getEan()));
									}
									item.setFnsku(product.getFnsku());
									item.setItemId(product.getItemId());
									item.setProductName(product.getProductName());
									item.setThumbnailUrl(product.getThumbnailUrl());
								} else {
									item.setSkuCode(i.getSkuCode());
									if (SBUtils.isNotNull(i.getEan())) {
										item.setEan(Long.parseLong(i.getEan()));
									}
									item.setFnsku(i.getFnSku());
									item.setItemId(i.getItemId());
									item.setProductName(i.getProductName());
									item.setThumbnailUrl(i.getThumbnailUrl());
								} 
							}
//							int year = Calendar.getInstance().get(Calendar.YEAR);
//							Long itemIdSeq = ediConfigDao.getId("OF_ITEM_ID_SEQ");
//							String ordItemId = year + String.format("%010d", itemIdSeq);
//							item.setEdiItemId(Long.parseLong(ordItemId));
							item.setEdiOrderId(ediOrderId);
							item.setLineItemSeq(i.getLineItemSeq().intValue());
							item.setWarehouseCode(shipment.getWarehouseCode());
							item.setQuantity(i.getQuantity());
							item.setCurrencyCode(i.getCurrencyCode());
							item.setCustomerPrice(i.getCustomerPrice().doubleValue());
							item.setLineItemTotal(i.getLineItemTotal().doubleValue());
							item.setGiftLabelContent(i.getGiftLabelContent());
							item.setGiftWrapId(i.getGiftWrapId());
							item.setSellerOfRecordName(i.getSellerOfRecordName());
							item.setSellerOfRecordId(i.getSellerOfRecordId().intValue());
							item.setMerchantId(i.getMerchantId());
							item.setOrderItemId(i.getCustomerOrderItemID());
							if(i.getCustomerOrderItemDetailID() != null) {
								item.setCustomerOrderItemDetailId(Long.parseLong(i.getCustomerOrderItemDetailID()));
							}
							/***
							item.setQntFromInv(qntFromInv);
							item.setQntFromVir(qntFromVir);
							item.setBayNo(bayNo);
							item.setRackNo(rackNo);
							item.setIsCanceled(isCanceled);
							item.setIsItemUpload(isItemUpload);
							item.setIsInvDeduct(isInvDeduct);
							item.setInvDeductErrorMsg(invDeductErrorMsg);
							item.setRefItemId(refItemId);
							***/
							
							ediShipmentItems.add(item);
						}
						shipment.setEdiShipmentItems(ediShipmentItems);
					} 
				}
				EdiShipmentHdr sh = ediShipmentDao.save(shipment);
				
				try {
					logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrder(...) ---> Updating Loading & Trailor Id ---> START");
					if(SBUtils.getPropertyValue("manifest.etailor.id.list").contains(String.valueOf(etailorId))) {
						Long id = ediConfigDao.getId("LOAD_TRAILOR_ID");
						Long prefix = ediConfigDao.getId("LOAD_TRAILOR_PREFIX");
						String trailorId = SBUtils.getPropertyValue("LOAD_TRAILOR_PREFIX_CODE")+"-T"+String.format("%04d", prefix)+"-"+String.format("%010d", id);
						String loadId    = SBUtils.getPropertyValue("LOAD_TRAILOR_PREFIX_CODE")+"-L"+String.format("%04d", prefix)+"-"+String.format("%010d", id);
						sh.setTrailorId(trailorId);
						sh.setLoadId(loadId);
						ediShipmentDao.save(sh);
						
						measurmentUpdateAsync(requestId, ediOrderId);
						logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrder(...) ---> Updating Loading & Trailor Id ---> END");
					}
				} catch (Exception e) {
					logger.error(requestId + SBConstant.LOG_SEPRATOR + "Exception :: createOrder(...) ---> Updating Loading & Trailor Id.", e);
				}
				
				
				
				response.setStatus(SBConstant.TXN_STATUS_SUCCESS);
				response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_SUCCESS);
				String responseMessage = null;
				if(of.getPurchaseOrder() != null) {
					responseMessage = "Shipment Id '" + of.getPurchaseOrder() +"' is created successfully.";
				} else if(of.getCustomerOrder() != null) {
					responseMessage = "Order Id '" + of.getCustomerOrder() +"' is created successfully.";
				}
				response.setResponseMessage(responseMessage);
				//TODO update measurement and reserve inventory
			} catch (Exception e) {
				logger.error(requestId + SBConstant.LOG_SEPRATOR + "Exception :: createOrder(...)", e);
				response.setStatus(SBConstant.TXN_STATUS_EXCEPTION);
				response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_EXCEPTION);
				response.setErrorDesc("Order creation failed, General exception occured. \n"+e.getMessage());
			} 
		} else {
			response.setStatus(SBConstant.TXN_STATUS_FAILURE);
			response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_FAILURE);
			response.setErrorDesc("Order creation failed, Order metadata is null.");
		}
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrder(...) --> Response = " + response.toString());
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrder(String requestId, OF of, int etailorId, Long ediOrderId, String user)---END");
		return response;
	}

	@Override
	public Long isOrderExist(String requestId, int etailorId, String warehouseCode, String shipmentId) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "isOrderExist(String requestId, int etailorId, String warehouseCode, String shipmentId)---START");
		Long uniqueId = 0L;
		try {
			String sql = "SELECT COUNT(1) FROM SELLER.EDI_SHIPMENT_HDR WHERE ERETAILOR_ID = " + etailorId + " AND WAREHOUSE_CODE = '"+warehouseCode+"' AND PURCHASE_ORDER_NUMBER = '" +shipmentId+ "'";
			Long orders = jdbcTemplate.queryForObject(sql, Long.class);
			if (orders == 1) {
				sql = "SELECT EDI_ORDER_ID FROM SELLER.EDI_SHIPMENT_HDR WHERE ERETAILOR_ID = " + etailorId + " AND WAREHOUSE_CODE = '" + warehouseCode + "' AND PURCHASE_ORDER_NUMBER = '" + shipmentId + "'";
				uniqueId = jdbcTemplate.queryForObject(sql, Long.class);
			} else if(orders > 1) {
				uniqueId = 0L;
			} else {
				uniqueId = -1L; //No order found
			}
		} catch (DataAccessException e) {
			logger.error(requestId + SBConstant.LOG_SEPRATOR + "isOrderExist(String requestId, int etailorId, String warehouseCode, String shipmentId)", e);
		}
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "isOrderExist(String requestId, int etailorId, String warehouseCode, String shipmentId)---START");
		return uniqueId;
	}
	
//	public ProductDetails getProductInfo(int etailorId, String value) {
//		logger.info("getProductInfo(int etailorId, String value)----START");
//		ProductDetails product = null;
//		try {
//			product = inventoryService.getProductInfo(etailorId, value);
//		} catch (Exception e) {
//			logger.info("Exception :: getProductInfo(int etailorId, String value)", e);
//		}
//		logger.info("getProductInfo(int etailorId, String value)----START");
//		return product;
//	}

	@Override
	public boolean isOrderExistByEdiOrderId(String requestId, Long ediOrderId) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "isOrderExistByEdiOrderId(String requestId, Long ediOrderId)---START");
		boolean flag = false;
		try {
			String sql = "SELECT COUNT(1) FROM SELLER.EDI_SHIPMENT_HDR WHERE EDI_ORDER_ID = " + ediOrderId;
			Long orders = jdbcTemplate.queryForObject(sql, Long.class);
			if (orders == 1) {
				flag = true;
			}
		} catch (DataAccessException e) {
			logger.error(requestId + SBConstant.LOG_SEPRATOR + "isOrderExistByEdiOrderId(String requestId, Long ediOrderId)", e);
		} 
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "isOrderExistByEdiOrderId(String requestId, Long ediOrderId)---END");
		return flag;
	}

	@Override
	@Async
	public void measurmentUpdateAsync(String requestId, Long ediOrderId) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "measurmentUpdateAsync(String requestId, Long ediOrderId)----START");
		try {
			EdiShipmentHdr shipment = ediShipmentDao.findByEdiOrderId(ediOrderId);
			if(shipment != null) {
				DecimalFormat df = new DecimalFormat("##.000");
				double length = 0d;
				double width  = 0d;
				double height = 0d;
				double weigth = 0d;
				EdiBoxType ediBoxType = ediBoxTypeDao.findByBoxName(shipment.getBoxType());
				if(ediBoxType != null) {
					try {
						if(ediBoxType.getLengthIN() > 0) {
							length = Double.parseDouble(df.format(ediBoxType.getLengthIN()));
						}
					} catch (NumberFormatException e) {
						logger.error("NumberFormatException for length = "+ ediBoxType.getLengthIN());
					}
					try {
						if(ediBoxType.getWidthIN() > 0) {
							width = Double.parseDouble(df.format(ediBoxType.getWidthIN()));
						}
					} catch (NumberFormatException e) {
						logger.error("NumberFormatException for width = "+ ediBoxType.getWidthIN());
					}
					try {
						if(ediBoxType.getHeightIN() > 0) {
							height = Double.parseDouble(df.format(ediBoxType.getHeightIN()));
						}
					} catch (NumberFormatException e) {
						logger.error("NumberFormatException for height = "+ ediBoxType.getHeightIN());
					}
					try {
						if(ediBoxType.getWeightKG() > 0) {
							weigth = Double.parseDouble(df.format(ediBoxType.getWeightKG()));
						}
					} catch (NumberFormatException e) {
						logger.error("NumberFormatException for weigth = "+ ediBoxType.getWeightKG());
					}
				}

				Set<EdiShipmentItems> items = shipment.getEdiShipmentItems();
				if(items.size() > 0) {
					for(EdiShipmentItems item : items) {
						ProductMeasurements measurment = productMeasurementDao.findProductByItemId(shipment.getEtailorId(), item.getItemId());

						if(measurment != null) {
							weigth = weigth + (item.getQuantity() * measurment.getWeight());
						}
					}
					if(weigth > 0) {
						try {
							weigth = Double.parseDouble(df.format(weigth));
						} catch (NumberFormatException e) {
							logger.error("NumberFormatException for weigth = "+ ediBoxType.getWeightKG());
						}
					}
				}
				shipment.setEdiOrderId(shipment.getEdiOrderId());
				shipment.setPackageLengthValue(length);
				shipment.setPackageLengthUnit("IN");
				shipment.setPackageWidthValue(Double.parseDouble(df.format(width)));
				shipment.setPackageWidthUnit("IN");
				shipment.setPackageHeightValue(Double.parseDouble(df.format(height)));
				shipment.setPackageHeightUnit("IN");
				shipment.setPackageWeightValue(Double.parseDouble(df.format(weigth)));
				shipment.setPackageWeightUnit("KG");
				shipment.setIsMeasureDone(1);
				ediShipmentDao.save(shipment);
				logger.info("Update measurment for OrderId="+shipment.getOrderId()+" done.");
			}
		} catch (NumberFormatException e) {
			logger.error(requestId + SBConstant.LOG_SEPRATOR + "measurmentUpdateAsync(String requestId, Long ediOrderId)", e);
		} catch (Exception e) {
			logger.error(requestId + SBConstant.LOG_SEPRATOR + "measurmentUpdateAsync(String requestId, Long ediOrderId)", e);
		}
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "measurmentUpdateAsync(String requestId, Long ediOrderId)----END");
	}
	
	@Override
	public ServiceResponse createOfr(String requestId, Long ediOrderId, int orderStatus) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOfr(String requestId, Long ediOrderId, int orderStatus)---START");
		ServiceResponse response = new ServiceResponse();
		EdiShipmentHdr sh = null;
		EdiShipmentOfr ofr = new EdiShipmentOfr();
		Long ofrId = 0L;
		try {
			response.setRequestId(requestId);
			sh = ediShipmentDao.findByEdiOrderId(ediOrderId);
			ofrId = isOfrExistByEdiOrderId(requestId, ediOrderId, orderStatus);
			if(ofrId > 0) {
				try {
					ofr = ediShipmentOfrDao.findOne(ofrId);
					String topic = SBConstant.MESSAGE_TYPE_OFR+orderStatus;
					OutboundMessage message = new OutboundMessage();
					message.setRequestId(requestId);
					message.setMessageType(topic);
					message.setOfr(ofr);
					message.setStatus(SBConstant.TXN_STATUS_INIT);
					logger.info("Kafka Re-Request >>>> " + message.toString());
					boolean status = kafkaUtils.send(requestId, topic, message);
					logger.info(requestId + SBConstant.LOG_SEPRATOR + "OFR{"+orderStatus+"} #"+ofrId+SBConstant.LOG_SEPRATOR+"Kafka re-produce "+SBConstant.LOG_SEPRATOR+(status == true ? "SUCCESS" : "FAILED"));
				} catch (Exception e) {
					logger.error("Exception :: OFR{"+orderStatus+"} #"+ofrId+SBConstant.LOG_SEPRATOR+"Kafka re-produce failed.", e);
				}
				
				logger.warn(requestId + SBConstant.LOG_SEPRATOR + "createOfr()---Duplicate Request");
				response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_DUPLICATE);
				response.setStatus(SBConstant.TXN_STATUS_DUPLICATE);
				response.setUniqueKey(ofrId.toString());
				response.setResponseMessage("OFR{"+orderStatus+"} against ediOrderId = "+ediOrderId+" is already created. But OFR{"+orderStatus+"} for #"+ofrId+" re-produce.");
				if(sh.getIsAccepted() == 0) {
					try {
						sh.setIsAccepted(1);
						ediShipmentDao.save(sh);
					} catch (Exception e) {
						logger.error(requestId + SBConstant.LOG_SEPRATOR + " updation isAccepted = 1 failed for ediOrderId = " + ediOrderId);
					}
				}
			} else {
				EdiConfig config = new EdiConfig(requestId, ediConfigDao, sh.getWarehouseCode(), sh.getEtailorId(), SBConstant.MESSAGE_TYPE_OFR);
				//ofr.setOfrId(ofrId);
				ofr.setEdiOrderId(ediOrderId);
				ofr.setEretailorId(sh.getEtailorId());
				ofr.setTransCreationDate(config.getTransCreationDate());
				ofr.setTransIsTest(config.getTransIsTest());
				ofr.setTransControlNumber(config.getTransControlNumber());
				ofr.setMessageCount(config.getMessageCount());
				ofr.setTransStructureVersion(config.getTransStructureVersion());
				ofr.setTransReceivingPartyId(config.getTransReceivingPartyId());
				ofr.setTransSendingPartyId(config.getTransSendingPartyId());
				ofr.setMessageType(SBConstant.MESSAGE_TYPE_OFR);
				ofr.setMessageIsTest(config.getMessageIsTest());
				ofr.setMessageReceivingPartyId(config.getMessageReceivingPartyId());
				ofr.setMessageSendingPartyId(config.getMessageSendingPartyId());
				ofr.setMessageStructureVersion(config.getMessageStructureVersion());
				ofr.setMessageCreationDate(config.getMessageCreationDate());
				ofr.setMessageControlNumber(config.getMessageControlNumber());
				ofr.setVendorPartyType(config.getVendorPartyType());
				ofr.setVendorPartyId(config.getVendorPartyId());
				ofr.setWarehouseLocationId(config.getWarehouseCode());
				ofr.setPurchaseOrderNumber(sh.getShipmentId());
				ofr.setCustomerOrderNumber(sh.getOrderId());
				ofr.setOrderAcceptedDate(config.getMessageCreationDate());
				ofr.setOrderStatus(orderStatus);
				ofr.setResponseCondition(config.getResponseCondition());
				ofr.setResultCode(config.getResultCode());
				ofr.setResultDescription(config.getResultDescription());
				ofr.setInventoryEffectiveDate(config.getMessageCreationDate());
				ofr.setNumberOfItems(sh.getNoOfItems());
				ofr.setRunStatus(0);
				ofr.setTxnStatus(SBConstant.TXN_STATUS_INIT);
				ofr.setProcessInstanceId(requestId);
				ofr.setOfrFilePath(config.getArchiveFilepath());
				ofr.setCreatedDate(new Date());
				ofr.setCreatedBy(sh.getCreatedBy());
				
				ofr = ediShipmentOfrDao.save(ofr);
				ofrId = ofr.getOfrId();
				logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOfr()---SUCCESS");
				response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_SUCCESS);
				response.setStatus(SBConstant.TXN_STATUS_SUCCESS);
				response.setUniqueKey(ofrId.toString());
				response.setResponseMessage("OFR{"+orderStatus+"} against ediOrderId = "+ediOrderId+" created succesfully.");
			}
		} catch (Exception e) {
			response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_EXCEPTION);
			response.setStatus(SBConstant.TXN_STATUS_EXCEPTION);
			response.setUniqueKey(null);
			response.setResponseMessage("OFR{"+orderStatus+"} against ediOrderId = "+ediOrderId+" creation failed.");
			response.setErrorDesc(e.getMessage());
		} finally {
			if (sh != null && response.getStatus().equalsIgnoreCase(SBConstant.TXN_STATUS_SUCCESS)) {
				String topic = SBConstant.MESSAGE_TYPE_OFR+orderStatus;
				OutboundMessage message = new OutboundMessage();
				message.setRequestId(requestId);
				message.setMessageType(topic);
				message.setOfr(ofr);
				message.setStatus(SBConstant.TXN_STATUS_INIT);
				logger.info("Kafka Request >>>> " + message.toString());
				boolean status = kafkaUtils.send(requestId, topic, message);
				logger.info(requestId + SBConstant.LOG_SEPRATOR + "OFR{"+orderStatus+"} #"+ofrId+SBConstant.LOG_SEPRATOR+"Kafka produce "+SBConstant.LOG_SEPRATOR+(status == true ? "SUCCESS" : "FAILED"));
				try {
					sh.setIsAccepted(1);
					ediShipmentDao.save(sh);
				} catch (Exception e) {
					logger.error(requestId + SBConstant.LOG_SEPRATOR + " updation isAccepted = 1 failed for ediOrderId = " + ediOrderId);
				}
			} else {
				
				logger.error(requestId + SBConstant.LOG_SEPRATOR + "OFR{"+orderStatus+"} #"+ofrId+SBConstant.LOG_SEPRATOR+"not Kafka produce due to OFR create response = "+response.getStatus());
			}
		}
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOfr(String requestId, Long ediOrderId, int orderStatus)---END");
		return response;
	}

	@Override
	public ServiceResponse createAsn(String requestId, Long ediOrderId) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createAsn(String requestId, Long ediOrderId)---START");
		ServiceResponse response = new ServiceResponse();
		EdiShipmentHdr sh = null;
		EdiShipmentAsn asn = null;
		response.setRequestId(requestId);
		sh = ediShipmentDao.findByEdiOrderId(ediOrderId);
		if (sh != null) {
			asn = ediShipmentAsnDao.findByEdiOrderId(ediOrderId);
			if (asn != null) {
				logger.warn(requestId + SBConstant.LOG_SEPRATOR + "createAsn(String requestId, Long ediOrderId)---Duplicate Request");
				response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_DUPLICATE);
				response.setStatus(SBConstant.TXN_STATUS_DUPLICATE);
				response.setUniqueKey(ediOrderId.toString());
				response.setResponseMessage("ASN against ediOrderId = "+ediOrderId+" is already created.");
				if(sh.getIsAsnSend() == 0) {
					try {
						sh.setIsAsnSend(1);
						ediShipmentDao.save(sh);
					} catch (Exception e) {
						logger.error(requestId + SBConstant.LOG_SEPRATOR + " updation isAsnSend = 1 failed for ediOrderId = " + ediOrderId);
					}
				}
			} else {
				try {
					EdiConfig config = new EdiConfig(requestId, ediConfigDao, sh.getWarehouseCode(), sh.getEtailorId(), SBConstant.MESSAGE_TYPE_ASN);
					asn = new EdiShipmentAsn();
					asn.setEdiOrderId(ediOrderId);
					asn.setEretailorId(sh.getEtailorId());
					asn.setTransCreationDate(config.getTransCreationDate());
					asn.setTransIsTest(config.getTransIsTest());
					asn.setTransControlNumber(config.getTransControlNumber());
					asn.setMessageCount(config.getMessageCount());
					asn.setTransStructureVersion(config.getTransStructureVersion());
					asn.setTransReceivingPartyId(config.getTransReceivingPartyId());
					asn.setTransSendingPartyId(config.getTransSendingPartyId());
					asn.setMessageType(config.getMessageType());
					asn.setMessageIsTest(config.getMessageIsTest());
					asn.setMessageSendingPartyId(config.getMessageSendingPartyId());
					asn.setMessageReceivingPartyId(config.getMessageReceivingPartyId());
					asn.setMessageStructureVersion(config.getMessageStructureVersion());
					asn.setMessageCreationDate(config.getMessageCreationDate());
					asn.setMessageControlNumber(config.getMessageControlNumber());
					asn.setVendorPartyType(config.getVendorPartyType());
					asn.setVendorPartyId(config.getVendorPartyId());
					asn.setWarehouseLocationId(config.getWarehouseCode());
					asn.setPurchaseOrderNumber(sh.getShipmentId());
					asn.setCustomerOrderNumber(sh.getOrderId());
					asn.setResponseCondition(config.getResponseCondition());
					asn.setResultCode(config.getResultCode());
					asn.setResultDescription(config.getResultDescription());
					asn.setPackageId(sh.getPackageId());
					asn.setTrackingId(sh.getTrackingId());
					asn.setManifestId(sh.getManifestId());
					try {
						asn.setReadyToShipDate(this.getReadyToShipDate(requestId, ediOrderId));
					} catch (Exception e) {
						asn.setReadyToShipDate(config.getReadyToShipDate());
					}
					asn.setManifestDate(sh.getManifestDate());
					asn.setPackageWeightUnit(sh.getPackageWeightUnit());
					asn.setPackageWeightValue(sh.getPackageWeightValue());
					asn.setShipMethod(sh.getShipMethod());
					asn.setRunStatus(0);
					asn.setTxnStatus(SBConstant.TXN_STATUS_INIT);
					asn.setProcessInstanceId(requestId);
					asn.setAsnFilePath(config.getArchiveFilepath());
					asn.setTxnErrorMsg(null);
					asn.setCreatedDate(new Date());
					ediShipmentAsnDao.save(asn);
					logger.info(requestId + SBConstant.LOG_SEPRATOR + "createAsn(String requestId, Long ediOrderId)---SUCCESS");
					response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_SUCCESS);
					response.setStatus(SBConstant.TXN_STATUS_SUCCESS);
					response.setUniqueKey(ediOrderId.toString());
					response.setResponseMessage("ASN against ediOrderId = "+ediOrderId+" created succesfully.");
				} catch (Exception e) {
					response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_EXCEPTION);
					response.setStatus(SBConstant.TXN_STATUS_EXCEPTION);
					response.setUniqueKey(null);
					response.setResponseMessage("ASN against ediOrderId = "+ediOrderId+" creation failed.");
					response.setErrorDesc(e.getMessage());
				} finally {
					if (sh != null && response.getStatus().equalsIgnoreCase(SBConstant.TXN_STATUS_SUCCESS)) {
						String topic = SBConstant.MESSAGE_TYPE_ASN;
						OutboundMessage message = new OutboundMessage();
						message.setRequestId(requestId);
						message.setMessageType(topic);
						message.setAsn(asn);
						message.setStatus(SBConstant.TXN_STATUS_INIT);
						logger.info("Kafka Request >>>> " + message.toString());
						boolean status = kafkaUtils.send(requestId, topic, message);
						logger.info(requestId + SBConstant.LOG_SEPRATOR + "ASN #"+ediOrderId+SBConstant.LOG_SEPRATOR+"Kafka produce "+SBConstant.LOG_SEPRATOR+(status == true ? "SUCCESS" : "FAILED"));
						try {
							sh.setIsAsnSend(1);
							ediShipmentDao.save(sh);
						} catch (Exception e) {
							logger.error(requestId + SBConstant.LOG_SEPRATOR + " updation isAsnSend = 1 failed for ediOrderId = " + ediOrderId);
						}
					} else {
						logger.error(requestId + SBConstant.LOG_SEPRATOR + "ASN #"+ediOrderId+SBConstant.LOG_SEPRATOR+"not Kafka produce due to OFR create response = "+response.getStatus());
					}
				}
			} 
		}
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createAsn(String requestId, Long ediOrderId)---END");
		return response;
	}

	@Override
	public String getReadyToShipDate(String requestId, Long ediOrderId) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "isOrderExist(String requestId, int etailorId, String warehouseCode, String shipmentId)---START");
		String readyToShipDate = SBUtils.getTxnSysDateTime();
		try {
			String sql = "SELECT COUNT(1) FROM SELLER.EDI_SHIPMENT_OFR WHERE EDI_ORDER_ID = " + ediOrderId + " AND ORDER_STATUS = 13";
			Long orders = jdbcTemplate.queryForObject(sql, Long.class);
			if (orders == 1) {
				sql = "SELECT TRANS_CREATION_DATE FROM SELLER.EDI_SHIPMENT_OFR WHERE EDI_ORDER_ID = " + ediOrderId + " AND ORDER_STATUS = 13";
				readyToShipDate = jdbcTemplate.queryForObject(sql, String.class);
			}
		} catch (DataAccessException e) {
			logger.error(requestId + SBConstant.LOG_SEPRATOR + "isOrderExist(String requestId, int etailorId, String warehouseCode, String shipmentId)", e);
		}
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "isOrderExist(String requestId, int etailorId, String warehouseCode, String shipmentId)---START");
		return readyToShipDate;
	}

	@Override
	public Long isOfrExistByEdiOrderId(String requestId, Long ediOrderId, int orderStatus) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "isOfrExistByEdiOrderId(String requestId, Long ediOrderId, int orderStatus)---START");
		Long ofrId = 0L;
		try {
			String sql = "SELECT COUNT(1) FROM SELLER.EDI_SHIPMENT_OFR WHERE EDI_ORDER_ID = " + ediOrderId + " AND ORDER_STATUS = " + orderStatus;
			Long ofrs = jdbcTemplate.queryForObject(sql, Long.class);
			if (ofrs == 1) {
				sql = "SELECT OFR_ID FROM SELLER.EDI_SHIPMENT_OFR WHERE EDI_ORDER_ID = " + ediOrderId + " AND ORDER_STATUS = " + orderStatus;
				ofrId = jdbcTemplate.queryForObject(sql, Long.class);
			}
		} catch (DataAccessException e) {
			logger.error(requestId + SBConstant.LOG_SEPRATOR + "isOfrExistByEdiOrderId(String requestId, Long ediOrderId, int orderStatus)", e);
		} 
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "isOfrExistByEdiOrderId(String requestId, Long ediOrderId, int orderStatus)---END");
		return ofrId;
	}
}
 