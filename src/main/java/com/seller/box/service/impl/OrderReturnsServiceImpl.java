package com.seller.box.service.impl;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.seller.box.core.CRN;
import com.seller.box.core.ServiceResponse;
import com.seller.box.dao.EdiConfigDao;
import com.seller.box.dao.EdiShipmentCrnDao;
import com.seller.box.dao.ProductSearchDao;
import com.seller.box.entities.EdiShipmentCrn;
import com.seller.box.entities.ProductDetails;
import com.seller.box.form.EdiInventoryIanForm;
import com.seller.box.service.InventoryService;
import com.seller.box.service.OrderReturnsService;
import com.seller.box.utils.KafkaUtils;
import com.seller.box.utils.SBConstant;
import com.seller.box.utils.SBUtils;

@Service
public class OrderReturnsServiceImpl implements OrderReturnsService {
	private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);
	@Autowired
	KafkaUtils kafkaUtils;
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	EdiConfigDao ediConfigDao;
	@Autowired
	EdiShipmentCrnDao ediShipmentCrnDao;
	@Autowired
	ProductSearchDao productSearchDao;
	@Autowired
	InventoryService inventoryService;
	@Override
	@Async
	public void createOrderReturnsAsync(String requestId, CRN crn, String vendorPartyId, int etailorId, String user) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrderReturnsAsync(String requestId, CRN crn, String vendorPartyId, int etailorId, String user)---START");
		createOrderReturns(requestId, crn, vendorPartyId, etailorId, user);
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrderReturnsAsync(String requestId, CRN crn, String vendorPartyId, int etailorId, String user)---END");
	}

	@Override
	public ServiceResponse createOrderReturns(String requestId, CRN crn, String vendorPartyId, int etailorId, String user) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrderReturns(String requestId, CRN crn, String vendorPartyId, int etailorId, String user)---START");
		ServiceResponse response = new ServiceResponse();
		response.setRequestId(requestId);
		EdiShipmentCrn cr = null;
		if(crn != null) {
			try {
				cr = new EdiShipmentCrn();
				ProductDetails product = productSearchDao.findProduct(etailorId, crn.getItemId());
				//cr.setCrnId(crnId);
				cr.setEretailorId(etailorId);
				//cr.setEretailorName(eretailorName);
				cr.setCustomerOrderNumber(crn.getCustomerOrderNumber());
				cr.setPurchaseOrderNumber(crn.getPurchaseOrderNumber());
				cr.setItemId(crn.getItemId());
				cr.setItemType(crn.getItemType());
				cr.setSkuCode(product.getSkuCode());
				cr.setFnSku(product.getFnsku());
				if(product.getEan() != null) {
					try {
						cr.setEan(Long.parseLong(product.getEan()));
					} catch (NumberFormatException e) {
						logger.error(product.getEan()+" is not a valid EAN");
					}
				}
				cr.setProductName(product.getProductName());
				cr.setThumbnailUrl(product.getThumbnailUrl());
				cr.setQuantity(crn.getQuantity());
				cr.setWarehouseLocationId(crn.getWarehouseLocationId());
				//cr.setBayNo(bayNo);
				//cr.setRackNo(rackNo);
				cr.setVendorPartyType("BUYER_ASSIGNED");
				cr.setVendorPartyId(vendorPartyId);
				cr.setReturnRequestDate(SBUtils.convertGMTtoISTDate(crn.getRequestDateTime()));
				cr.setReturnCondition(crn.getReturnCondition());
				cr.setItemLocation(crn.getInventoryLocation());
				cr.setReturnId(crn.getReturnId());
				cr.setIsOmsNotify(0);
				cr.setIsEdiReturn(0);
				cr.setCreatedBy(user);
				cr.setCreatedDate(new Date());
				cr.setProcessInstanceId(requestId);
				cr.setCrnFilePath(crn.getCrnFilePath());
				//cr.setIanId(ianId);
				//cr.setRefInwardId(refInwardId);
				
				
				cr = ediShipmentCrnDao.save(cr);
				
				response.setStatus(SBConstant.TXN_STATUS_SUCCESS);
				response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_SUCCESS);
				response.setUniqueKey(cr.getCrnId().toString());
				response.setResponseMessage("Return Id '" + crn.getReturnId() +"' for Shipment Id '"+crn.getPurchaseOrderNumber()+"' is created successfully.");
			} catch (Exception e) {
				response.setStatus(SBConstant.TXN_STATUS_EXCEPTION);
				response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_EXCEPTION);
				response.setUniqueKey(cr.getCrnId().toString());
				response.setErrorDesc("Order returns creation failed, General exception occured. \n"+e.getMessage());
				logger.error(requestId + SBConstant.LOG_SEPRATOR + "Exception :: createOrderReturns(String requestId, CRN crn, String vendorPartyId, int etailorId, String user)", e);
			} finally {
				if(response.getStatus().equalsIgnoreCase(SBConstant.TXN_STATUS_SUCCESS)) {
					if(cr.getCrnId() > 0) {
						//GENERATE IAN
						if (SBUtils.getPropertyValue("manifest.etailor.id.list").contains(String.valueOf(etailorId))) {
							if (cr.getItemLocation().equalsIgnoreCase(SBConstant.CRN_INVENTORY_LOCATION_CR)) {
								logger.info(requestId + SBConstant.LOG_SEPRATOR + "IAN for CRN ReturnId '" + cr.getReturnId() + "' " + SBConstant.LOG_SEPRATOR + "START");
								ServiceResponse resp = createIanAgainstOrderReturns(requestId, cr);
								logger.info(requestId + SBConstant.LOG_SEPRATOR + "IAN Response" + SBConstant.LOG_SEPRATOR + resp.toString());
								if (resp.getUniqueKey() != null) {
									cr.setIanId(Long.parseLong(resp.getUniqueKey()));
									cr.setIsEdiReturn(2);
									ediShipmentCrnDao.save(cr);
								}
								logger.info(requestId + SBConstant.LOG_SEPRATOR + "IAN for CRN ReturnId '" + cr.getReturnId() + "' " + SBConstant.LOG_SEPRATOR + "END");
							}
						}
						//TODO MAKE INVENTORY RETURNS
					}
				}
			}			
		} else {
			response.setStatus(SBConstant.TXN_STATUS_FAILURE);
			response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_FAILURE);
			response.setErrorDesc("Order returns creation failed, Order returns metadata is null.");
		}
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrderReturns(...) --> Response = " + response.toString());
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrderReturns(String requestId, CRN crn, String vendorPartyId, int etailorId, String user)---END");
		return response;
	}

	@Override
	public Long isOrderReturnsExist(String requestId, int etailorId, CRN crn) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "isOrderReturnsExist(String requestId, int etailorId, CRN crn)---START");
		Long uniqueId = 0L;
		try {
			String sql = "SELECT COUNT(1) FROM SELLER.EDI_SHIPMENT_CRN WHERE PURCHASE_ORDER_NUMBER = '"+crn.getPurchaseOrderNumber()+"' AND RETURN_ID = '"+crn.getReturnId()+"' AND ITEM_ID = '"+crn.getItemId()+"' AND ERETAILOR_ID = " + etailorId + " AND WAREHOUSE_LOCATION_ID = '"+crn.getWarehouseLocationId()+"'";
			Long orders = jdbcTemplate.queryForObject(sql, Long.class);
			if (orders == 1) {
				sql = "SELECT CRN_ID FROM SELLER.EDI_SHIPMENT_CRN WHERE PURCHASE_ORDER_NUMBER = '"+crn.getPurchaseOrderNumber()+"' AND RETURN_ID = '"+crn.getReturnId()+"' AND ITEM_ID = '"+crn.getItemId()+"' AND ERETAILOR_ID = " + etailorId + " AND WAREHOUSE_LOCATION_ID = '"+crn.getWarehouseLocationId()+"'";
				uniqueId = jdbcTemplate.queryForObject(sql, Long.class);
			} else if(orders > 1) {
				uniqueId = 0L;
			} else {
				uniqueId = -1L; //No crn found
			}
		} catch (DataAccessException e) {
			logger.error(requestId + SBConstant.LOG_SEPRATOR + "isOrderReturnsExist(String requestId, int etailorId, CRN crn)", e);
		}
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "isOrderReturnsExist(String requestId, int etailorId, CRN crn)---START");
		return uniqueId;
	}
	
//	public ProductDetails getProductInfo(int etailorId, String value) {
//		logger.info("getProductInfo(int etailorId, String value)----START");
//		ProductDetails product = null;
//		try {
//			product = productSearchDao.findProduct(etailorId, value);
//		} catch (Exception e) {
//			logger.info("Exception :: getProductInfo(int etailorId, String value)", e);
//		}
//		logger.info("getProductInfo(int etailorId, String value)----START");
//		return product;
//	}

	@Override
	public boolean isOrderReturnsExistByCrnId(String requestId, Long crnId) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "isOrderReturnsExistByCrnId(String requestId, Long crnId)---START");
		boolean isCrn = false;
		try {
			String sql = "SELECT COUNT(1) FROM SELLER.EDI_SHIPMENT_CRN WHERE CRN_ID = "+crnId;
			Long orders = jdbcTemplate.queryForObject(sql, Long.class);
			if (orders == 1) {
				isCrn = true;
			} 
		} catch (DataAccessException e) {
			logger.error(requestId + SBConstant.LOG_SEPRATOR + "isOrderReturnsExistByCrnId(String requestId, Long crnId)", e);
		}
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "isOrderReturnsExistByCrnId(String requestId, Long crnId)---START");
		return isCrn;
	}

	@Override
	public boolean isOrderReturnsProcessedByCrnId(String requestId, Long crnId) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "isOrderReturnsExistByCrnId(String requestId, Long crnId)---START");
		boolean isCrnDone = false;
		try {
			String sql = "SELECT COUNT(1) FROM SELLER.EDI_SHIPMENT_CRN WHERE CRN_ID = "+crnId;
			Long orders = jdbcTemplate.queryForObject(sql, Long.class);
			if (orders == 1) {
				sql = "SELECT IS_EDI_RETURNED FROM SELLER.EDI_SHIPMENT_CRN WHERE CRN_ID = "+crnId+" AND IAN_ID IS NULL";
				int isEdiReturned = jdbcTemplate.queryForObject(sql, Integer.class);
				if(isEdiReturned == 2) {
					isCrnDone = true;
				}
			} 
		} catch (DataAccessException e) {
			logger.error(requestId + SBConstant.LOG_SEPRATOR + "isOrderReturnsExistByCrnId(String requestId, Long crnId)", e);
		}
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "isOrderReturnsExistByCrnId(String requestId, Long crnId)---START");
		return isCrnDone;
	}

	@Override
	public ServiceResponse createIanAgainstOrderReturns(String requestId, EdiShipmentCrn crn) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createIanAgainstOrderReturns(String requestId, EdiShipmentCrn crn)---START");
		EdiInventoryIanForm input = new EdiInventoryIanForm();
		input.setRequestId(requestId);
		input.setWarehouseCode(crn.getWarehouseLocationId());
		input.setEtailorId(crn.getEretailorId());
		input.setSkuCode(crn.getSkuCode());
		input.setFnsku(crn.getFnSku());
		input.setQuantity(crn.getQuantity());
		input.setAdjustmentType(SBConstant.IAN_ADUSTMENT_TYPE_FOUND);
		input.setPurchaseOrderNumber(crn.getPurchaseOrderNumber());
		input.setCreatedBy(crn.getCreatedBy());
		input.setInventorySourceLocation(crn.getItemLocation());
		input.setInventoryDestinationLocation(SBConstant.IAN_LOCATION_TYPE_UNAVAILABLE);
		ServiceResponse response = inventoryService.makeIAN(input);
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "IAN Response"+SBConstant.LOG_SEPRATOR+response.toString());
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createIanAgainstOrderReturns(String requestId, EdiShipmentCrn crn)---END");
		return response;
	}
}
