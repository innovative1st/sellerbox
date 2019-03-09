package com.seller.box.controller;

import java.io.IOException;
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
import com.seller.box.dao.OrderDao;
import com.seller.box.entities.EdiPicklist;
import com.seller.box.entities.ReadyToShip;
import com.seller.box.entities.ShipmentHdrWithItem;
import com.seller.box.exception.NoDataFoundException;
import com.seller.box.exception.SellerClientException;
import com.seller.box.exception.SellerServiceException;
import com.seller.box.form.PicklistSearchForm;
import com.seller.box.utils.SBConstant;
import com.seller.box.utils.SBUtils;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	OrderDao orderDao;
	
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
				sse.setErrorType(HttpStatus.NO_CONTENT.name());
				sse.setErrorCode("0");
				sse.setRequestId(UUID.randomUUID().toString());
				sse.setServiceName("/order/readyToShip");
				throw sse;
			} else {
				return new ResponseEntity<List<ReadyToShip>>(readyShipment, HttpStatus.OK);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
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
				sse.setErrorType(HttpStatus.NO_CONTENT.name());
				sse.setErrorCode("0");
				sse.setRequestId(UUID.randomUUID().toString());
				sse.setServiceName("/order/readyToShip");
				throw sse;
			} else {
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}
		}
		
		
		
		
		
		
	}
	
}
