package com.seller.box.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seller.box.dao.EdiConfigDao;

import io.swagger.annotations.Api;

@RestController
@Api(tags= {"Configuration"}, description="API to retrieve or manipulate edi-config related information.", hidden = true)
@RequestMapping("/config")
public class EdiConfigController {
	@Autowired
	EdiConfigDao configDao;
//	@GetMapping(value = "/getEdiConfig", 
//			 produces = {MediaType.APPLICATION_JSON_VALUE})
//	public EdiConfig getEdiConfig(@RequestParam(name="locationCode") String locationCode, @RequestParam(name="etailorId") int etailorId, @RequestParam(name="messageType") String messageType){
//		EdiConfig config = new EdiConfig(configDao, locationCode, etailorId, messageType);
//		if(messageType.equalsIgnoreCase(SBConstant.MESSAGE_TYPE_IAN)) {
//			config.setArchiveFilepath(SBUtils.getPropertyValue("IAN_FILE_PATH"));
//		}
//		return null;
//	}
	
	@GetMapping(value = "/getEdiId", produces = {MediaType.APPLICATION_JSON_VALUE})
	public Long getEdiIdBySequenceName(String seq_name) {
		Long id = configDao.getId(seq_name);
        return id;
	}
	
	@GetMapping(value = "/getTransControlNumber", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String getTransControlNumber() {
		return configDao.getTransControlNumber();
	}
	
	@GetMapping(value = "/getMessageControlNumber", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String getMessageControlNumber() {
		return configDao.getMessageControlNumber();
	}
	
	@GetMapping(value = "/getInventoryControlNumber", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String getInventoryControlNumber() {
		return configDao.getInventoryControlNumber();
	}
	
	@GetMapping(value = "/getAdjustmentControlId", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String getAdjustmentControlId() {
		return configDao.getAdjustmentControlId();
	}
	
	@GetMapping(value = "/getMarketplaceId", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String getMarketplaceId(@RequestParam(name="locationCode") String locationCode) {
		return configDao.getMarketplaceId(locationCode);
	}
	
	@GetMapping(value = "/getReceivingPartyId", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String getReceivingPartyId(@RequestParam(name="locationCode") String locationCode) {
		return configDao.getReceivingPartyId(locationCode);
	}
	
	@GetMapping(value = "/getSendingPartyId", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String getSendingPartyId(@RequestParam(name="locationCode") String locationCode) {
		return configDao.getSendingPartyId(locationCode);
	}
}
