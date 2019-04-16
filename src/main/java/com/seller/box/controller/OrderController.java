package com.seller.box.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.seller.box.core.OF;
import com.seller.box.core.ServiceResponse;
import com.seller.box.dao.EdiConfigDao;
import com.seller.box.dao.EdiShipmentDao;
import com.seller.box.dao.OrderDao;
import com.seller.box.dao.ShipmentInvoiceDao;
import com.seller.box.entities.EdiPicklist;
import com.seller.box.entities.EdiShipmentHdr;
import com.seller.box.entities.EdiShipmentInvoice;
import com.seller.box.entities.EdiShipmentItems;
import com.seller.box.entities.ReadyToShip;
import com.seller.box.entities.ShipmentHdrWithItem;
import com.seller.box.exception.NoDataFoundException;
import com.seller.box.exception.SellerClientException;
import com.seller.box.exception.SellerServiceException;
import com.seller.box.form.PicklistSearchForm;
import com.seller.box.form.Shipment;
import com.seller.box.form.ShipmentItem;
import com.seller.box.service.OrderService;
import com.seller.box.utils.SBConstant;
import com.seller.box.utils.SBUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/order")
@Api(tags = "Order", position=2, value="Order", description="API to retrieve or manipulate Order related information.")
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
					consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
					)
	public ResponseEntity<List<ShipmentHdrWithItem>> viewNewShipments(@RequestParam(name="guid") String guid, 
																	  @RequestParam(name="token") String token, 
																	  @RequestParam(name = "RequestBody") String body){
		logger.info("viewNewShipments(...)-------START");
		PicklistSearchForm criteria = new PicklistSearchForm();
		try {
			criteria = new ObjectMapper().readValue(body, PicklistSearchForm.class);
			System.out.println(criteria.getGiftWrapType().isEmpty());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<ShipmentHdrWithItem> newShipments = null;
		if(SBUtils.isNull(criteria.getLocationCode()) || criteria.getEtailorId() == 0 || SBUtils.isNull(criteria.getExSDAfter()) || SBUtils.isNull(criteria.getExSDBefore())) {
			StringBuffer args = new StringBuffer();
			if(SBUtils.isNull(criteria.getLocationCode())) {
				args.append(SBConstant.VAR_LOCATION_CODE);
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
		 	 consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
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
		if(SBUtils.isNull(criteria.getLocationCode()) || criteria.getEtailorId() == 0 || criteria.getBatchSize() == 0 || SBUtils.isNull(criteria.getOrderIds())) {
			StringBuffer args = new StringBuffer();
			if(SBUtils.isNull(criteria.getLocationCode())) {
				args.append(SBConstant.VAR_LOCATION_CODE);
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
			 consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<List<EdiPicklist>> getReadyToPickList(@RequestParam(name="guid") String guid, 
																@RequestParam(name="token") String token, 
																@RequestParam(name = "RequestBody") String body){
		logger.info("getReadyToPickList(...)-------START");
		Map<String, Object> bodyMap = null;
		int etailorId			= 0; 
		String locationCode		= null;
		String picklistFor		= null;
		String picklistStatus	= null;
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey(SBConstant.VAR_ETAILOR_ID)) {
				etailorId = (int) bodyMap.get(SBConstant.VAR_ETAILOR_ID);
			}
			if(bodyMap.containsKey(SBConstant.VAR_LOCATION_CODE)) {
				locationCode = (String) bodyMap.get(SBConstant.VAR_LOCATION_CODE);
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
		if(etailorId == 0 || SBUtils.isNull(locationCode) || SBUtils.isNull(picklistFor) || SBUtils.isNull(picklistStatus)) {
			StringBuffer args = new StringBuffer();
			if(SBUtils.isNull(locationCode)) {
				args.append(SBConstant.VAR_LOCATION_CODE);
			}
			if(etailorId == 0) {
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
			picklist = (List<EdiPicklist>) orderDao.findReadyToPick(etailorId, locationCode, picklistFor, picklistStatus);
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
	@PostMapping(value = "/readyToShip", 
			consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<List<ReadyToShip>> getReadyToShipList(@RequestParam(name="guid") String guid, 
																@RequestParam(name="token") String token, 
																@RequestParam(name = "RequestBody") String body){
		logger.info("getReadyToShipList(...)-------START");
		Map<String, Object> bodyMap = null;
		int etailorId			= 0; 
		String locationCode		= null;
		String pickupDate		= null;
		String pickupOtherDate	= null;
		String carrierName	= null;
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey(SBConstant.VAR_ETAILOR_ID)) {
				etailorId = (int) bodyMap.get(SBConstant.VAR_ETAILOR_ID);
			}
			if(bodyMap.containsKey(SBConstant.VAR_LOCATION_CODE)) {
				locationCode = (String) bodyMap.get(SBConstant.VAR_LOCATION_CODE);
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
		if(etailorId == 0 || SBUtils.isNull(locationCode) || SBUtils.isNull(pickupDate) || SBUtils.isNull(carrierName)) {
			StringBuffer args = new StringBuffer();
			if(SBUtils.isNull(locationCode)) {
				args.append(SBConstant.VAR_LOCATION_CODE);
			}
			if(etailorId == 0) {
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
			readyShipment = (List<ReadyToShip>) orderDao.findReadyToShip(etailorId, locationCode, pickupDate, pickupOtherDate, carrierName);
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
			consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<Map<String, Object>> getPicklistStatus(@RequestParam(name="guid") String guid, 
																@RequestParam(name="token") String token, 
																@RequestParam(name = "RequestBody") String body){
		
		Map<String, Object> bodyMap = null;
		int etailorId		= 0; 
		String locationCode	= null;
		String scanedValue	= null;
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey(SBConstant.VAR_ETAILOR_ID)) {
				etailorId = (int) bodyMap.get(SBConstant.VAR_ETAILOR_ID);
			}
			if(bodyMap.containsKey(SBConstant.VAR_LOCATION_CODE)) {
				locationCode = (String) bodyMap.get(SBConstant.VAR_LOCATION_CODE);
			}
			if(bodyMap.containsKey(SBConstant.VAR_SCANED_VALUE)) {
				scanedValue = (String) bodyMap.get(SBConstant.VAR_SCANED_VALUE);
			}
		} catch (IOException e) {
			bodyMap = new HashMap<String, Object>();
		}
		Map<String, Object> response = null;
		if(etailorId == 0 || SBUtils.isNull(locationCode) || SBUtils.isNull(scanedValue)) {
			StringBuffer args = new StringBuffer();
			if(SBUtils.isNull(locationCode)) {
				args.append(SBConstant.VAR_LOCATION_CODE);
			}
			if(etailorId == 0) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_ETAILOR_ID);
			}
			if(SBUtils.isNull(scanedValue)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_SCANED_VALUE);
			}
			
			throw new SellerClientException("Following arguments "+args.toString()+" is mandarory to getPicklistStatus(...)");
		} else {
			response = orderDao.findPicklistStatus(etailorId, locationCode, scanedValue);
			if(response == null) {
				NoDataFoundException sse = new NoDataFoundException("There are no picklist based on provided criteria.");
				sse.setErrorType(SBConstant.ErrorType.DATA_NOT_FOUND.name());
				sse.setErrorCode(SBConstant.ERROR_CODE_DATA_NOT_FOUND);
				sse.setRequestId(UUID.randomUUID().toString());
				sse.setServiceName("/order/readyToShip");
				throw sse;
			} else {
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}
		}
	}
	
	//@SuppressWarnings({ "unchecked", "unlikely-arg-type" })
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Get shipment details")
	@PostMapping(value = "/getShipment", 
			consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<Shipment> getShipment(@RequestParam(name="guid") String guid, 
												@RequestParam(name="token") String token, 
												@RequestParam(name = "RequestBody") String body){
		logger.info("getShipment(...)-------START");
		Map<String, Object> bodyMap = null;
		int etailorId		= 0; 
		String locationCode	= null;
		String picklistNumber= null;
		String ean			= null;
		Long ediOrderId 	= 0L;
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey(SBConstant.VAR_ETAILOR_ID)) {
				etailorId = (int) bodyMap.get(SBConstant.VAR_ETAILOR_ID);
			}
			if(bodyMap.containsKey(SBConstant.VAR_LOCATION_CODE)) {
				locationCode = (String) bodyMap.get(SBConstant.VAR_LOCATION_CODE);
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
		if(SBUtils.isNull(etailorId) || SBUtils.isNull(locationCode) || SBUtils.isNull(picklistNumber) || SBUtils.isNull(ean)) {
			StringBuffer args = new StringBuffer();
			if(SBUtils.isNull(locationCode)) {
				args.append(SBConstant.VAR_LOCATION_CODE);
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
			throw new SellerClientException("Following arguments "+args.toString()+" is mandarory to getShipment(...)");
		} else {
			if(ediOrderId.equals(0L)) {
				ediOrderId = orderDao.getEdiOrderIdForPacking(etailorId, locationCode, picklistNumber, ean);
			}
			if(!ediOrderId.equals(0L)) {
				shipment = this.getSHipmentForPacking(ediOrderId);
			}
			if(shipment == null) {
				NoDataFoundException sse = new NoDataFoundException("There are no more order for scaned ISBN/EAN/UPC.");
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
	
	@ApiOperation(value = "Get shipment detail for packing", hidden = true)
	private Shipment getSHipmentForPacking(Long ediOrderId) {
		Shipment shipment = null;
		try {
			EdiShipmentHdr shipmentHdr = shipmentDao.findByEdiOrderId(ediOrderId);
			if(shipmentHdr != null) {
				shipment = new Shipment();
				shipment.setEdiOrderId(shipmentHdr.getEdiOrderId());
				shipment.setReadyToPickUpTimeUTC(null);
				shipment.setOperatingWarehouseCode(shipmentHdr.getWarehouseCode());
				shipment.setShipmentId(shipmentHdr.getShipmentId());
				shipment.setOrderId(shipmentHdr.getOrderId());
				shipment.setOriginWarehouseCode(shipmentHdr.getWarehouseCode());
				shipment.setPackageId(Integer.toString(shipmentHdr.getPackageId()));
				shipment.setHeightDimensionUnit(shipmentHdr.getPackageHeightUnit());   
				shipment.setHeightDimensionValue(shipmentHdr.getPackageHeightValue());   
				shipment.setLengthDimensionUnit(shipmentHdr.getPackageLengthUnit());    
				shipment.setLengthDimensionValue(shipmentHdr.getPackageLengthValue());   
				shipment.setWidthDimensionUnit(shipmentHdr.getPackageWidthUnit());     
				shipment.setWidthDimensionValue(shipmentHdr.getPackageWidthValue());   
				shipment.setWeightDiamensionUnit(shipmentHdr.getPackageWeightUnit());  
				shipment.setWeightDiamensionValue(shipmentHdr.getPackageWeightValue()); 
				shipment.setLabelLengthDimensionUnit(shipmentHdr.getLabelLengthUnit());
				shipment.setLabelLengthDimensionValue(shipmentHdr.getLabelLengthValue());
				shipment.setLabelWidthDimensionUnit(shipmentHdr.getLabelWidthUnit());
				shipment.setLabelWidthDimensionValue(shipmentHdr.getLabelWidthValue());   
				shipment.setLabelFormatType(shipmentHdr.getLabelFormatType());
				shipment.setBarcode(shipmentHdr.getBarcode());
				shipment.setLoadId(shipmentHdr.getLoadId());
				shipment.setTrailorId(shipmentHdr.getTrailorId());
				shipment.setTrackingId(shipmentHdr.getTrackingId());
				shipment.setCarrierName(shipmentHdr.getCarrierName());
				//shipment.setInvoiceFilepath(orderDao.getInvoiceFilepath(shipmentHdr.getShipmentId()));
				shipment.setShiplabelFilepath(shipmentHdr.getShipLabelFilepath());
				shipment.setManifestId(shipmentHdr.getManifestId());
				shipment.setManifestErrorMessage(null);
				shipment.setPaymentType(shipmentHdr.getPaymentType());
				shipment.setBalanceDue(shipmentHdr.getBalanceDue());
				shipment.setCurrencyCode(shipmentHdr.getCurrencyCode());
				shipment.setMarketplaceId(Integer.toString(shipmentHdr.getOrderSiteId()));
				shipment.setBraaPpType(shipmentHdr.getBraaPpType());
				shipment.setBraaPpTypeIdentifier(shipmentHdr.getBraaPpTypeIdentifier());
				shipment.setBraaPpQuantity(shipmentHdr.getBraaPpQuantity());
				shipment.setOrderCancelled(shipmentHdr.getIsCanceled());
				shipment.setIsGift(shipmentHdr.getIsGift());
				shipment.setIsGiftWrap(shipmentHdr.getIsGiftWrap());
				shipment.setIsFastTrack(shipmentHdr.getIsPriorityShipment());
				shipment.setIsPaperDunnage(SBUtils.isNull(shipmentHdr.getBraaPpTypeIdentifier()) == true ? 0 : 1);
				List<ShipmentItem> shipmentItem = new ArrayList<ShipmentItem>();
				for(EdiShipmentItems item : shipmentHdr.getEdiShipmentItems()) {
					ShipmentItem si = new ShipmentItem();
					si.setItemSeq(item.getLineItemSeq());
					si.setSkuCode(item.getSkuCode());
					si.setEan(item.getEan());
					si.setItemId(item.getItemId());
					si.setFnsku(item.getFnsku());
					si.setProductName(item.getProductName());
					si.setQuantity(item.getQuantity());
					si.setQuantityPacked(0);
					si.setThumbnailUrl(item.getThumbnailUrl());
					si.setCustomerOrderItemID(item.getOrderItemId());
					si.setCustomerOrderItemDetailID(item.getCustomerOrderItemDetailId() != null ? item.getCustomerOrderItemDetailId().toString() : null);
					si.setBayNo(item.getBayNo());
					si.setRackNo(item.getRackNo());
					si.setQntFromInv(item.getQntFromInv());
					si.setQntFromProd(item.getQntFromVir());
					si.setIsGift(SBUtils.isNull(item.getGiftLabelContent()) == true ? 0 : 1);
					si.setIsGiftWrap(SBUtils.isNull(item.getGiftWrapId()) == true ? 0 : 1);
					si.setGiftLabelContent(item.getGiftLabelContent());
					shipmentItem.add(si);
				}
				shipment.setShipmentItem(shipmentItem);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return shipment;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiOperation(value = "API For Synchronous Creating Order")
	@PostMapping(value = "/create", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<ServiceResponse> createOrderSync(@RequestParam(name="guid") String guid, 
												@RequestParam(name="token") String token, 
												@RequestParam(name = "RequestBody") String body){
		logger.info("createOrderSync(...)-------START");
		Map<String, Object> bodyMap = null;
		int etailorId				= 0;
		String requestId			= null;
		String warehouseCode		= null;
		//Message message 			= null;
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
						response = orderService.createOrder(requestId, of, etailorId, ediOrderId, guid);
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
	@PostMapping(value = "/createAsync", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<ServiceResponse> createOrderAsync(@RequestParam(name="guid") String guid, 
								 @RequestParam(name="token") String token, 
								 @RequestParam(name = "RequestBody") String body){
		logger.info("createOrderAsync(...)-------START");
		Map<String, Object> bodyMap = null;
		int etailorId				= 0;
		String requestId			= null;
		String warehouseCode		= null;
		//Message message 			= null;
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
			response.setRequestId(requestId);
			if(bodyMap.containsKey("OF")) {
				Gson g = new Gson();
				of = g.fromJson((String)bodyMap.get("OF"), OF.class);
				/***
				message = g.fromJson((String)bodyMap.get("Message"), Message.class);
				if(message != null) {
					if (message.getMessageType().equalsIgnoreCase(SBConstant.MESSAGE_TYPE_OF)) {
						if (message.getOf() != null) {
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
						orderService.createOrderAsync(requestId, of, etailorId, ediOrderId, guid);
						response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_SUCCESS);
						response.setStatus(SBConstant.TXN_STATUS_SUCCESS);
						response.setUniqueKey(ediOrderId.toString());
						response.setResponseMessage("Shipment Id =" + of.getPurchaseOrder() +" is async submitted to process.");
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
	@PostMapping(value = "/update/measurement", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
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
			consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
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
}
