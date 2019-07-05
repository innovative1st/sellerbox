package com.seller.box.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.seller.box.core.OF;
import com.seller.box.core.PendingItems;
import com.seller.box.core.ServiceResponse;
import com.seller.box.dao.EdiConfigDao;
import com.seller.box.dao.EdiShipmentDao;
import com.seller.box.dao.OrderDao;
import com.seller.box.dao.ShipmentInvoiceDao;
import com.seller.box.entities.EdiPicklist;
import com.seller.box.entities.EdiShipmentInvoice;
import com.seller.box.entities.ReadyToShip;
import com.seller.box.entities.ShipmentHdrWithItem;
import com.seller.box.exception.NoDataFoundException;
import com.seller.box.exception.SellerClientException;
import com.seller.box.exception.SellerServiceException;
import com.seller.box.exception.SellerServiceException.ErrorType;
import com.seller.box.form.PicklistSearchForm;
import com.seller.box.form.Shipment;
import com.seller.box.service.OrderService;
import com.seller.box.utils.SBConstant;
import com.seller.box.utils.SBUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/order")
@Api(tags = "Order", position= 3, value="Order", consumes= "application/x-www-form-urlencoded", description="API to retrieve or manipulate Order related information.")
public class OrderController {
	private static final Logger logger = LogManager.getLogger(OrderController.class);
	
	@Autowired
	OrderDao orderDao;
	@Autowired
	EdiShipmentDao shipmentDao;
	@Autowired
	ShipmentInvoiceDao invoiceDao;
	@Autowired
	OrderService orderService;
	@Autowired
	EdiConfigDao ediConfigDao;
	
	
	@ApiOperation(value = "View new shipments")
	@RequestMapping(value = "/view/new", 
					method = RequestMethod.POST,
					consumes = {MediaType.ALL_VALUE}
					)
	public ResponseEntity<List<ShipmentHdrWithItem>> viewNewShipments(@RequestParam(name="guid") String guid, 
																	  @RequestParam(name="token") String token, 
																	  @RequestParam(name = "RequestBody") String body){
		logger.info("viewNewShipments(...)-------START");
		PicklistSearchForm criteria = new PicklistSearchForm();
		try {
			criteria = new ObjectMapper().readValue(body, PicklistSearchForm.class);
		} catch (IOException e) {
			logger.error("IOException :: viewNewShipments(...)", e);
		}
		
		List<ShipmentHdrWithItem> newShipments = null;
		if(SBUtils.isNull(criteria.getWarehouseCode()) || criteria.getEtailorId() == 0 || SBUtils.isNull(criteria.getExSDAfter()) || SBUtils.isNull(criteria.getExSDBefore())) {
			StringBuffer args = new StringBuffer();
			if(SBUtils.isNull(criteria.getWarehouseCode())) {
				args.append(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(criteria.getEtailorId() == 0) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_ETAILOR_ID);
			}
			if(SBUtils.isNull(criteria.getUsername())) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_USERNAME);
			}
			logger.error("Following arguments "+args+" is mandatory to viewNewShipments(...)");
			throw new SellerClientException("Following arguments "+args+" is mandatory to viewNewShipments(...)");
		} else {
			newShipments = (List<ShipmentHdrWithItem>) orderDao.findNewShipments(criteria);
			if(newShipments.isEmpty() || newShipments== null) {
				NoDataFoundException sse = new NoDataFoundException("There are no shipment in new status based on provided criteria.");
				sse.setErrorType(SBConstant.ErrorType.DATA_NOT_FOUND.name());
				sse.setErrorCode(SBConstant.ERROR_CODE_DATA_NOT_FOUND);
				sse.setRequestId(UUID.randomUUID().toString());
				sse.setServiceName("/order/view/new");
				logger.error("NoDataFoundException :: viewNewShipments(...)", sse.toString());
				throw sse;
			} else {
				logger.info("viewNewShipments(...)-------END");
				return new ResponseEntity<List<ShipmentHdrWithItem>>(newShipments, HttpStatus.OK);
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@ApiOperation(value = "Create picklist")
	@PostMapping(value = "/create/picklist", 
		 	 consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<String> createPicklist(@RequestParam(name="guid") String guid, 
												 @RequestParam(name="token") String token, 
												 @RequestParam(name = "RequestBody") String body){
		logger.info("createPicklist(...)-------START");
		PicklistSearchForm criteria = new PicklistSearchForm();
		try {
			criteria = new ObjectMapper().readValue(body, PicklistSearchForm.class);
		} catch (IOException e) {
			logger.error("IOException :: createPicklist(...)", e);
		}
		
		Map<String, String> response = null; 
		if(SBUtils.isNull(criteria.getWarehouseCode()) || criteria.getEtailorId() == 0 || criteria.getBatchSize() == 0 || SBUtils.isNull(criteria.getOrderIds())) {
			StringBuffer args = new StringBuffer();
			if(SBUtils.isNull(criteria.getWarehouseCode())) {
				args.append(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(criteria.getEtailorId() == 0) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_ETAILOR_ID);
			}
			if(SBUtils.isNull(criteria.getOrderIds())) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_ORDER_IDS);
			}
			if(criteria.getBatchSize() == 0) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_BATCH_SIZE);
			}
			if(SBUtils.isNull(criteria.getUsername())) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_USERNAME);
			}
			throw new SellerClientException("Following arguments "+args.toString()+" is mandarory to createPicklist(...)");
		} else {
			if(criteria.getBatchSize() > 0 && criteria.getOrderIds().split(",").length > 0) {
				response = orderDao.createPicklist(criteria);
				if(response != null) {
					if (!((String)response.get(SBConstant.TXN_RESPONSE_STATUS)).equalsIgnoreCase(SBConstant.TXN_STATUS_SUCCESS)) {
						SellerServiceException sse = new SellerServiceException(
								"Transaction failed to create picklist, Please retry again after few min.");
						sse.setErrorCode(SBConstant.ERROR_CODE_SERVICE_ERROR);
						sse.setErrorType(sse.getErrorType().SERVICE_ERROR);
						sse.setRequestId(response.get(SBConstant.TXN_RESPONSE_REQUEST_ID));
						sse.setServiceName("/order/create/picklist");
						logger.error("SellerServiceException :: createPicklist()", sse.toString());
						throw sse;
					} else {
						String resp = "service: createPicklist()" + ", " + "requestStatus: " + response.get(SBConstant.TXN_RESPONSE_STATUS)+ "requestId: " + response.get(SBConstant.TXN_RESPONSE_REQUEST_ID) + ", " + "picklistId: " + response.get(SBConstant.TXN_RESPONSE_PICKLIST_ID) + ", " + "picklistNumber: " + response.get(SBConstant.TXN_RESPONSE_PICKLIST_NUMBER);
						logger.info(resp);
						return new ResponseEntity<>(resp, HttpStatus.OK);
					}
				}
			} else {
				logger.error("Batch size is mandatory and it should be more then 0 to create picklist.");
				throw new SellerClientException("Batch size is mandatory and it should be more then 0 to create picklist.");
			}
		}
		logger.info("createPicklist(...)-------END");
		return null;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "View picklist")
	@PostMapping(value = "/readyToPick", 
			 consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<List<EdiPicklist>> getReadyToPickList(@RequestParam(name="guid") String guid, 
																@RequestParam(name="token") String token, 
																@RequestParam(name = "RequestBody") String body){
		logger.info("getReadyToPickList(...)-------START");
		Map<String, Object> bodyMap = null;
		String etailorId			= null; 
		String warehouseCode		= null;
		String picklistFor			= null;
		String picklistStatus		= null;
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey(SBConstant.VAR_ETAILOR_ID)) {
				etailorId = (String) bodyMap.get(SBConstant.VAR_ETAILOR_ID);
			}
			if(bodyMap.containsKey(SBConstant.VAR_WAREHOUSE_CODE)) {
				warehouseCode = (String) bodyMap.get(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(bodyMap.containsKey(SBConstant.VAR_PICKLIST_FOR)) {
				picklistFor = (String) bodyMap.get(SBConstant.VAR_PICKLIST_FOR);
			}
			if(bodyMap.containsKey(SBConstant.VAR_PICKLIST_STATUS)) {
				picklistStatus = (String) bodyMap.get(SBConstant.VAR_PICKLIST_STATUS);
			}
		} catch (IOException e) {
			bodyMap = new HashMap<String, Object>();
			logger.error("IOException :: getReadyToPickList(...)", e);
		}
		
		List<EdiPicklist> picklist = null;
		if(SBUtils.isNull(etailorId) || SBUtils.isNull(warehouseCode) || SBUtils.isNull(picklistFor) || SBUtils.isNull(picklistStatus)) {
			StringBuffer args = new StringBuffer();
			if(SBUtils.isNull(warehouseCode)) {
				args.append(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(SBUtils.isNull(etailorId)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_ETAILOR_ID);
			}
			if(SBUtils.isNull(picklistFor)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_PICKLIST_FOR);
			}
			if(SBUtils.isNull(picklistStatus)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_PICKLIST_STATUS);
			}
			logger.error("SellerClientException :: getReadyToPickList(...)", "Following arguments "+args.toString()+" is mandarory to getReadyToPickList(...)");
			throw new SellerClientException("Following arguments "+args.toString()+" is mandarory to getReadyToPickList(...)");
		} else {
			picklist = (List<EdiPicklist>) orderDao.findReadyToPick(etailorId, warehouseCode, picklistFor, picklistStatus);
			if(picklist == null) {
				NoDataFoundException sse = new NoDataFoundException("There are no picklist based on provided criteria.");
				sse.setErrorType(SBConstant.ErrorType.DATA_NOT_FOUND.name());
				sse.setErrorCode(SBConstant.ERROR_CODE_DATA_NOT_FOUND);
				sse.setRequestId(UUID.randomUUID().toString());
				sse.setServiceName("/order/readyToPick");
				logger.error("NoDataFoundException :: getReadyToPickList(...)", sse.toString());
				throw sse;
			} else {
				logger.info("getReadyToPickList(...)-------END");
				return new ResponseEntity<List<EdiPicklist>>(picklist, HttpStatus.OK);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "View ready shipments")
	@ApiImplicitParams(
			{ 
				@ApiImplicitParam(name = "RequestBody", dataType = "String", required = true, paramType = "query", value = "<table border=\"1\"><tr><td width=\"100\"><strong>Field Name </strong></td><td width=\"100\"><strong>Data Type </strong></td><td align=\"center\" width=\"100\"><strong>Mandatory </strong></td><td width=\"300\"><strong>Description </strong></td></tr><tr style=\"background: #ccc !important;\"><td>warehouseCode</td><td>Varchar(10)</td><td align=\"center\">Mandatory</td><td>eRetail Warehouse Code. API will pick Loc Code from WMS API configuration.</td></tr><tr><td>etailorId</td><td>int(3)</td><td>Mandatory</td><td>eRetail Id</td></tr><tr><td>pickupDate</td><td>Varchar(20)</td><td align=\"center\">Mandatory</td><td>Pickup date type is mandatory to fetch data for specific time. All possible value - ALL/TODAY/YESTERDAY/LAST7DAYS/OTHER</td></tr><tr><td>pickupOtherDate</td><td>Varchar(20)</td><td align=\"center\">Optional</td><td>If pickupDate = \"OTHER\" then pickupOtherDate is mandatory and date format should be 'dd-MM-yyyy'.</td></tr><tr><td>carrierName</td><td>Varchar(20)</td><td align=\"center\">Mandatory</td><td>Need to provide specific carrier name else 'ALL'.</td></tr><tr><td colspan=\"4\"><p><strong>Input JSON Format- <br/>{<br/>&nbsp; &nbsp;\"warehouseCode\": \"XXXX\",<br/>&nbsp; &nbsp;\"etailorId\": 101,<br/>&nbsp; &nbsp;\"pickupDate\": \"OTHER\",<br/>&nbsp; &nbsp;\"pickupOtherDate\": \"31-03-2019\",<br/>&nbsp; &nbsp;\"carrierName\": \"ALL\"<br/>}</strong></p></td></tr></table>"),
				@ApiImplicitParam(name = "guid", dataType = "String", required = true, paramType = "query", value = "API Owner"),
				@ApiImplicitParam(name = "token", dataType = "String", required = true, paramType = "query", value = "API Key")
			})
	@PostMapping(value = "/readyToShip", consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<List<ReadyToShip>> getReadyToShipList(@RequestParam(name="guid") String guid, 
																@RequestParam(name="token") String token, 
																@RequestParam(name = "RequestBody") String body){
		logger.info("getReadyToShipList(...)-------START");
		Map<String, Object> bodyMap = null;
		String etailorId			= null; 
		String warehouseCode		= null;
		String pickupDate			= null;
		String pickupOtherDate		= null;
		String carrierName			= null;
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey(SBConstant.VAR_ETAILOR_ID)) {
				etailorId = (String) bodyMap.get(SBConstant.VAR_ETAILOR_ID);
			}
			if(bodyMap.containsKey(SBConstant.VAR_WAREHOUSE_CODE)) {
				warehouseCode = (String) bodyMap.get(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(bodyMap.containsKey(SBConstant.VAR_PICKUP_DATE)) {
				pickupDate = (String) bodyMap.get(SBConstant.VAR_PICKUP_DATE);
			}
			if(bodyMap.containsKey(SBConstant.VAR_PICKUP_OTHER_DATE)) {
				pickupOtherDate = (String) bodyMap.get(SBConstant.VAR_PICKUP_OTHER_DATE);
			}
			if(bodyMap.containsKey(SBConstant.VAR_CARRIER_NAME)) {
				carrierName = (String) bodyMap.get(SBConstant.VAR_CARRIER_NAME);
			}
		} catch (IOException e) {
			bodyMap = new HashMap<String, Object>();
			logger.error("IOException :: getReadyToShipList(...)", e);
		}
		List<ReadyToShip> readyShipment = null;
		if(SBUtils.isNull(etailorId) || SBUtils.isNull(warehouseCode) || SBUtils.isNull(pickupDate) || SBUtils.isNull(carrierName)) {
			StringBuffer args = new StringBuffer();
			if(SBUtils.isNull(warehouseCode)) {
				args.append(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(SBUtils.isNull(etailorId)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_ETAILOR_ID);
			}
			if(SBUtils.isNull(pickupDate)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_PICKUP_DATE);
			}
			if(SBUtils.isNull(carrierName)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_CARRIER_NAME);
			}
			logger.error("SellerClientException :: getReadyToShipList(...)", "Following arguments "+args.toString()+" is mandarory to getReadyToShipList(...)");
			throw new SellerClientException("Following arguments "+args.toString()+" is mandarory to getReadyToShipList(...)");
		} else {
			readyShipment = (List<ReadyToShip>) orderDao.findReadyToShip(etailorId, warehouseCode, pickupDate, pickupOtherDate, carrierName);
			if(readyShipment == null) {
				NoDataFoundException sse = new NoDataFoundException("There are no picklist based on provided criteria.");
				sse.setErrorType(SBConstant.ErrorType.DATA_NOT_FOUND.name());
				sse.setErrorCode(SBConstant.ERROR_CODE_DATA_NOT_FOUND);
				sse.setRequestId(UUID.randomUUID().toString());
				sse.setServiceName("/order/readyToShip");
				logger.error("NoDataFoundException :: getReadyToShipList(...)", sse.toString());
				throw sse;
			} else {
				logger.info("getReadyToShipList(...)-------END");
				return new ResponseEntity<List<ReadyToShip>>(readyShipment, HttpStatus.OK);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "View picklist status")
	@PostMapping(value = "/picklistStatus", 
			consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<Map<String, Object>> getPicklistStatus(@RequestParam(name="guid") String guid, 
																@RequestParam(name="token") String token, 
																@RequestParam(name = "RequestBody") String body){
		logger.info("getPicklistStatus(...)-------START");
		Map<String, Object> bodyMap = null;
		String etailorId			= null; 
		String warehouseCode		= null;
		String scanedValue		= null;
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey(SBConstant.VAR_ETAILOR_ID)) {
				etailorId = (String) bodyMap.get(SBConstant.VAR_ETAILOR_ID);
			}
			if(bodyMap.containsKey(SBConstant.VAR_WAREHOUSE_CODE)) {
				warehouseCode = (String) bodyMap.get(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(bodyMap.containsKey(SBConstant.VAR_SCANED_VALUE)) {
				scanedValue = (String) bodyMap.get(SBConstant.VAR_SCANED_VALUE);
			}
		} catch (IOException e) {
			bodyMap = new HashMap<String, Object>();
		}
		Map<String, Object> response = null;
		if(SBUtils.isNull(etailorId) || SBUtils.isNull(warehouseCode) || SBUtils.isNull(scanedValue)) {
			StringBuffer args = new StringBuffer();
			if(SBUtils.isNull(warehouseCode)) {
				args.append(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(SBUtils.isNull(etailorId)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_ETAILOR_ID);
			}
			if(SBUtils.isNull(scanedValue)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_SCANED_VALUE);
			}
			logger.info("getPicklistStatus(...)-------Arguments missing");
			throw new SellerClientException("Following arguments "+args.toString()+" is mandarory to getPicklistStatus(...)");
		} else {
			response = orderDao.findPicklistStatus(etailorId, warehouseCode, scanedValue);
			if(response == null) {
				logger.info("getPicklistStatus(...)-------Response Null");
				NoDataFoundException sse = new NoDataFoundException("There are no picklist based on provided criteria.");
				sse.setErrorType(SBConstant.ErrorType.DATA_NOT_FOUND.name());
				sse.setErrorCode(SBConstant.ERROR_CODE_DATA_NOT_FOUND);
				sse.setRequestId(UUID.randomUUID().toString());
				sse.setServiceName("/order/readyToShip");
				throw sse;
			} else {
				logger.info("getPicklistStatus(...)-------END");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Get shipment details")
	@PostMapping(value = "/getShipment", 
			consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<Shipment> getShipment(@RequestParam(name="guid") String guid, 
												@RequestParam(name="token") String token, 
												@RequestParam(name = "RequestBody") String body){
		logger.info("getShipment(...)-------START");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String remoteAddress = request.getRemoteAddr();
		System.out.println(remoteAddress);
		Map<String, Object> bodyMap = null;
		int etailorId		= 0; 
		String warehouseCode= null;
		String picklistNumber= null;
		String ean			= null;
		Long ediOrderId 	= 0L;
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey(SBConstant.VAR_ETAILOR_ID)) {
				try {
					etailorId = Integer.parseInt(String.valueOf(bodyMap.get(SBConstant.VAR_ETAILOR_ID)));
				} catch (NumberFormatException e) {
					logger.error("NumberFormatException while parsing etailorId.");
				}
			}
			if(bodyMap.containsKey(SBConstant.VAR_WAREHOUSE_CODE)) {
				warehouseCode = (String) bodyMap.get(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(bodyMap.containsKey(SBConstant.VAR_PICKLIST_NUMBER)) {
				picklistNumber = (String) bodyMap.get(SBConstant.VAR_PICKLIST_NUMBER);
			}
			if(bodyMap.containsKey(SBConstant.VAR_EAN)) {
				ean = (String) bodyMap.get(SBConstant.VAR_EAN);
			}
			if(bodyMap.containsKey(SBConstant.VAR_EDI_ORDER_ID)) {
				ediOrderId = (Long) bodyMap.get(SBConstant.VAR_EDI_ORDER_ID);
			}
		} catch (IOException e) {
			bodyMap = new HashMap<String, Object>();
			logger.error("IOException :: getShipment(...)", e);
		}
		Shipment shipment = null;
		if(SBUtils.isNull(etailorId) || SBUtils.isNull(warehouseCode) || SBUtils.isNull(picklistNumber) || SBUtils.isNull(ean)) {
			StringBuffer args = new StringBuffer();
			if(SBUtils.isNull(warehouseCode)) {
				args.append(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(SBUtils.isNull(picklistNumber)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_PICKLIST_NUMBER);
			}
			if(SBUtils.isNull(etailorId)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_ETAILOR_ID);
			}
			if(SBUtils.isNull(ean)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_SCANED_VALUE);
			}
			logger.error("SellerClientException :: getShipment(...)", "Following arguments "+args.toString()+" is mandarory to getShipment(...)");
			NoDataFoundException sse = new NoDataFoundException("Following arguments "+args.toString()+" is mandarory to get shipment info.");
			sse.setErrorType(SBConstant.ErrorType.ARGUMENT_MISSING.name());
			sse.setErrorCode(SBConstant.ERROR_CODE_ARGUMENT_MISSING);
			sse.setRequestId(UUID.randomUUID().toString());
			sse.setServiceName("/order/getShipment");
			throw sse;//new SellerClientException("Following arguments "+args.toString()+" is mandarory to shipment info.");
		} else {
			if(ediOrderId.equals(0L)) {
				ediOrderId = orderDao.getEdiOrderIdForPacking(etailorId, warehouseCode, picklistNumber, ean);
			}
			if(!ediOrderId.equals(0L)) {
				shipment = orderService.getShipmentForPacking(ediOrderId, null);
			}
			if(shipment == null) {
				NoDataFoundException sse = new NoDataFoundException("There are no more order for scaned "+ean);
				sse.setErrorType(SBConstant.ErrorType.DATA_NOT_FOUND.name());
				sse.setErrorCode(SBConstant.ERROR_CODE_DATA_NOT_FOUND);
				sse.setRequestId(UUID.randomUUID().toString());
				sse.setServiceName("/order/getShipment");
				logger.error("NoDataFoundException :: getShipment(...)", sse.toString());
				throw sse;
			} else {
				logger.info("getShipment(...)-------END");
				return new ResponseEntity<Shipment>(shipment, HttpStatus.OK);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Make shipment sideline")
	@PostMapping(value = "/sideline", 
			consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<Map<String, Object>> sidelineShipment(@RequestParam(name="guid") String guid, 
												@RequestParam(name="token") String token, 
												@RequestParam(name = "RequestBody") String body){
		logger.info("sidelineShipment(...)-------START");
		Map<String, Object> bodyMap = null;
		int etailorId		= 0; 
		String warehouseCode= null;
		Long ediOrderId 	= 0L;
		String picklistNumber=null;
		String sidelineReason = null;
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey(SBConstant.VAR_ETAILOR_ID)) {
				try {
					etailorId = Integer.parseInt(String.valueOf(bodyMap.get(SBConstant.VAR_ETAILOR_ID)));
				} catch (NumberFormatException e) {
					logger.error("NumberFormatException while parsing etailorId.");
				}
			}
			if(bodyMap.containsKey(SBConstant.VAR_WAREHOUSE_CODE)) {
				warehouseCode = (String) bodyMap.get(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(bodyMap.containsKey(SBConstant.VAR_EDI_ORDER_ID)) {
				try {
					ediOrderId = Long.parseLong(String.valueOf(bodyMap.get(SBConstant.VAR_EDI_ORDER_ID)));
				} catch (NumberFormatException e) {
					logger.error("NumberFormatException while parsing ediOrderId.");
				}
			}
			if(bodyMap.containsKey(SBConstant.VAR_PICKLIST_NUMBER)) {
				picklistNumber = (String) bodyMap.get(SBConstant.VAR_PICKLIST_NUMBER);
			}
			if(bodyMap.containsKey(SBConstant.VAR_SIDELINE_REASON)) {
				sidelineReason = (String) bodyMap.get(SBConstant.VAR_SIDELINE_REASON);
			}
		} catch (IOException e) {
			bodyMap = new HashMap<String, Object>();
			logger.error("IOException :: sidelineShipment(...)", e);
		}
		if(SBUtils.isNull(etailorId) || SBUtils.isNull(warehouseCode) || SBUtils.isNull(sidelineReason) || SBUtils.isNull(picklistNumber)) {
			StringBuffer args = new StringBuffer();
			if(SBUtils.isNull(warehouseCode)) {
				args.append(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(SBUtils.isNull(etailorId)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_ETAILOR_ID);
			}
			if(SBUtils.isNull(sidelineReason)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_SIDELINE_REASON);
			}
			if(SBUtils.isNull(picklistNumber)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_PICKLIST_NUMBER);
			}
			logger.error("SellerClientException :: sidelineShipment(...)", "Following arguments "+args.toString()+" is mandarory to make sideline shipment.");
			NoDataFoundException sse = new NoDataFoundException("Following arguments "+args.toString()+" is mandarory to make sideline shipment.");
			sse.setErrorType(SBConstant.ErrorType.ARGUMENT_MISSING.name());
			sse.setErrorCode(SBConstant.ERROR_CODE_ARGUMENT_MISSING);
			sse.setRequestId(UUID.randomUUID().toString());
			sse.setServiceName("/order/sideline");
			throw sse;//new SellerClientException("Following arguments "+args.toString()+" is mandarory to shipment info.");
		} else {
			String result = null;
			if(!ediOrderId.equals(0L)) {
				result = orderService.makeSidelineShipment(orderDao, ediOrderId, sidelineReason, guid);
			}
			if(result == null) {
				NoDataFoundException sse = new NoDataFoundException("Unable to make sideline shipment for ediOrderId = "+ediOrderId);
				sse.setErrorType(SBConstant.ErrorType.DATA_NOT_FOUND.name());
				sse.setErrorCode(SBConstant.ERROR_CODE_DATA_NOT_FOUND);
				sse.setRequestId(UUID.randomUUID().toString());
				sse.setServiceName("/order/sideline");
				logger.error("NoDataFoundException :: sidelineShipment(...)", sse.toString());
				throw sse;
			} else {
				if(result == SBConstant.TXN_STATUS_SUCCESS) {
					response = orderDao.findPicklistStatus(String.valueOf(etailorId), warehouseCode, picklistNumber);
					
				} else {
					response.put("status", SBConstant.TXN_STATUS_FAILURE);
					response.put("message", "Unable to make sideline, Please skip the order and connect with support team.");
				}
				logger.info("sidelineShipment(...)-------END");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Skip shipment process")
	@PostMapping(value = "/skipShipment", 
			consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<Map<String, String>> skipShipment(@RequestParam(name="guid") String guid, 
												@RequestParam(name="token") String token, 
												@RequestParam(name = "RequestBody") String body){
		logger.info("skipShipment(...)-------START");
		Map<String, Object> bodyMap = null;
		int etailorId		= 0; 
		String warehouseCode= null;
		Long ediOrderId 	= 0L;
		String reasonForSkip= null;
		Map<String, String> status 		= null;
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey(SBConstant.VAR_ETAILOR_ID)) {
				try {
					etailorId = Integer.parseInt(String.valueOf(bodyMap.get(SBConstant.VAR_ETAILOR_ID)));
				} catch (NumberFormatException e) {
					logger.error("NumberFormatException while parsing etailorId.");
				}
			}
			if(bodyMap.containsKey(SBConstant.VAR_WAREHOUSE_CODE)) {
				warehouseCode = (String) bodyMap.get(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(bodyMap.containsKey(SBConstant.VAR_EDI_ORDER_ID)) {
				try {
					ediOrderId = Long.parseLong(String.valueOf(bodyMap.get(SBConstant.VAR_EDI_ORDER_ID)));
				} catch (NumberFormatException e) {
					logger.error("NumberFormatException while parsing ediOrderId.");
				}
			}
			if(bodyMap.containsKey(SBConstant.VAR_REASON_FOR_SKIP)) {
				reasonForSkip = (String) bodyMap.get(SBConstant.VAR_REASON_FOR_SKIP);
			}
		} catch (IOException e) {
			bodyMap = new HashMap<String, Object>();
			logger.error("IOException :: skipShipment(...)", e);
		}
		if(SBUtils.isNull(etailorId) || SBUtils.isNull(warehouseCode) || SBUtils.isNull(ediOrderId)) {
			StringBuffer args = new StringBuffer();
			if(SBUtils.isNull(warehouseCode)) {
				args.append(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(SBUtils.isNull(etailorId)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_ETAILOR_ID);
			}
			if(SBUtils.isNull(ediOrderId)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_EDI_ORDER_ID);
			}
			logger.error("SellerClientException :: skipShipment(...)", "Following arguments "+args.toString()+" is mandarory to skip shipment process.");
			NoDataFoundException sse = new NoDataFoundException("Following arguments "+args.toString()+" is mandarory to skip shipment process.");
			sse.setErrorType(SBConstant.ErrorType.ARGUMENT_MISSING.name());
			sse.setErrorCode(SBConstant.ERROR_CODE_ARGUMENT_MISSING);
			sse.setRequestId(UUID.randomUUID().toString());
			sse.setServiceName("/order/skipShipment");
			throw sse;//new SellerClientException("Following arguments "+args.toString()+" is mandarory to shipment info.");
		} else {
			String result = null;
			if(!ediOrderId.equals(0L)) {
				result = orderService.skipShipment(ediOrderId, reasonForSkip);
			}
			if(result == null) {
				NoDataFoundException sse = new NoDataFoundException("Unable to skip shipment process for ediOrderId = "+ediOrderId);
				sse.setErrorType(SBConstant.ErrorType.DATA_NOT_FOUND.name());
				sse.setErrorCode(SBConstant.ERROR_CODE_DATA_NOT_FOUND);
				sse.setRequestId(UUID.randomUUID().toString());
				sse.setServiceName("/order/skipShipment");
				logger.error("NoDataFoundException :: skipShipment(...)", sse.toString());
				throw sse;
			} else {
				status = new HashMap<String, String>();
				status.put("status", result);
				logger.info("skipShipment(...)-------END");
				return new ResponseEntity<Map<String, String>>(status, HttpStatus.OK);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Complete shipment process")
	@PostMapping(value = "/completionOfShipment", 
			consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<Map<String, Object>> completionOfShipment(@RequestParam(name="guid") String guid, 
												@RequestParam(name="token") String token, 
												@RequestParam(name = "RequestBody") String body){
		logger.info("completionOfShipment(...)-------START");
		Map<String, Object> bodyMap = null;
		int etailorId		= 0; 
		String warehouseCode= null;
		Long ediOrderId 	= 0L;
		String trackingId	= null;
		String requestId 	= UUID.randomUUID().toString();
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey(SBConstant.VAR_ETAILOR_ID)) {
				try {
					etailorId = Integer.parseInt(String.valueOf(bodyMap.get(SBConstant.VAR_ETAILOR_ID)));
				} catch (NumberFormatException e) {
					logger.error("NumberFormatException while parsing etailorId.");
				}
			}
			if(bodyMap.containsKey(SBConstant.VAR_WAREHOUSE_CODE)) {
				warehouseCode = (String) bodyMap.get(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(bodyMap.containsKey(SBConstant.VAR_EDI_ORDER_ID)) {
				try {
					ediOrderId = Long.parseLong(String.valueOf(bodyMap.get(SBConstant.VAR_EDI_ORDER_ID)));
				} catch (NumberFormatException e) {
					logger.error("NumberFormatException while parsing ediOrderId.");
				}
			}
			if(bodyMap.containsKey(SBConstant.VAR_TRACKING_ID)) {
				trackingId = String.valueOf(bodyMap.get(SBConstant.VAR_TRACKING_ID));
			}
		} catch (IOException e) {
			bodyMap = new HashMap<String, Object>();
			logger.error("IOException :: completionOfShipment(...)", e);
		}
		if(SBUtils.isNull(etailorId) || SBUtils.isNull(warehouseCode) || SBUtils.isNull(ediOrderId) || SBUtils.isNull(trackingId)) {
			StringBuffer args = new StringBuffer();
			if(SBUtils.isNull(warehouseCode)) {
				args.append(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(SBUtils.isNull(etailorId)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_ETAILOR_ID);
			}
			if(SBUtils.isNull(ediOrderId)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_EDI_ORDER_ID);
			}
			if(SBUtils.isNull(trackingId)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_TRACKING_ID);
			}
			logger.error("SellerClientException :: completionOfShipment(...)", "Following arguments "+args.toString()+" is mandarory to complete shipment process.");
			NoDataFoundException sse = new NoDataFoundException("Following arguments "+args.toString()+" is mandarory to complete shipment process.");
			sse.setErrorType(SBConstant.ErrorType.ARGUMENT_MISSING.name());
			sse.setErrorCode(SBConstant.ERROR_CODE_ARGUMENT_MISSING);
			sse.setRequestId(requestId);
			sse.setServiceName("/order/completionOfShipment");
			throw sse;
		} else {
			Map<String, Object> result = new HashMap<String, Object>();
			if(!ediOrderId.equals(0L)) {
				result = orderService.completionOfShipment(requestId, ediOrderId, warehouseCode, etailorId, trackingId, guid);
			}
			if(result == null) {
				NoDataFoundException sse = new NoDataFoundException("Unable to complete shipment process for ediOrderId = "+ediOrderId);
				sse.setErrorType(SBConstant.ErrorType.DATA_NOT_FOUND.name());
				sse.setErrorCode(SBConstant.ERROR_CODE_DATA_NOT_FOUND);
				sse.setRequestId(requestId);
				sse.setServiceName("/order/completionOfShipment");
				logger.error("NoDataFoundException :: skipShipment(...)", sse.toString());
				throw sse;
			} else {
				response.putAll(result);
				logger.info("completionOfShipment(...)-------END");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}
		}
	}
	
	
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiOperation(value = "API For Synchronous Creating Order")
	@PostMapping(value = "/create", consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<ServiceResponse> createOrderSync(@RequestParam(name="guid") String guid, 
												@RequestParam(name="token") String token, 
												@RequestParam(name = "RequestBody") String body){
		logger.info("createOrderSync(...)-------START");
		Map<String, Object> bodyMap = null;
		int etailorId				= 0;
		String requestId			= null;
		String warehouseCode		= null;
		String orderType			= null;
		OF of 						= null;
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
			if(bodyMap.containsKey(SBConstant.VAR_ORDER_TYPE)) {
				orderType = (String) bodyMap.get(SBConstant.VAR_ORDER_TYPE);
			} else {
				orderType = "WMS";
			}
			response.setRequestId(requestId);
			if(bodyMap.containsKey("OF")) {
				Gson g = new Gson();
				of = g.fromJson((String)bodyMap.get("OF"), OF.class);
				/***
				message = g.fromJson((String)bodyMap.get("Message"), Message.class);
				if(message != null) {
					if (message.getMessageType().equalsIgnoreCase(SBConstant.MESSAGE_TYPE_OF)) {
						if (message.getOf() != null) {
							warehouseCode = message.getOf().getWarehouseLocationId();
							of = message.getOf();
						}
					}
				}***/
				if(of != null) {
					warehouseCode = of.getWarehouseLocationId();
				}
			}
			if(SBUtils.isNull(etailorId) || SBUtils.isNull(warehouseCode) || of == null) {
				StringBuffer args = new StringBuffer();
				if(SBUtils.isNull(etailorId)) {
					args.append(SBConstant.VAR_ETAILOR_ID);
				}
				if(SBUtils.isNull(warehouseCode)) {
					args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_WAREHOUSE_CODE);
				}
				if(of == null) {
					args.append(args.length() > 0 ? ", " : "").append("OF body");
				}
				response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_FAILURE);
				response.setStatus(SBConstant.TXN_STATUS_FAILURE);
				response.setResponseMessage("Following arguments "+args.toString()+" is mandarory to create order.");
				response.setUniqueKey(null);
				response.setErrorDesc("Following arguments "+args.toString()+" is mandarory to create order.");
			} else {
				if( of != null) {
					Long uniqueId = orderService.isOrderExist(requestId, etailorId, of.getWarehouseLocationId(), of.getPurchaseOrder());
					if(uniqueId > 0L) {
						String responseMsg = "System is unable to create order.";
						if(of != null) {
							if(of.getPurchaseOrder() != null) {
								responseMsg = "Shipment Id '" + of.getPurchaseOrder() +"' is unable to process.";
							} else if(of.getCustomerOrder() != null) {
								responseMsg = "Order Id '" + of.getCustomerOrder() +"' is unable to process.";
							}
						}
						response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_DUPLICATE);
						response.setStatus(SBConstant.TXN_STATUS_DUPLICATE);
						response.setResponseMessage(responseMsg);
						response.setUniqueKey(uniqueId.toString());
					} else {
						Long ediOrderId = ediConfigDao.getId(SBConstant.MESSAGE_TYPE_OF);
						response = orderService.createOrder(requestId, of, orderType, etailorId, ediOrderId, guid);
					}
				}
			}
		} catch (IOException e) {
			logger.error("IOException :: createOrderSync(...)", e);
			bodyMap = new HashMap<String, Object>();
			String responseMsg = "System is unable to create order.";
			if(of != null) {
				if(of.getPurchaseOrder() != null) {
					responseMsg = "Shipment Id '" + of.getPurchaseOrder() +"' is unable to process.";
				} else if(of.getCustomerOrder() != null) {
					responseMsg = "Order Id '" + of.getCustomerOrder() +"' is unable to process.";
				}
			}
			response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_EXCEPTION);
			response.setStatus(SBConstant.TXN_STATUS_EXCEPTION);
			response.setResponseMessage(responseMsg);
			response.setUniqueKey(null);
			response.setErrorDesc(e.getMessage());
		}
		logger.info("createOrderSync(...) Response = "+response.toString());
		logger.info("createOrderSync(...)-------END");
		return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "null" })
	@ApiOperation(value = "API For Asynchronous Creating Order")//, hidden = true)
	@PostMapping(value = "/createAsync", consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<ServiceResponse> createOrderAsync(@RequestParam(name="guid") String guid, 
								 @RequestParam(name="token") String token, 
								 @RequestParam(name = "RequestBody") String body){
		logger.info("createOrderAsync(...)-------START");
		Map<String, Object> bodyMap = null;
		int etailorId				= 0;
		String requestId			= null;
		String warehouseCode		= null;
		String orderType			= null;
		OF of 						= null;
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
			if(bodyMap.containsKey("OF")) {
				Gson g = new Gson();
				of = g.fromJson((String)bodyMap.get("OF"), OF.class);
				if(of != null) {
					warehouseCode = of.getWarehouseLocationId();
				}
			}
			if(SBUtils.isNull(etailorId) || SBUtils.isNull(warehouseCode) || of == null) {
				StringBuffer args = new StringBuffer();
				if(SBUtils.isNull(etailorId)) {
					args.append(SBConstant.VAR_ETAILOR_ID);
				}
				if(SBUtils.isNull(warehouseCode)) {
					args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_WAREHOUSE_CODE);
				}
				if(of == null) {
					args.append(args.length() > 0 ? ", " : "").append("OF body");
				}
				response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_FAILURE);
				response.setStatus(SBConstant.TXN_STATUS_FAILURE);
				response.setResponseMessage("Following arguments "+args.toString()+" is mandarory to create order.");
				response.setUniqueKey(null);
				response.setErrorDesc("Following arguments "+args.toString()+" is mandarory to create order.");
			} else {
				if( of != null) {
					Long uniqueId = orderService.isOrderExist(requestId, etailorId, of.getWarehouseLocationId(), of.getPurchaseOrder());
					if(uniqueId > 0L) {
						String responseMsg = "System is unable to create order.";
						if(of != null) {
							if(of.getPurchaseOrder() != null) {
								responseMsg = "Shipment Id '" + of.getPurchaseOrder() +"' is unable to process.";
							} else if(of.getCustomerOrder() != null) {
								responseMsg = "Order Id '" + of.getCustomerOrder() +"' is unable to process.";
							}
						}
						response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_DUPLICATE);
						response.setStatus(SBConstant.TXN_STATUS_DUPLICATE);
						response.setResponseMessage(responseMsg);
						response.setUniqueKey(uniqueId.toString());
					} else {
						Long ediOrderId = ediConfigDao.getId(SBConstant.MESSAGE_TYPE_OF);
						orderService.createOrderAsync(requestId, of, orderType, etailorId, ediOrderId, guid);
						response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_SUCCESS);
						response.setStatus(SBConstant.TXN_STATUS_SUCCESS);
						response.setUniqueKey(ediOrderId.toString());
						response.setResponseMessage("Shipment Id " + of.getPurchaseOrder() +" is async submitted to process.");
					}
				}
			}
		} catch (IOException e) {
			logger.error("IOException :: createOrderAsync(...)", e);
			bodyMap = new HashMap<String, Object>();
			response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_EXCEPTION);
			response.setStatus(SBConstant.TXN_STATUS_EXCEPTION);
			response.setResponseMessage("Shipment Id '" + of.getPurchaseOrder() +"' is unable to process.");
			response.setUniqueKey(null);
			response.setErrorDesc(e.getMessage());
		}
		logger.info("createOrderAsync(...) Response = "+response.toString());
		logger.info("createOrderAsync(...)-------END");
		return new ResponseEntity<ServiceResponse>(response, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "API For Asynchronous Creating Order")//, hidden = true)
	@PostMapping(value = "/update/measurement", consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<ServiceResponse> measurmentUpdateAsync(@RequestParam(name="guid") String guid, 
								 @RequestParam(name="token") String token, 
								 @RequestParam(name = "RequestBody") String body){
		logger.info("measurmentUpdateAsync(...)-------START");
		Map<String, Object> bodyMap = null;
		String ediOrderId			= null;
		String requestId			= null;
		ServiceResponse response = new ServiceResponse();
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey(SBConstant.VAR_EDI_ORDER_ID)) {
				ediOrderId = (String) bodyMap.get(SBConstant.VAR_EDI_ORDER_ID);
				try {
					Long.parseLong(ediOrderId);
				} catch (NumberFormatException e) {
					ediOrderId = null;
				}
			} 
			if(bodyMap.containsKey(SBConstant.VAR_REQUEST_ID)) {
				requestId = (String) bodyMap.get(SBConstant.VAR_REQUEST_ID);
			} else {
				requestId = UUID.randomUUID().toString();
			}
			response.setRequestId(requestId);
			if(SBUtils.isNull(ediOrderId)) {
				response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_FAILURE);
				response.setStatus(SBConstant.TXN_STATUS_FAILURE);
				response.setResponseMessage("System ediOrderId is mandarory to update measurment of order. Provided ediOrderId = "+bodyMap.get(SBConstant.VAR_EDI_ORDER_ID)+" not valid.");
				response.setUniqueKey(null);
				response.setErrorDesc("System ediOrderId is mandarory to update measurment of order. Provided ediOrderId = "+bodyMap.get(SBConstant.VAR_EDI_ORDER_ID)+" not valid.");
			} else {
				boolean flag = orderService.isOrderExistByEdiOrderId(requestId, Long.parseLong(ediOrderId));
				if(flag) {
					orderService.measurmentUpdateAsync(requestId, Long.parseLong(ediOrderId));
					response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_SUCCESS);
					response.setStatus(SBConstant.TXN_STATUS_SUCCESS);
					response.setResponseMessage("Update measurment for ediOrderId = "+ediOrderId+" submitted.");
					response.setUniqueKey(ediOrderId.toString());
				} else {
					response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_NO_DATA);
					response.setStatus(SBConstant.TXN_STATUS_NO_DATA);
					response.setUniqueKey(ediOrderId.toString());
					response.setResponseMessage("Unable to find shipment based on provided ediOrderId = " + ediOrderId);
				}
			}
		} catch (IOException e) {
			logger.error("IOException :: measurmentUpdateAsync(...)", e);
			bodyMap = new HashMap<String, Object>();
			response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_EXCEPTION);
			response.setStatus(SBConstant.TXN_STATUS_EXCEPTION);
			response.setResponseMessage("General exception occured, Unable to process measurment update for "+ ediOrderId);
			response.setUniqueKey(null);
			response.setErrorDesc(e.getMessage());
		}
		logger.info("measurmentUpdateAsync(...) Response = "+response.toString());
		logger.info("measurmentUpdateAsync(...)-------END");
		return new ResponseEntity<ServiceResponse>(response, HttpStatus.CREATED);
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Make invoice entry")
	@PostMapping(value = "/create/invoice", 
			consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<Map<String, Object>> makeInvoiceEntry(@RequestParam(name="guid") String guid, 
												@RequestParam(name="token") String token, 
												@RequestParam(name = "RequestBody") String body){
		logger.info("makeInvoiceEntry(...)-------START");
		Map<String, Object> bodyMap = null;
		String shipmentId			= null;
		String filepath				= null;
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey("shipmentId")) {
				shipmentId = (String) bodyMap.get("shipmentId");
			}
			if(bodyMap.containsKey("filepath")) {
				filepath = (String) bodyMap.get("filepath");
			}
		} catch (IOException e) {
			logger.error("IOException :: makeInvoiceEntry(...)", e);
			bodyMap = new HashMap<String, Object>();
		}
		
		if(SBUtils.isNull(shipmentId) || SBUtils.isNull(filepath)) {
			StringBuffer args = new StringBuffer();
			if(SBUtils.isNull(shipmentId)) {
				args.append("shipmentId");
			}
			if(SBUtils.isNull(filepath)) {
				args.append(args.length() > 0 ? ", " : "").append("filepath");
			}
			throw new SellerClientException("Following arguments "+args.toString()+" is mandarory to makeInvoiceEntry(...)");
		} else {
			bodyMap = new HashMap<String, Object>();
			try {
				EdiShipmentInvoice invoice = invoiceDao.findByShipmentId(shipmentId);
				if(invoice == null) {
					invoice = new EdiShipmentInvoice();
					invoice.setShipmentId(shipmentId);
					invoice.setInvoiceFilepath(filepath);
					invoiceDao.save(invoice);
					bodyMap.put(SBConstant.TXN_PARAM_STATUS, SBConstant.TXN_STATUS_SUCCESS);
				} else {
					bodyMap.put(SBConstant.TXN_PARAM_STATUS, SBConstant.TXN_STATUS_DUPLICATE);
				}
			} catch (Exception e) {
				logger.error("Exception :: makeInvoiceEntry(...)", e);
				bodyMap.put(SBConstant.TXN_PARAM_STATUS, SBConstant.TXN_STATUS_FAILURE);
			}
			logger.info("makeInvoiceEntry(...) Response = "+bodyMap.toString());
			logger.info("makeInvoiceEntry(...)-------END");
			return new ResponseEntity<Map<String, Object>>(bodyMap, HttpStatus.OK);
		}
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Make OFR {29, 8, 13} entry")
	@PostMapping(value = "/create/ofr", 
			consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<ServiceResponse> createOFR(@RequestParam(name="guid") String guid, 
												@RequestParam(name="token") String token, 
												@RequestParam(name = "RequestBody") String body){
		logger.info("createOFR(...)-------START");
		ServiceResponse response = new ServiceResponse();
		Map<String, Object> bodyMap = null;
		String ediOrderId			= null;
		String requestId			= null;
		int orderStatus				= 0;
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey(SBConstant.VAR_EDI_ORDER_ID)) {
				ediOrderId = (String) bodyMap.get(SBConstant.VAR_EDI_ORDER_ID);
				try {
					Long.parseLong(ediOrderId);
				} catch (NumberFormatException e) {
					ediOrderId = null;
				}
			}
			if(bodyMap.containsKey(SBConstant.VAR_REQUEST_ID)) {
				requestId = (String) bodyMap.get(SBConstant.VAR_REQUEST_ID);
			} else {
				requestId = UUID.randomUUID().toString();
			}
			if(bodyMap.containsKey(SBConstant.VAR_OFR_ORDER_STATUS)) {
				orderStatus = (int) bodyMap.get(SBConstant.VAR_OFR_ORDER_STATUS);
			} 
			response.setRequestId(requestId);
			
			if(SBUtils.isNull(ediOrderId) || SBUtils.isNull(orderStatus)) {
				response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_FAILURE);
				response.setStatus(SBConstant.TXN_STATUS_FAILURE);
				response.setResponseMessage("System ediOrderId & orderStatus both is mandarory to create ofr against order.");
				response.setUniqueKey(null);
				response.setErrorDesc("System ediOrderId & orderStatus both is mandarory to create ofr against order.");
			} else {
				boolean flag = orderService.isOrderExistByEdiOrderId(requestId, Long.parseLong(ediOrderId));
				if(flag) {
					ServiceResponse sr = orderService.createOfr(requestId, Long.parseLong(ediOrderId), orderStatus);
					response.setResponseCode(sr.getResponseCode());
					response.setStatus(sr.getStatus());
					response.setResponseMessage(sr.getResponseMessage());
					response.setUniqueKey(sr.getUniqueKey());
					response.setErrorDesc(sr.getErrorDesc());
				} else {
					response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_NO_DATA);
					response.setStatus(SBConstant.TXN_STATUS_NO_DATA);
					response.setUniqueKey(ediOrderId.toString());
					response.setResponseMessage("Unable to find shipment based on provided ediOrderId = " + ediOrderId);
				}
			}
		} catch (IOException e) {
			logger.error("IOException :: createOFR(...)", e);
			response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_EXCEPTION);
			response.setStatus(SBConstant.TXN_STATUS_EXCEPTION);
			response.setResponseMessage("General exception occured, Unable to create ofr for ediOrderId = "+ ediOrderId);
			response.setUniqueKey(null);
			response.setErrorDesc(e.getMessage());
		}
		return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
	}
	
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Get pending items against picklist number")
	@PostMapping(value = "/pendingItemList", 
			consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<List<PendingItems>> pendingItemListAgainstPicklist(@RequestParam(name="guid") String guid, 
												@RequestParam(name="token") String token, 
												@RequestParam(name = "RequestBody") String body){
		logger.info("pendingItemListAgainstPicklist(...)"+SBConstant.LOG_SEPRATOR_WITH_START);
		Map<String, Object> bodyMap = null;
		String picklistNumber		= null;
		String warehouseCode		= null;
		List<PendingItems> pendingItems = new ArrayList<PendingItems>();
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey(SBConstant.VAR_PICKLIST_NUMBER)) {
				picklistNumber = (String) bodyMap.get(SBConstant.VAR_PICKLIST_NUMBER);
			}
			if(bodyMap.containsKey(SBConstant.VAR_WAREHOUSE_CODE)) {
				warehouseCode = (String) bodyMap.get(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(SBUtils.isNull(warehouseCode) || SBUtils.isNull(picklistNumber)) {
				StringBuffer args = new StringBuffer();
				if(SBUtils.isNull(warehouseCode)) {
					args.append(SBConstant.VAR_WAREHOUSE_CODE);
				}
				if(SBUtils.isNull(picklistNumber)) {
					args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_PICKLIST_NUMBER);
				}
				SellerServiceException sse = new SellerServiceException("Following arguments "+args.toString()+" is mandarory to to get list of pending items.");
				sse.setErrorType(ErrorType.CLIENT_ERROR);
				sse.setErrorCode(SBConstant.ERROR_CODE_ARGUMENT_MISSING);
				sse.setRequestId(UUID.randomUUID().toString());
				sse.setServiceName("/order/pendingItemList");
				throw sse;
			} else {
				pendingItems = orderDao.getPendingItemAgainstPicklist(picklistNumber);
				logger.info("pendingItemListAgainstPicklist(...)"+SBConstant.LOG_SEPRATOR_WITH_END);
			}
		} catch (IOException e) {
			logger.error("IOException :: pendingItemListAgainstPicklist(...)", e);
			SellerServiceException sse = new SellerServiceException(e.getLocalizedMessage());
			sse.setErrorType(ErrorType.CLIENT_ERROR);
			sse.setErrorCode(SBConstant.ERROR_CODE_CLIENT_ERROR);
			sse.setRequestId(UUID.randomUUID().toString());
			sse.setServiceName("/order/pendingItemList");
			throw sse;
		}
		return new ResponseEntity<List<PendingItems>>(pendingItems, HttpStatus.OK);
	}
	
}
