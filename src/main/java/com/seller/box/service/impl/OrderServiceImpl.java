package com.seller.box.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.seller.box.core.OF;
import com.seller.box.core.OFItem;
import com.seller.box.core.ServiceResponse;
import com.seller.box.dao.EdiBoxTypeDao;
import com.seller.box.dao.EdiConfigDao;
import com.seller.box.dao.EdiShipmentDao;
import com.seller.box.dao.ProductMeasurementDao;
import com.seller.box.dao.ProductSearchDao;
import com.seller.box.entities.EdiBoxType;
import com.seller.box.entities.EdiShipmentHdr;
import com.seller.box.entities.EdiShipmentItems;
import com.seller.box.entities.ProductDetail;
import com.seller.box.entities.ProductDetails;
import com.seller.box.entities.ProductMeasurements;
import com.seller.box.service.OrderService;
import com.seller.box.utils.SBConstant;
import com.seller.box.utils.SBUtils;
@Service
public class OrderServiceImpl implements OrderService {
	private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);
	@Autowired
	EdiConfigDao ediConfigDao;
	@Autowired
	EdiShipmentDao ediShipmentDao;
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	ProductSearchDao productSearchDao;
	@Autowired
	ProductMeasurementDao productMeasurementDao;
	@Autowired
	EdiBoxTypeDao ediBoxTypeDao;
	
	@Override
	@Async
	public void createOrderAsync(String requestId, OF of, int etailorId, Long ediOrderId, String user) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrderAsync(String requestId, OF of, int etailorId, Long ediOrderId, String user)---START");
		createOrder(requestId, of, etailorId, ediOrderId, user);
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrderAsync(String requestId, OF of, int etailorId, Long ediOrderId, String user)---END");
	}

	@Override
	public ServiceResponse createOrder(String requestId, OF of, int etailorId, Long ediOrderId, String user) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrder(String requestId, OF of, int etailorId, Long ediOrderId, String user)---START");
		ServiceResponse response = new ServiceResponse();
		if(of != null) {
			try {
				EdiShipmentHdr shipment = new EdiShipmentHdr();
				response.setUniqueKey(ediOrderId.toString());
				response.setRequestId(requestId);
				
				shipment.setEdiOrderId(ediOrderId);
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
								ProductDetails product = this.getProductInfo(etailorId, value);
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
		Long uniqueId = 0L;
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
		return uniqueId;
	}
	
	public ProductDetails getProductInfo(int etailorId, String value) {
		logger.info("getProductInfo(int etailorId, String value)----START");
		ProductDetails product = null;
		try {
			product = productSearchDao.findProduct(etailorId, value);
		} catch (Exception e) {
			logger.info("Exception :: getProductInfo(int etailorId, String value)", e);
		}
		logger.info("getProductInfo(int etailorId, String value)----START");
		return product;
	}

	@Override
	public boolean isOrderExistByEdiOrderId(String requestId, Long ediOrderId) {
		boolean flag = false;
		String sql = "SELECT COUNT(1) FROM SELLER.EDI_SHIPMENT_HDR WHERE EDI_ORDER_ID = " + ediOrderId;
		Long orders = jdbcTemplate.queryForObject(sql, Long.class);
		if (orders == 1) {
			flag = true;
		} 
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
}
 