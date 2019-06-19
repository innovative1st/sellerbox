package com.seller.box.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.seller.box.core.CRN;
import com.seller.box.core.ServiceResponse;
import com.seller.box.dao.EdiConfigDao;
import com.seller.box.entities.EdiShipmentCrn;
import com.seller.box.service.OrderReturnsService;
import com.seller.box.utils.SBConstant;
import com.seller.box.utils.SBUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/order/returns")
@Api(tags = "Order Returns", position= 4, value="Order Returns", consumes= "application/x-www-form-urlencoded", description="API to retrieve or create return for an order.")
public class OrderReturnsController {
	private static final Logger logger = LogManager.getLogger(OrderController.class);
	
	@Autowired
	OrderReturnsService orderReturnsService;
	@Autowired
	EdiConfigDao ediConfigDao;
	
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiOperation(value = "API For Synchronous Creating Order Returns")
	@PostMapping(value = "/create", consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<ServiceResponse> createOrderReturnsSync(@RequestParam(name="guid") String guid, 
												@RequestParam(name="token") String token, 
												@RequestParam(name = "RequestBody") String body){
		logger.info("createOrderReturnsSync(...)-------START");
		Map<String, Object> bodyMap = null;
		int etailorId				= 0;
		String requestId			= null;
		String warehouseCode		= null;
		String vendorPartyId		= null;
		CRN crn						= null;
		ServiceResponse response = new ServiceResponse();
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey(SBConstant.VAR_ETAILOR_ID)) {
				etailorId = (int) bodyMap.get(SBConstant.VAR_ETAILOR_ID);
			} else {
				etailorId = 0;
			}
			if(bodyMap.containsKey(SBConstant.VAR_WAREHOUSE_CODE)) {
				warehouseCode = (String) bodyMap.get(SBConstant.VAR_WAREHOUSE_CODE);
			} 
			if(bodyMap.containsKey(SBConstant.VAR_REQUEST_ID)) {
				requestId = (String) bodyMap.get(SBConstant.VAR_REQUEST_ID);
			} else {
				requestId = UUID.randomUUID().toString();
			}
			if(bodyMap.containsKey(SBConstant.VAR_VENDOR_PARTY_ID)) {
				vendorPartyId = (String) bodyMap.get(SBConstant.VAR_VENDOR_PARTY_ID);
			} 
			response.setRequestId(requestId);
			if(bodyMap.containsKey("CRN")) {
				Gson g = new Gson();
				crn = g.fromJson((String)bodyMap.get("CRN"), CRN.class);
				if(crn != null) {
					warehouseCode = crn.getWarehouseLocationId();
				}
			}
			if(SBUtils.isNull(etailorId) || SBUtils.isNull(warehouseCode) || crn == null) {
				StringBuffer args = new StringBuffer();
				if(SBUtils.isNull(etailorId)) {
					args.append(SBConstant.VAR_ETAILOR_ID);
				}
				if(SBUtils.isNull(warehouseCode)) {
					args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_WAREHOUSE_CODE);
				}
				if(crn == null) {
					args.append(args.length() > 0 ? ", " : "").append("CRN body");
				}
				response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_FAILURE);
				response.setStatus(SBConstant.TXN_STATUS_FAILURE);
				response.setResponseMessage("Following arguments "+args.toString()+" is mandarory to create order.");
				response.setUniqueKey(null);
				response.setErrorDesc("Following arguments "+args.toString()+" is mandarory to create order.");
			} else {
				if(crn != null) {
					Long uniqueId = orderReturnsService.isOrderReturnsExist(requestId, etailorId, crn);
					if(uniqueId > 0L) {
						String responseMsg = "System is unable to create order return.";
						if(crn != null) {
							if(crn.getPurchaseOrderNumber() != null) {
								responseMsg = "Shipment Id '" + crn.getPurchaseOrderNumber() +"' and Return Id '"+crn.getReturnId()+"' is unable to process. Due to duplicate request.";
							} 
						}
						response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_DUPLICATE);
						response.setStatus(SBConstant.TXN_STATUS_DUPLICATE);
						response.setResponseMessage(responseMsg);
						response.setUniqueKey(uniqueId.toString());
					} else {
						//Long ediOrderId = ediConfigDao.getId(SBConstant.MESSAGE_TYPE_CRN);
						response = orderReturnsService.createOrderReturns(requestId, crn, vendorPartyId, etailorId, guid);
					}
				}
			}
		} catch (IOException e) {
			logger.error("IOException :: createOrderReturnsSync(...)", e);
			bodyMap = new HashMap<String, Object>();
			String responseMsg = "System is unable to create order returns.";
			if(crn != null) {
				if(crn.getPurchaseOrderNumber() != null) {
					responseMsg = "Shipment Id '" + crn.getPurchaseOrderNumber() +"' and Return Id '"+crn.getReturnId()+"' is unable to process. Due to duplicate request.";
				} 
			}
			response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_EXCEPTION);
			response.setStatus(SBConstant.TXN_STATUS_EXCEPTION);
			response.setResponseMessage(responseMsg);
			response.setUniqueKey(null);
			response.setErrorDesc(e.getMessage());
		}
		logger.info("createOrderReturnsSync(...) Response = "+response.toString());
		logger.info("createOrderReturnsSync(...)-------END");
		return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiOperation(value = "API For Asynchronous Creating Order Returns")
	@PostMapping(value = "/createAsync", consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<ServiceResponse> createOrderReturnsAsync(@RequestParam(name="guid") String guid, 
								 @RequestParam(name="token") String token, 
								 @RequestParam(name = "RequestBody") String body){
		logger.info("createOrderReturnsAsync(...)-------START");
		Map<String, Object> bodyMap = null;
		int etailorId				= 0;
		String requestId			= null;
		String warehouseCode		= null;
		String vendorPartyId		= null;
		CRN crn						= null;
		ServiceResponse response = new ServiceResponse();
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey(SBConstant.VAR_ETAILOR_ID)) {
				etailorId = (int) bodyMap.get(SBConstant.VAR_ETAILOR_ID);
			} else {
				etailorId = 94;
			}
			if(bodyMap.containsKey(SBConstant.VAR_REQUEST_ID)) {
				requestId = (String) bodyMap.get(SBConstant.VAR_REQUEST_ID);
			} else {
				requestId = UUID.randomUUID().toString();
			}
			if(bodyMap.containsKey(SBConstant.VAR_VENDOR_PARTY_ID)) {
				vendorPartyId = (String) bodyMap.get(SBConstant.VAR_VENDOR_PARTY_ID);
			} 
			response.setRequestId(requestId);
			if(bodyMap.containsKey("CRN")) {
				Gson g = new Gson();
				crn = g.fromJson((String)bodyMap.get("CRN"), CRN.class);
				if(crn != null) {
					warehouseCode = crn.getWarehouseLocationId();
				}
			}
			if(SBUtils.isNull(etailorId) || SBUtils.isNull(warehouseCode) || crn == null) {
				StringBuffer args = new StringBuffer();
				if(SBUtils.isNull(etailorId)) {
					args.append(SBConstant.VAR_ETAILOR_ID);
				}
				if(SBUtils.isNull(warehouseCode)) {
					args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_WAREHOUSE_CODE);
				}
				if(crn == null) {
					args.append(args.length() > 0 ? ", " : "").append("CRN body");
				}
				response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_FAILURE);
				response.setStatus(SBConstant.TXN_STATUS_FAILURE);
				response.setResponseMessage("Following arguments "+args.toString()+" is mandarory to create order returns.");
				response.setUniqueKey(null);
				response.setErrorDesc("Following arguments "+args.toString()+" is mandarory to create order returns.");
			} else {
				if( crn != null) {
					Long uniqueId = orderReturnsService.isOrderReturnsExist(requestId, etailorId, crn);
					if(uniqueId > 0L) {
						String responseMsg = "System is unable to create order returns.";
						if(crn != null) {
							if(crn.getPurchaseOrderNumber() != null) {
								responseMsg = "Shipment Id '" + crn.getPurchaseOrderNumber() +"' and Return Id '"+crn.getReturnId()+"' is unable to process. Due to duplicate request.";
							} 
						}
						response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_DUPLICATE);
						response.setStatus(SBConstant.TXN_STATUS_DUPLICATE);
						response.setResponseMessage(responseMsg);
						response.setUniqueKey(uniqueId.toString());
					} else {
						//Long ediOrderId = ediConfigDao.getId(SBConstant.MESSAGE_TYPE_OF);
						orderReturnsService.createOrderReturnsAsync(requestId, crn, vendorPartyId, etailorId, guid);
						response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_SUCCESS);
						response.setStatus(SBConstant.TXN_STATUS_SUCCESS);
						//response.setUniqueKey(ediOrderId.toString());
						response.setResponseMessage("Shipment Id '" + crn.getPurchaseOrderNumber()  +"' and Return Id '"+crn.getReturnId()+"' is async submitted to process.");
					}
				}
			}
		} catch (IOException e) {
			logger.error("IOException :: createOrderReturnsAsync(...)", e);
			bodyMap = new HashMap<String, Object>();
			String responseMsg = "System is unable to create order returns.";
			if(crn != null) {
				if(crn.getPurchaseOrderNumber() != null) {
					responseMsg = "Return Shipment Id '" + crn.getPurchaseOrderNumber() +"' and Return Id '"+crn.getReturnId()+"' is unable to process. Due to duplicate request.";
				} 
			}
			response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_EXCEPTION);
			response.setStatus(SBConstant.TXN_STATUS_EXCEPTION);
			response.setResponseMessage(responseMsg);
			response.setUniqueKey(null);
			response.setErrorDesc(e.getMessage());
		}
		logger.info("createOrderReturnsAsync(...) Response = "+response.toString());
		logger.info("createOrderReturnsAsync(...)-------END");
		return new ResponseEntity<ServiceResponse>(response, HttpStatus.CREATED);
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiOperation(value = "API For Creating IAN for Order Returns")
	@PostMapping(value = "/ian/create", consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<ServiceResponse> createIanAgainstOrderReturns(@RequestParam(name="guid") String guid, 
								 @RequestParam(name="token") String token, 
								 @RequestParam(name = "RequestBody") String body){
		logger.info("createIanAgainstOrderReturns(...)-------START");
		Map<String, Object> bodyMap = null;
		int etailorId				= 0;
		String requestId			= null;
		String warehouseCode		= null;
		String vendorPartyId		= null;
		EdiShipmentCrn crn			= null;
		ServiceResponse response = new ServiceResponse();
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey(SBConstant.VAR_ETAILOR_ID)) {
				etailorId = (int) bodyMap.get(SBConstant.VAR_ETAILOR_ID);
			} else {
				etailorId = 94;
			}
			if(bodyMap.containsKey(SBConstant.VAR_REQUEST_ID)) {
				requestId = (String) bodyMap.get(SBConstant.VAR_REQUEST_ID);
			} else {
				requestId = UUID.randomUUID().toString();
			}
			if(bodyMap.containsKey(SBConstant.VAR_VENDOR_PARTY_ID)) {
				vendorPartyId = (String) bodyMap.get(SBConstant.VAR_VENDOR_PARTY_ID);
			} 
			response.setRequestId(requestId);
			if(bodyMap.containsKey("EdiShipmentCrn")) {
				Gson g = new Gson();
				crn = g.fromJson((String)bodyMap.get("EdiShipmentCrn"), EdiShipmentCrn.class);
				if(crn != null) {
					warehouseCode = crn.getWarehouseLocationId();
				}
			}
			if(SBUtils.isNull(etailorId) || SBUtils.isNull(warehouseCode) || crn == null) {
				StringBuffer args = new StringBuffer();
				if(SBUtils.isNull(etailorId)) {
					args.append(SBConstant.VAR_ETAILOR_ID);
				}
				if(SBUtils.isNull(warehouseCode)) {
					args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_WAREHOUSE_CODE);
				}
				if(crn == null) {
					args.append(args.length() > 0 ? ", " : "").append("EdiShipmentCrn body");
				}
				response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_FAILURE);
				response.setStatus(SBConstant.TXN_STATUS_FAILURE);
				response.setResponseMessage("Following arguments "+args.toString()+" is mandarory to create order returns.");
				response.setUniqueKey(null);
				response.setErrorDesc("Following arguments "+args.toString()+" is mandarory to create order returns.");
			} else {
				if( crn != null) {
					boolean isCrn = orderReturnsService.isOrderReturnsExistByCrnId(requestId, crn.getCrnId());
					if(isCrn) {
						boolean isCrnDone = orderReturnsService.isOrderReturnsProcessedByCrnId(requestId, crn.getCrnId());
						if (isCrnDone) {
							response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_DUPLICATE);
							response.setStatus(SBConstant.TXN_STATUS_DUPLICATE);
							//response.setUniqueKey(crn.getCrnId().toString());
							response.setResponseMessage("Ian against Shipment Id '" + crn.getPurchaseOrderNumber() + "' and Return Id '" + crn.getReturnId() + "' is already processed.");
						} else {
							if (crn.getItemLocation().equalsIgnoreCase(SBConstant.CRN_INVENTORY_LOCATION_CR)) {
								ServiceResponse ian = orderReturnsService.createIanAgainstOrderReturns(requestId, crn);
								response.setResponseCode(ian.getResponseCode());
								response.setStatus(ian.getStatus());
								response.setUniqueKey(ian.getUniqueKey());
								response.setResponseMessage(ian.getResponseMessage());
								response.setErrorDesc(ian.getErrorDesc());
							} else {
								response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_FAILURE);
								response.setStatus(SBConstant.TXN_STATUS_FAILURE);
								//response.setUniqueKey(crn.getCrnId().toString());
								response.setResponseMessage("Ian against Shipment Id '" + crn.getPurchaseOrderNumber() + "' and Return Id '" + crn.getReturnId() + "' is not valid for ItemLocation = "+crn.getItemLocation());
							}
						}
					} else {
						String responseMsg = "System is unable to find order returns.";
						if(crn != null) {
							if(crn.getPurchaseOrderNumber() != null) {
								responseMsg = "Shipment Id '" + crn.getPurchaseOrderNumber() +"' and Return Id '"+crn.getReturnId()+"' is unable to find.";
							} 
						}
						response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_NO_DATA);
						response.setStatus(SBConstant.TXN_STATUS_NO_DATA);
						response.setResponseMessage(responseMsg);
					}
				}
			}
		} catch (IOException e) {
			logger.error("IOException :: createIanAgainstOrderReturns(...)", e);
			bodyMap = new HashMap<String, Object>();
			String responseMsg = "System is unable to create order returns.";
			if(crn != null) {
				if(crn.getPurchaseOrderNumber() != null) {
					responseMsg = "Return Shipment Id '" + crn.getPurchaseOrderNumber() +"' and Return Id '"+crn.getReturnId()+"' is unable to process. Due to duplicate request.";
				} 
			}
			response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_EXCEPTION);
			response.setStatus(SBConstant.TXN_STATUS_EXCEPTION);
			response.setResponseMessage(responseMsg);
			response.setUniqueKey(null);
			response.setErrorDesc(e.getMessage());
		}
		logger.info("createIanAgainstOrderReturns(...) Response = "+response.toString());
		logger.info("createIanAgainstOrderReturns(...)-------END");
		return new ResponseEntity<ServiceResponse>(response, HttpStatus.CREATED);
	}
}
