package com.seller.box.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seller.box.dao.MasterDao;
import com.seller.box.utils.SBConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags= {"Master"}, position = 7, value="Master", consumes= "application/x-www-form-urlencoded", description="API to retrieve or manipulate master related information.")
@RequestMapping("/master")
public class MasterController {
	private static final Logger logger = LogManager.getLogger(MasterController.class);
	@Autowired
	MasterDao masterDao;
	
	@ApiOperation(value = "API to get list of order status")
	@PostMapping(value = "/orderStatusList", consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<Map<Integer, String>> getOrderStatusList(){
		logger.info("getOrderStatusList()"+SBConstant.LOG_SEPRATOR_WITH_START);
		Map<Integer, String> orderStatusMap = new HashMap<Integer, String>();
		orderStatusMap = masterDao.getOrderStatus();
		logger.info("getOrderStatusList()"+SBConstant.LOG_SEPRATOR_WITH_END);
		return new ResponseEntity<Map<Integer, String>>(orderStatusMap, HttpStatus.OK);
	}
	
	@ApiOperation(value = "API to get list of etailors")
	@PostMapping(value = "/etailorList", consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<Map<Integer, String>> getEtailorList(){
		logger.info("getEtailorList()"+SBConstant.LOG_SEPRATOR_WITH_START);
		Map<Integer, String> etailorList = new HashMap<Integer, String>();
		etailorList = masterDao.getEtailorList();
		logger.info("getEtailorList()"+SBConstant.LOG_SEPRATOR_WITH_END);
		return new ResponseEntity<Map<Integer, String>>(etailorList, HttpStatus.OK);
	}
	
	@ApiOperation(value = "API to get list of warehouse")
	@PostMapping(value = "/warehouseCodeList", consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<Map<String, String>> getWarehouseCodeList(){
		logger.info("getWarehouseCodeList()"+SBConstant.LOG_SEPRATOR_WITH_START);
		Map<String, String> warehouseCodeMap = new HashMap<String, String>();
		warehouseCodeMap = masterDao.getWarehouseCodeList();
		logger.info("getWarehouseCodeList()"+SBConstant.LOG_SEPRATOR_WITH_END);
		return new ResponseEntity<Map<String, String>>(warehouseCodeMap, HttpStatus.OK);
	}
}
