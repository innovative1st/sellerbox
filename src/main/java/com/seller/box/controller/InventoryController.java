package com.seller.box.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seller.box.config.EdiConfig;
import com.seller.box.dao.EdiConfigDao;
import com.seller.box.dao.EdiInventoryIanDao;
import com.seller.box.dao.VirtualInventorySearchDao;
import com.seller.box.entities.EdiInventoryIan;
import com.seller.box.entities.ShipmentHdrWithItem;
import com.seller.box.entities.VirtualInventorySearch;
import com.seller.box.exception.NoDataFoundException;
import com.seller.box.exception.SellerClientException;
import com.seller.box.form.EdiInventoryIanForm;
import com.seller.box.form.PicklistSearchForm;
import com.seller.box.form.VirtualInventoryForm;
import com.seller.box.utils.SBConstant;
import com.seller.box.utils.SBUtils;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
	@Autowired
	VirtualInventorySearchDao vInventoryDao;
	
	@Autowired
	EdiInventoryIanDao inventoryIanDao;
	
	@Autowired
	EdiConfigDao configDao;
	
	@PostMapping(value = "/virtualInventoryStatus", 
				 produces = {MediaType.APPLICATION_JSON_VALUE},
				 consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<Page<VirtualInventorySearch>> getAllInventoryPagable(@RequestParam(name="guid") String guid, 
			  										  						   @RequestParam(name="token") String token, 
			  										  						   @RequestParam(name = "RequestBody") String body){
		VirtualInventoryForm criteria = new VirtualInventoryForm();
		try {
			criteria = new ObjectMapper().readValue(body, VirtualInventoryForm.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int page = criteria.getPage();
		int size = criteria.getSize();
		if(size == 0) {
			size = SBConstant.DEFAULT_PAGE_SIZE;
		}
		if(criteria.getSkuCode() != null || criteria.getSkuCode() != "") {
			criteria.setSkuCode(null);
		}
		if(SBUtils.isNull(criteria.getLocationCode()) || criteria.getEtailorId() == 0) {
			StringBuffer args = new StringBuffer();
			if(SBUtils.isNull(criteria.getLocationCode())) {
				args.append(SBConstant.VAR_LOCATION_CODE);
			}
			if(criteria.getEtailorId() == 0) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_ETAILOR_ID);
			}
			
			throw new SellerClientException("Following arguments "+args+" is mandatory to getAllInventoryPagable(...)");
		} 
		Pageable pagable = PageRequest.of(page, size);
		Page<VirtualInventorySearch> result = null;
		if(criteria.getLocationCode() != null && criteria.getEtailorId() != 0 && criteria.getSkuCode() == null) {
			result = (Page<VirtualInventorySearch>) vInventoryDao.findAllPagable(criteria.getEtailorId(), criteria.getLocationCode(), pagable);
		} else if(criteria.getLocationCode() != null && criteria.getEtailorId() != 0 && criteria.getSkuCode() != null) {
			result = (Page<VirtualInventorySearch>) vInventoryDao.findAllWithSkuPagable(criteria.getEtailorId(), criteria.getLocationCode(), criteria.getSkuCode(), pagable);
		} 
		if(result == null) {
			NoDataFoundException sse = new NoDataFoundException("There are no product/inventory status based on provided criteria.");
			sse.setErrorType(SBConstant.ErrorType.DATA_NOT_FOUND.name());
			sse.setErrorCode(SBConstant.ERROR_CODE_DATA_NOT_FOUND);
			sse.setRequestId(UUID.randomUUID().toString());
			sse.setServiceName("/inventory/viAdvanceSearch");
			throw sse;
		} else {
			return new ResponseEntity<Page<VirtualInventorySearch>>(result, HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/viBasicSearch", 
			 produces = {MediaType.APPLICATION_JSON_VALUE})
	public Page<VirtualInventorySearch> virtuenInventoryBasicSearch(@RequestParam(name = "etailorId") Optional<Integer> etailorId, @RequestParam(name = "value") String value){
		Page<VirtualInventorySearch> result = null;
		int page = 0;
		int size = SBConstant.DEFAULT_PAGE_SIZE;
		if((value != null && !value.isEmpty() && value != "") && (etailorId.isPresent())) {
			Pageable pagable = PageRequest.of(page, size);
			result = (Page<VirtualInventorySearch>) vInventoryDao.findAllInventoryOnBasicSearchWithEtailor(etailorId.get(), value, pagable);
		} else if((value != null && !value.isEmpty() && value != "") && (!etailorId.isPresent())) {
			Pageable pagable = PageRequest.of(page, size);
			result = (Page<VirtualInventorySearch>) vInventoryDao.findAllInventoryOnBasicSearch(value, pagable);
		} else {
			throw new SellerClientException("Invalid argument passed to viBasicSearch(...), value is null");
		}
		return result;
	}
	
	@PostMapping(value = "/inventoryStatusLost",
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public int getQueueQuantityForLost(@RequestParam(name = "etailorId") int etailorId, 
											  @RequestParam(name = "locationCode") String locationCode, 
											  @RequestParam(name = "skuCode") String skuCode) {
		return vInventoryDao.getQueueQuantityForLost(etailorId, locationCode, skuCode);
	}
	
	@PostMapping(value = "/inventoryStatus",
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public int getAvailableStock(@RequestParam(name = "etailorId") int etailorId, 
											  @RequestParam(name = "locationCode") String locationCode, 
											  @RequestParam(name = "skuCode") String skuCode) {
		return vInventoryDao.getAvailableStock(etailorId, locationCode, skuCode);
	}
	
	@PostMapping(value = "/makeIAN",
			produces = {MediaType.APPLICATION_JSON_VALUE},
			consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public String makeIAN(@RequestBody EdiInventoryIanForm input) {
		EdiConfig config = new EdiConfig(configDao, input.getLocationCode(), input.getEtailorId(), SBConstant.MESSAGE_TYPE_IAN);
		EdiInventoryIan ian = new EdiInventoryIan();
		ian.setIanId(config.getTxnId());
		ian.setLocationCode(config.getLocationCode());
		ian.setEtailorId(config.getEtailorId());
		ian.setMessageCount(config.getMessageCount());
		ian.setTransmissionDate(new Date());
		ian.setTransCreationDate(config.getTransCreationDate());
		ian.setTransIsTest(config.getTransIsTest());
		ian.setTransControlNumber(config.getTransControlNumber());
		ian.setMessageCount(config.getMessageCount());
		ian.setTransStructureVersion(config.getTransStructureVersion());
		ian.setTransReceivingPartyId(config.getTransReceivingPartyId());
		ian.setTransSendingPartyId(config.getTransSendingPartyId());
		ian.setMessageType(config.getMessageType());
		ian.setMessageIsTest(config.getMessageIsTest());
		ian.setMessageReceivingPartyId(config.getMessageReceivingPartyId());
		ian.setMessageSendingPartyId(config.getMessageSendingPartyId());
		ian.setMessageStructureVersion(config.getMessageStructureVersion());
		ian.setMessageCreationDate(config.getMessageCreationDate());
		ian.setMessageControlNumber(config.getMessageControlNumber());
		ian.setAdjustmentType(input.getAdjustmentType());
		ian.setItemType(SBUtils.getPropertyValue("ITEM_TYPE_IAN"));
		ian.setItemId(input.getFnsku());
		ian.setSkuCode(input.getSkuCode());
		ian.setQuantity(input.getQuantity());
		ian.setUnitOfMeasure(SBUtils.getPropertyValue("UNIT_OF_MEASURE_IAN"));
		ian.setVendorPartyType(config.getVendorPartyType());
		ian.setVendorPartyId(config.getVendorPartyId());
		ian.setAdjustmentControlId(config.getAdjustmentControlId());
		ian.setInventoryEffectiveDateTime(config.getMessageCreationDate());
		ian.setPurchaseOrderNumber(input.getPurchaseOrderNumber());
		ian.setInventorySourceLocation(input.getInventorySourceLocation());
		ian.setInventoryDestinationLocation(input.getInventoryDestinationLocation());
		ian.setIanFilepath(config.getArchiveFilepath());
		ian.setMessageError(null);
		ian.setRunStatus(0);
		ian.setTxnStatus(SBConstant.TXN_STATUS_INIT);
		ian.setIsIanUpdated(0);
		ian.setInvUpdateErrorMsg(null);
		ian.setCreatedDate(new Date());
		ian.setCreatedBy(input.getCreatedBy());
		ian.setRequestId(config.getRequestId());
		
		inventoryIanDao.save(ian);
		return ian.getRequestId();
	}
}
