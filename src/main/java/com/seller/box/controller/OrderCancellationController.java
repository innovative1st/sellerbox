package com.seller.box.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.seller.box.core.OC;
import com.seller.box.core.ServiceResponse;
import com.seller.box.dao.EdiConfigDao;
import com.seller.box.dao.EdiShipmentOCDoa;
import com.seller.box.entities.EdiShipmentOc;
import com.seller.box.service.OrderCancellationService;
import com.seller.box.utils.SBConstant;
import com.seller.box.utils.SBUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/order/cancellation")
@Api(tags = "Order Cancellation", position= 5, value="Order Cancellation", consumes= "application/x-www-form-urlencoded", description="API to retrieve or create cancellation for an order.")
public class OrderCancellationController {
private static final Logger logger = LogManager.getLogger(OrderController.class);
	@Autowired
	OrderCancellationService orderCancellationService;
	@Autowired
	EdiConfigDao ediConfigDao;
	@Autowired
	EdiShipmentOCDoa ediShipmentOCDoa;
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiOperation(value = "API For Synchronous Creating Order Cancellation")
	@PostMapping(value = "/create", consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<ServiceResponse> createOrderCancellationSync(@RequestParam(name="guid") String guid, 
												@RequestParam(name="token") String token, 
												@RequestParam(name = "RequestBody") String body){
		logger.info("createOrderCancellationSync(...)-------START");
		Map<String, Object> bodyMap = null;
		int etailorId				= 0;
		String requestId			= null;
		String warehouseCode		= null;
		String vendorPartyId		= null;
		OC oc						= null;
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
			if(bodyMap.containsKey("OC")) {
				Gson g = new Gson();
				oc = g.fromJson((String)bodyMap.get("OC"), OC.class);
				if(oc != null) {
					warehouseCode = oc.getWarehouseLocationId();
				}
			}
			if(SBUtils.isNull(etailorId) || SBUtils.isNull(warehouseCode) || oc == null) {
				StringBuffer args = new StringBuffer();
				if(SBUtils.isNull(etailorId)) {
					args.append(SBConstant.VAR_ETAILOR_ID);
				}
				if(SBUtils.isNull(warehouseCode)) {
					args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_WAREHOUSE_CODE);
				}
				if(oc == null) {
					args.append(args.length() > 0 ? ", " : "").append("OC body");
				}
				response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_FAILURE);
				response.setStatus(SBConstant.TXN_STATUS_FAILURE);
				response.setResponseMessage("Following arguments "+args.toString()+" is mandarory to create order cancellation.");
				response.setUniqueKey(null);
				response.setErrorDesc("Following arguments "+args.toString()+" is mandarory to create order cancellation.");
			} else {
				if(oc != null) {
					Long uniqueId = orderCancellationService.isOrderCancellationExist(requestId, etailorId, warehouseCode, oc.getPurchaseOrderNumber());
					if(uniqueId >= 0L) {
						String responseMsg = "System is unable to create order cancellation.";
						if(oc != null) {
							if(oc.getPurchaseOrderNumber() != null) {
								responseMsg = "Cancellation for Shipment Id '" + oc.getPurchaseOrderNumber() +"' is unable to process. Due to duplicate request.";
							} 
						}
						response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_DUPLICATE);
						response.setStatus(SBConstant.TXN_STATUS_DUPLICATE);
						response.setResponseMessage(responseMsg);
						response.setUniqueKey(uniqueId.toString());
					} else {
						response = orderCancellationService.createOrderCancellation(requestId, oc, vendorPartyId, etailorId, guid);
					}
				}
			}
		} catch (IOException e) {
			logger.error("IOException :: createOrderCancellationSync(...)", e);
			bodyMap = new HashMap<String, Object>();
			String responseMsg = "System is unable to create order cancellation.";
			if(oc != null) {
				if(oc.getPurchaseOrderNumber() != null) {
					responseMsg = "Shipment Id '" + oc.getPurchaseOrderNumber() +"' is unable to process. Due to duplicate request.";
				} 
			}
			response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_EXCEPTION);
			response.setStatus(SBConstant.TXN_STATUS_EXCEPTION);
			response.setResponseMessage(responseMsg);
			response.setUniqueKey(null);
			response.setErrorDesc(e.getMessage());
		}
		logger.info("createOrderCancellationSync(...) Response = "+response.toString());
		logger.info("createOrderCancellationSync(...)-------END");
		return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiOperation(value = "API For Asynchronous Creating Order Cancellation")
	@PostMapping(value = "/createAsync", consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<ServiceResponse> createOrderCancellationAsync(@RequestParam(name="guid") String guid, 
								 @RequestParam(name="token") String token, 
								 @RequestParam(name = "RequestBody") String body){
		logger.info("createOrderCancellationAsync(...)-------START");
		Map<String, Object> bodyMap = null;
		int etailorId				= 0;
		String requestId			= null;
		String warehouseCode		= null;
		String orderType			= null;
		OC oc						= null;
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
			if(bodyMap.containsKey(SBConstant.VAR_ORDER_TYPE)) {
				orderType = (String) bodyMap.get(SBConstant.VAR_ORDER_TYPE);
			} else {
				orderType = "WMS";
			}
			response.setRequestId(requestId);
			if(bodyMap.containsKey("OC")) {
				Gson g = new Gson();
				oc = g.fromJson((String)bodyMap.get("OC"), OC.class);
				if(oc != null) {
					warehouseCode = oc.getWarehouseLocationId();
				}
			}
			if(SBUtils.isNull(etailorId) || SBUtils.isNull(warehouseCode) || oc == null) {
				StringBuffer args = new StringBuffer();
				if(SBUtils.isNull(etailorId)) {
					args.append(SBConstant.VAR_ETAILOR_ID);
				}
				if(SBUtils.isNull(warehouseCode)) {
					args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_WAREHOUSE_CODE);
				}
				if(oc == null) {
					args.append(args.length() > 0 ? ", " : "").append("OC body");
				}
				response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_FAILURE);
				response.setStatus(SBConstant.TXN_STATUS_FAILURE);
				response.setResponseMessage("Following arguments "+args.toString()+" is mandarory to create order cancellation.");
				response.setUniqueKey(null);
				response.setErrorDesc("Following arguments "+args.toString()+" is mandarory to create order cancellation.");
			} else {
				if( oc != null) {
					Long uniqueId = orderCancellationService.isOrderCancellationExist(requestId, etailorId, warehouseCode, oc.getPurchaseOrderNumber());
					if(uniqueId > 0L) {
						String responseMsg = "System is unable to create order cancellation.";
						if(oc != null) {
							if(oc.getPurchaseOrderNumber() != null) {
								responseMsg = "Cancellation for Shipment Id '" + oc.getPurchaseOrderNumber() +"' is unable to process. Due to duplicate request.";
							} 
						}
						response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_DUPLICATE);
						response.setStatus(SBConstant.TXN_STATUS_DUPLICATE);
						response.setResponseMessage(responseMsg);
						response.setUniqueKey(uniqueId.toString());
					} else {
						//Long ediOrderId = ediConfigDao.getId(SBConstant.MESSAGE_TYPE_OF);
						orderCancellationService.createOrderCancellationAsync(requestId, oc, orderType, etailorId, guid);
						response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_SUCCESS);
						response.setStatus(SBConstant.TXN_STATUS_SUCCESS);
						//response.setUniqueKey(ediOrderId.toString());
						response.setResponseMessage("Cancellation for Shipment Id '" + oc.getPurchaseOrderNumber()  +"' is async submitted to process.");
					}
				}
			}
		} catch (IOException e) {
			logger.error("IOException :: createOrderCancellationAsync(...)", e);
			bodyMap = new HashMap<String, Object>();
			String responseMsg = "System is unable to create order cancellation.";
			if(oc != null) {
				if(oc.getPurchaseOrderNumber() != null) {
					responseMsg = "Cancellation for Shipment Id '" + oc.getPurchaseOrderNumber() +"' is unable to process. Due to duplicate request.";
				} 
			}
			response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_EXCEPTION);
			response.setStatus(SBConstant.TXN_STATUS_EXCEPTION);
			response.setResponseMessage(responseMsg);
			response.setUniqueKey(null);
			response.setErrorDesc(e.getMessage());
		}
		logger.info("createOrderCancellationAsync(...) Response = "+response.toString());
		logger.info("createOrderCancellationAsync(...)-------END");
		return new ResponseEntity<ServiceResponse>(response, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Make OCR entry")
	@PostMapping(value = "/create/ocr", 
			consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<ServiceResponse> createOCR(@RequestParam(name="guid") String guid, 
												@RequestParam(name="token") String token, 
												@RequestParam(name = "RequestBody") String body){
		logger.info("createOCR(...)-------START");
		ServiceResponse response = new ServiceResponse();
		Map<String, Object> bodyMap = null;
		String requestId			= null;
		Long ocId					= 0L;
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey(SBConstant.VAR_REQUEST_ID)) {
				requestId = (String) bodyMap.get(SBConstant.VAR_REQUEST_ID);
			} else {
				requestId = UUID.randomUUID().toString();
			}
			if(bodyMap.containsKey(SBConstant.VAR_OC_ID)) {
				ocId = (Long) bodyMap.get(SBConstant.VAR_OC_ID);
			} 
			response.setRequestId(requestId);
			
			if(SBUtils.isNull(ocId)) {
				response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_FAILURE);
				response.setStatus(SBConstant.TXN_STATUS_FAILURE);
				response.setResponseMessage("System ocId is mandarory to create ofr against order.");
				response.setUniqueKey(null);
				response.setErrorDesc("System ocId both is mandarory to create ofr against order.");
			} else {
				EdiShipmentOc xoc = ediShipmentOCDoa.findByOcId(ocId);
				if(xoc != null) {
					ServiceResponse sr = orderCancellationService.createOcrAgainstOc(requestId, xoc);
					response.setResponseCode(sr.getResponseCode());
					response.setStatus(sr.getStatus());
					response.setResponseMessage(sr.getResponseMessage());
					response.setUniqueKey(sr.getUniqueKey());
					response.setErrorDesc(sr.getErrorDesc());
					logger.info("createOCR(...) Response >>>>"+sr.toString());
				} else {
					response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_NO_DATA);
					response.setStatus(SBConstant.TXN_STATUS_NO_DATA);
					response.setUniqueKey(null);
					response.setResponseMessage("Unable to find order cancellation based on provided ocId = " + ocId);
				}
			}
		} catch (IOException e) {
			logger.error("IOException :: createOCR(...)", e);
			response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_EXCEPTION);
			response.setStatus(SBConstant.TXN_STATUS_EXCEPTION);
			response.setResponseMessage("General exception occured, Unable to create ocr for ocId = "+ ocId);
			response.setUniqueKey(null);
			response.setErrorDesc(e.getMessage());
		}
		return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
	}
}
