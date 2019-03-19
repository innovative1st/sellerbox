package com.seller.box.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.seller.box.utils.SBConstant;
import com.seller.box.utils.SBUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/order")
@Api(tags = "Order", position=2, value="Order", description="API to retrieve or manipulate Order related information.")
public class OrderController {
	@Autowired
	OrderDao orderDao;
	@Autowired
	EdiShipmentDao shipmentDao;
	@Autowired
	ShipmentInvoiceDao invoiceDao;
	
	@ApiOperation(value = "View new shipments")
	@RequestMapping(value = "/view/new", 
					method = RequestMethod.POST,
					consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
					)
	public ResponseEntity<List<ShipmentHdrWithItem>> viewNewShipments(@RequestParam(name="guid") String guid, 
																	  @RequestParam(name="token") String token, 
																	  @RequestParam(name = "RequestBody") String body){
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
			
			throw new SellerClientException("Following arguments "+args+" is mandatory to viewNewShipments(...)");
		} else {
			newShipments = (List<ShipmentHdrWithItem>) orderDao.findNewShipments(criteria);
			if(newShipments.isEmpty() || newShipments== null) {
				NoDataFoundException sse = new NoDataFoundException("There are no shipment in new status based on provided criteria.");
				sse.setErrorType(SBConstant.ErrorType.DATA_NOT_FOUND.name());
				sse.setErrorCode(SBConstant.ERROR_CODE_DATA_NOT_FOUND);
				sse.setRequestId(UUID.randomUUID().toString());
				sse.setServiceName("/order/view/new");
				throw sse;
			} else {
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
		PicklistSearchForm criteria = new PicklistSearchForm();
		try {
			criteria = new ObjectMapper().readValue(body, PicklistSearchForm.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
						throw sse;
					} else {
						String resp = "service: createPicklist()" + ", " + "requestStatus: " + response.get(SBConstant.TXN_RESPONSE_STATUS)+ "requestId: " + response.get(SBConstant.TXN_RESPONSE_REQUEST_ID) + ", " + "picklistId: " + response.get(SBConstant.TXN_RESPONSE_PICKLIST_ID) + ", " + "picklistNumber: " + response.get(SBConstant.TXN_RESPONSE_PICKLIST_NUMBER);
						return new ResponseEntity<>(resp, HttpStatus.OK);
					}
				}
			} else {
				throw new SellerClientException("Batch size is mandatory and it should be more then 0 to create picklist.");
			}
		}
		return null;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "View picklist")
	@PostMapping(value = "/readyToPick", 
			 consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<List<EdiPicklist>> getReadyToPickList(@RequestParam(name="guid") String guid, 
																@RequestParam(name="token") String token, 
																@RequestParam(name = "RequestBody") String body){
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
			
			throw new SellerClientException("Following arguments "+args.toString()+" is mandarory to getReadyToPickList(...)");
		} else {
			picklist = (List<EdiPicklist>) orderDao.findReadyToPick(etailorId, locationCode, picklistFor, picklistStatus);
			if(picklist == null) {
				NoDataFoundException sse = new NoDataFoundException("There are no picklist based on provided criteria.");
				sse.setErrorType(SBConstant.ErrorType.DATA_NOT_FOUND.name());
				sse.setErrorCode(SBConstant.ERROR_CODE_DATA_NOT_FOUND);
				sse.setRequestId(UUID.randomUUID().toString());
				sse.setServiceName("/order/readyToPick");
				throw sse;
			} else {
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
			
			throw new SellerClientException("Following arguments "+args.toString()+" is mandarory to getReadyToShipList(...)");
		} else {
			readyShipment = (List<ReadyToShip>) orderDao.findReadyToShip(etailorId, locationCode, pickupDate, pickupOtherDate, carrierName);
			if(readyShipment == null) {
				NoDataFoundException sse = new NoDataFoundException("There are no picklist based on provided criteria.");
				sse.setErrorType(SBConstant.ErrorType.DATA_NOT_FOUND.name());
				sse.setErrorCode(SBConstant.ERROR_CODE_DATA_NOT_FOUND);
				sse.setRequestId(UUID.randomUUID().toString());
				sse.setServiceName("/order/readyToShip");
				throw sse;
			} else {
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
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Get shipment details")
	@PostMapping(value = "/getShipment", 
			consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<Shipment> getShipment(@RequestParam(name="guid") String guid, 
												@RequestParam(name="token") String token, 
												@RequestParam(name = "RequestBody") String body){
		
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
			
			throw new SellerClientException("Following arguments "+args.toString()+" is mandarory to getShipment(...)");
		} else {
			if(ediOrderId.equals(0L)) {
				ediOrderId = orderDao.getEdiOrderIdForPacking(etailorId, locationCode, picklistNumber, ean);
			}
			if(!ediOrderId.equals(0)) {
				shipment = this.getSHipmentForPacking(ediOrderId);
			}
			if(shipment == null) {
				NoDataFoundException sse = new NoDataFoundException("There are no more order for scaned ISBN/EAN/UPC.");
				sse.setErrorType(SBConstant.ErrorType.DATA_NOT_FOUND.name());
				sse.setErrorCode(SBConstant.ERROR_CODE_DATA_NOT_FOUND);
				sse.setRequestId(UUID.randomUUID().toString());
				sse.setServiceName("/order/getShipment");
				throw sse;
			} else {
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
					si.setCustomerOrderItemDetailID(Integer.toString(item.getCustomerOrderItemDetailId()));
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
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Make invoice entry")
	@PostMapping(value = "/create/invoice", 
			consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<Map<String, Object>> makeInvoiceEntry(@RequestParam(name="guid") String guid, 
												@RequestParam(name="token") String token, 
												@RequestParam(name = "RequestBody") String body){
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
				bodyMap.put(SBConstant.TXN_PARAM_STATUS, SBConstant.TXN_STATUS_FAILURE);
			}
			return new ResponseEntity<Map<String, Object>>(bodyMap, HttpStatus.OK);
		}
		
	}
}
