package com.seller.box.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seller.box.core.ServiceResponse;
import com.seller.box.dao.EdiPackStationInfoDao;
import com.seller.box.dao.EdiPackStationPrinterDao;
import com.seller.box.entities.EdiPackStationInfo;
import com.seller.box.entities.EdiPackStationPrinter;
import com.seller.box.exception.NoDataFoundException;
import com.seller.box.exception.SellerClientException;
import com.seller.box.service.PackStationService;
import com.seller.box.utils.SBConstant;
import com.seller.box.utils.SBUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/ps")
@Api(tags = "Pack Station", position= 6, value="PackStation", consumes= "application/x-www-form-urlencoded", description="API to retrieve or manipulate pack station related information.")
public class PackStationController {
	private static final Logger logger = LogManager.getLogger(PackStationController.class);
	@Autowired
    EdiPackStationInfoDao ediPSInfoDao;
	@Autowired
	EdiPackStationPrinterDao ediPSPrinterDao;
	@Autowired
	PackStationService packStationService;
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "View pack station")
	@ApiImplicitParams(
			{ 
				@ApiImplicitParam(name = "RequestBody", dataType = "String", required = true, paramType = "query", value = "<table border=\"1\"><tr><td width=\"100\"><strong>Field Name </strong></td><td width=\"100\"><strong>Data Type </strong></td><td align=\"center\" width=\"100\"><strong>Mandatory </strong></td><td width=\"300\"><strong>Description </strong></td></tr><tr style=\"background: #ccc !important;\"><td>warehouseCode</td><td>Varchar(10)</td><td align=\"center\">Mandatory</td><td>eRetail Warehouse Code. API will validate warehouse code from WMS API configuration.</td></tr><tr><td colspan=\"4\"><p><strong>Input JSON Format- <br/>{<br/>&nbsp; &nbsp;\"warehouseCode\": \"XXXX\"<br/>}</strong></p></td></tr></table>"),
				@ApiImplicitParam(name = "guid", dataType = "String", required = true, paramType = "query", value = "API Owner"),
				@ApiImplicitParam(name = "token", dataType = "String", required = true, paramType = "query", value = "API Key")
			})
	@RequestMapping(value = "/view", method = RequestMethod.POST, consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<List<EdiPackStationInfo>> viewPackStations(@RequestParam(name="guid") String guid, 
																	  @RequestParam(name="token") String token, 
																	  @RequestParam(name = "RequestBody") String body){
		logger.info("viewPackStations(...)-------START");
		Map<String, Object> bodyMap = null; 
		String warehouseCode		= null;
		String requestId			= null;
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey(SBConstant.VAR_WAREHOUSE_CODE)) {
				warehouseCode = (String) bodyMap.get(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(bodyMap.containsKey(SBConstant.VAR_REQUEST_ID)) {
				requestId = (String) bodyMap.get(SBConstant.VAR_REQUEST_ID);
			} else {
				requestId = UUID.randomUUID().toString();
			}
		} catch (IOException e) {
			logger.error("IOException :: viewPackStations(...)", e);
		}
		if(SBUtils.isNull(warehouseCode)) {
			throw new SellerClientException("WarehouseCode is mandarory to get pack station information.");
		} else {
			List<EdiPackStationInfo> psInfoList = (List<EdiPackStationInfo>) ediPSInfoDao.findAll();
			if(psInfoList == null) {
				NoDataFoundException sse = new NoDataFoundException("There are no picklist based on provided criteria.");
				sse.setErrorType(SBConstant.ErrorType.DATA_NOT_FOUND.name());
				sse.setErrorCode(SBConstant.ERROR_CODE_DATA_NOT_FOUND);
				sse.setRequestId(requestId);
				sse.setServiceName("/ps/view");
				logger.error("NoDataFoundException :: psInfoList result is null for warehouseCode = "+ warehouseCode, sse.toString());
				logger.info("viewPackStations(...)-------END");
				throw sse;
			} else {
				logger.info("psInfoList >>>>> "+ psInfoList.toString());
				logger.info("viewPackStations(...)-------END");
				return new ResponseEntity<List<EdiPackStationInfo>>(psInfoList, HttpStatus.OK);
			}
		}
	}


	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Create new pack station")
	@ApiImplicitParams(
			{ 
				@ApiImplicitParam(name = "RequestBody", dataType = "String", required = true, paramType = "query", value = "<table border=\"1\"><tr style=\"background: green !important;\"><td width=\"100\"><strong>Field Name </strong></td><td width=\"100\"><strong>Data Type </strong></td><td align=\"center\" width=\"100\"><strong>Mandatory </strong></td><td width=\"300\"><strong>Description </strong></td></tr><tr style=\"background: #ccc !important;\"><td>warehouseCode</td><td>Varchar(10)</td><td align=\"center\">Mandatory</td><td>eRetail Warehouse Code. API will validate warehouse code from WMS API configuration.</td></tr><tr style=\"background: #0d0 !important;\"><td>packStationName</td><td>Varchar(10)</td><td align=\"center\">Mandatory</td><td>Name of the pack station with respect to warehouse</td></tr><tr style=\"background: #ccc !important;\"><td>packStationIpaddress</td><td>Varchar(20)</td><td align=\"center\">Mandatory</td><td>Currect IP address of the pack station</td></tr><tr style=\"background: #0d0 !important;\"><td>packStationHostname</td><td>Varchar(20)</td><td align=\"center\">Optional</td><td>Name of the pack station hostname</td></tr><tr><td colspan=\"4\"><p><strong>Input JSON Format- <br/>{ <br/>&nbsp; &nbsp;\"warehouseCode\":\"XXXX\", <br/>&nbsp; &nbsp;\"packStationName\":\"xxxx\", <br/>&nbsp; &nbsp;\"packStationIpaddress\":\"xxx.xxx.xxxx.xxx\", <br/>&nbsp; &nbsp;\"packStationHostname\":\"XXXXXXXXXXXX\" <br/>}</strong></p></td></tr></table>"),
				@ApiImplicitParam(name = "guid", dataType = "String", required = true, paramType = "query", value = "API Owner"),
				@ApiImplicitParam(name = "token", dataType = "String", required = true, paramType = "query", value = "API Key")
			})
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<ServiceResponse> addNewPackStation(@RequestParam(name="guid") String guid, 
																	  @RequestParam(name="token") String token, 
																	  @RequestParam(name = "RequestBody") String body){
		logger.info("addNewPackStation(...)-------START");
		ServiceResponse response = new ServiceResponse();
		Map<String, Object> bodyMap = null;
		String warehouseCode		= null;
		String packStationName		= null;
		String packStationHostname	= null;
		String packStationMachinename= null;
		String packStationIpaddress	= null;
		String createdBy			= guid;
		int psListenPort			= 0;
		String requestId			= null;
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey(SBConstant.VAR_WAREHOUSE_CODE)) {
				warehouseCode = (String) bodyMap.get(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(bodyMap.containsKey(SBConstant.VAR_PS_NAME)) {
				packStationName = (String) bodyMap.get(SBConstant.VAR_PS_NAME);
			}
			if(bodyMap.containsKey(SBConstant.VAR_PS_HOST_NAME)) {
				packStationHostname = (String) bodyMap.get(SBConstant.VAR_PS_HOST_NAME);
			}
			if(bodyMap.containsKey(SBConstant.VAR_PS_MACHINE_NAME)) {
				packStationMachinename = (String) bodyMap.get(SBConstant.VAR_PS_MACHINE_NAME);
			}
			if(bodyMap.containsKey(SBConstant.VAR_PS_IP_ADDRESS)) {
				packStationIpaddress = (String) bodyMap.get(SBConstant.VAR_PS_IP_ADDRESS);
			}
			try {
				psListenPort = Integer.parseInt(SBUtils.getPropertyValue("ps.server.listen.port"));
			} catch (NumberFormatException e) {
				psListenPort = SBConstant.VAR_PS_LISTENER_PORT;
			}
			if(bodyMap.containsKey(SBConstant.VAR_REQUEST_ID)) {
				requestId = (String) bodyMap.get(SBConstant.VAR_REQUEST_ID);
			} else {
				requestId = UUID.randomUUID().toString();
			}
			response.setRequestId(requestId);
		} catch (IOException e) {
			logger.error("IOException :: addNewPackStation(...)", e);
		}
		if(SBUtils.isNull(warehouseCode) || SBUtils.isNull(packStationName) || SBUtils.isNull(packStationIpaddress)) {
			StringBuffer args = new StringBuffer();
			if(SBUtils.isNull(warehouseCode)) {
				args.append(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(SBUtils.isNull(packStationName)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_PS_NAME);
			}
			if(SBUtils.isNull(packStationIpaddress)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_PS_IP_ADDRESS);
			}
			logger.error("SellerClientException :: addNewPackStation(...)", "Following arguments "+args.toString()+" is mandarory to create a new pack station");
			throw new SellerClientException("Following arguments "+args.toString()+" is mandarory to create a new pack station");
		} else {
			EdiPackStationInfo psInfo = ediPSInfoDao.findByPackStationNameAndPackStationLocation(packStationName, warehouseCode);
			if(psInfo == null) {
				try {
					psInfo = new EdiPackStationInfo();
					psInfo.setPackStationName(packStationName);
					psInfo.setPackStationLocation(warehouseCode);
					psInfo.setPackStationHostname(packStationHostname);
					psInfo.setPackStationMachinename(packStationMachinename);
					psInfo.setPackStationIpaddress(packStationIpaddress);
					psInfo.setIsActive(0);
					psInfo.setCreatedBy(createdBy);
					psInfo.setCreatedDate(new Date());
					psInfo.setPsListenPort(psListenPort);
					psInfo = ediPSInfoDao.save(psInfo);
					response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_SUCCESS);
					response.setStatus(SBConstant.TXN_STATUS_SUCCESS);
					response.setResponseMessage("Pack station "+ packStationName + " under warehouse " + warehouseCode+ "created successfully.");
					response.setUniqueKey(psInfo.getPackStationId().toString());
					response.setErrorDesc(null);
				} catch (Exception e) {
					response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_EXCEPTION);
					response.setStatus(SBConstant.TXN_STATUS_EXCEPTION);
					response.setErrorDesc("Unable to create pack station "+ packStationName + " under warehouse " + warehouseCode+SBConstant.LOG_SEPRATOR+e.getMessage());
					response.setResponseMessage("Unable to create pack station "+ packStationName + " under warehouse " + warehouseCode);
					logger.error("Exception :: addNewPackStation(...)", e);
				}
			} else {
				response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_DUPLICATE);
				response.setStatus(SBConstant.TXN_STATUS_DUPLICATE);
				response.setUniqueKey(psInfo.getPackStationId().toString());
				response.setResponseMessage("Duplicate request for PS name = " + packStationName + " against warehouse = " + warehouseCode);
			}
			logger.info("addNewPackStation(...) : Response >>> "+response.toString());
			logger.info("addNewPackStation(...)-------END");
			return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
		}
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Create new pack station")
	@ApiImplicitParams(
			{ 
				@ApiImplicitParam(name = "RequestBody", dataType = "String", required = true, paramType = "query", value = "<table border=\"1\"><tr style=\"background: green !important;\"><td width=\"100\"><strong>Field Name </strong></td><td width=\"100\"><strong>Data Type </strong></td><td align=\"center\" width=\"100\"><strong>Mandatory </strong></td><td width=\"300\"><strong>Description </strong></td></tr><tr style=\"background: #ccc !important;\"><td>warehouseCode</td><td>Varchar(10)</td><td align=\"center\">Mandatory</td><td>eRetail Warehouse Code. API will validate warehouse code from WMS API configuration.</td></tr><tr style=\"background: #0d0 !important;\"><td>packStationName</td><td>Varchar(10)</td><td align=\"center\">Mandatory</td><td>Name of the pack station with respect to warehouse</td></tr><tr style=\"background: #ccc !important;\"><td>packStationIpaddress</td><td>Varchar(20)</td><td align=\"center\">Mandatory</td><td>Currect IP address of the pack station</td></tr><tr style=\"background: #0d0 !important;\"><td>packStationHostname</td><td>Varchar(20)</td><td align=\"center\">Optional</td><td>Name of the pack station hostname</td></tr><tr><td colspan=\"4\"><p><strong>Input JSON Format- <br/>{ <br/>&nbsp; &nbsp;\"warehouseCode\":\"XXXX\", <br/>&nbsp; &nbsp;\"packStationName\":\"xxxx\", <br/>&nbsp; &nbsp;\"packStationIpaddress\":\"xxx.xxx.xxxx.xxx\", <br/>&nbsp; &nbsp;\"packStationHostname\":\"XXXXXXXXXXXX\" <br/>}</strong></p></td></tr></table>"),
				@ApiImplicitParam(name = "guid", dataType = "String", required = true, paramType = "query", value = "API Owner"),
				@ApiImplicitParam(name = "token", dataType = "String", required = true, paramType = "query", value = "API Key")
			})
	@RequestMapping(value = "/addPrinter", method = RequestMethod.POST, consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<ServiceResponse> addNewPrinterToPackStation(@RequestParam(name="guid") String guid, 
																	  @RequestParam(name="token") String token, 
																	  @RequestParam(name = "RequestBody") String body){
		logger.info("addNewPrinterToPackStation(...)-------START");
		ServiceResponse response = new ServiceResponse();
		Map<String, Object> bodyMap = null;
		String warehouseCode		= null;
		Long packStationId			= 0L;
		String packStationName		= null;
		String printerType			= null;
		String printerName			= null;
		String requestId			= null;
		double mediaX				= 0d;
		double mediaY				= 0d;
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey(SBConstant.VAR_WAREHOUSE_CODE)) {
				warehouseCode = (String) bodyMap.get(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(bodyMap.containsKey(SBConstant.VAR_PS_NAME)) {
				packStationName = (String) bodyMap.get(SBConstant.VAR_PS_NAME);
			}
			if(bodyMap.containsKey(SBConstant.VAR_PS_ID)) {
				if(!SBUtils.isNull(bodyMap.get(SBConstant.VAR_PS_ID))) {
					try {
						packStationId = Long.parseLong(bodyMap.get(SBConstant.VAR_PS_ID).toString());
					} catch (NumberFormatException e) {
						packStationId = 0L;
					}
				}
			}
			if(bodyMap.containsKey(SBConstant.VAR_PS_PRINTER_TYPE)) {
				printerType = (String) bodyMap.get(SBConstant.VAR_PS_PRINTER_TYPE);
			}
			if(bodyMap.containsKey(SBConstant.VAR_PS_PRINTER_NAME)) {
				printerName = (String) bodyMap.get(SBConstant.VAR_PS_PRINTER_NAME);
			}

			if(bodyMap.containsKey("mediaX")) {
				if(!SBUtils.isNull(bodyMap.get("mediaX"))) {
					try {
						mediaX = Double.valueOf(bodyMap.get("mediaX").toString());
					} catch (NumberFormatException e) {
						mediaX = 0d;
					}
				}
			}

			if(bodyMap.containsKey("mediaY")) {
				if(!SBUtils.isNull(bodyMap.get("mediaY"))) {
					try {
						mediaY = Double.valueOf(bodyMap.get("mediaY").toString());
					} catch (NumberFormatException e) {
						mediaY = 0d;
					}
				}
			}
			
			if(bodyMap.containsKey(SBConstant.VAR_REQUEST_ID)) {
				requestId = (String) bodyMap.get(SBConstant.VAR_REQUEST_ID);
			} else {
				requestId = UUID.randomUUID().toString();
			}
			response.setRequestId(requestId);
		} catch (IOException e) {
			logger.error("IOException :: addNewPrinterToPackStation(...)", e);
		}
		if(SBUtils.isNull(warehouseCode) || SBUtils.isNull(packStationName) || SBUtils.isNull(packStationId) || SBUtils.isNull(printerName) || SBUtils.isNull(printerType)) {
			StringBuffer args = new StringBuffer();
			if(SBUtils.isNull(warehouseCode)) {
				args.append(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(SBUtils.isNull(packStationName) || SBUtils.isNull(packStationId)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_PS_NAME).append(" OR ").append(SBConstant.VAR_PS_ID);
			}
			if(SBUtils.isNull(printerName)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_PS_PRINTER_NAME);
			}
			if(SBUtils.isNull(printerType)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_PS_PRINTER_TYPE);
			}
			logger.error("SellerClientException :: addNewPrinterToPackStation(...)", "Following arguments "+args.toString()+" is mandarory to add new printer.");
			throw new SellerClientException("Following arguments "+args.toString()+" is mandarory to add new printer.");
		} else {
			EdiPackStationInfo psInfo = null;
			if(!SBUtils.isNull(packStationId)) {
				psInfo = ediPSInfoDao.findByPackStationId(packStationId);
			}
			if(psInfo == null && !SBUtils.isNull(packStationName) && !SBUtils.isNull(warehouseCode)) {
				psInfo = ediPSInfoDao.findByPackStationNameAndPackStationLocation(packStationName, warehouseCode);
			}
			if(psInfo != null) {
				if(!SBUtils.isNull(psInfo.getPackStationId()) && !SBUtils.isNull(printerType)) {
					EdiPackStationPrinter psPrinter = ediPSPrinterDao.findByPackStationIdAndPrinterType(psInfo.getPackStationId(), printerType);
					if(psPrinter == null) {
						try {
							psPrinter = new EdiPackStationPrinter();
							psPrinter.setPackStationId(psInfo.getPackStationId());
							psPrinter.setPrinterType(printerType);
							psPrinter.setPrinterName(printerName);
							psPrinter.setPrinterStatus(0);
							if (Double.compare(mediaX, 0d) > 0) {
								psPrinter.setMediaX(mediaX);
							} else {
								psPrinter.setMediaX(0.0);
							}
							if (Double.compare(mediaY, 0d) > 0) {
								psPrinter.setMediaY(mediaY);
							} else {
								psPrinter.setMediaY(0.0);
							}
							psPrinter.setMediaW(101.6);
							psPrinter.setMediaH(152.4);
							
							Set<EdiPackStationPrinter> printers = psInfo.getEdiPackStationPrinter();
							printers.add(psPrinter);
							psInfo.setModifiedBy(guid);
							psInfo.setModifiedDate(new Date());
							psInfo = ediPSInfoDao.save(psInfo);
							for(EdiPackStationPrinter p : psInfo.getEdiPackStationPrinter()) {
								if(p.getPrinterType().equalsIgnoreCase(printerType)) {
									response.setUniqueKey(p.getPrinterId().toString());
								}
							}
							//psPrinter = ediPSPrinterDao.save(psPrinter);
							response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_SUCCESS);
							response.setStatus(SBConstant.TXN_STATUS_SUCCESS);
							response.setResponseMessage("Printer "+printerType+" added successfully for pack station "+ packStationName + " under warehouse " + warehouseCode);
							//response.setUniqueKey(psInfo.getPackStationId().toString());
							response.setErrorDesc(null);
						} catch (Exception e) {
							response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_EXCEPTION);
							response.setStatus(SBConstant.TXN_STATUS_EXCEPTION);
							response.setErrorDesc("Unable to add printer "+printerType+" for pack station "+ packStationName + " under warehouse " + warehouseCode+SBConstant.LOG_SEPRATOR+e.getMessage());
							response.setResponseMessage("Unable to add printer "+printerType+" for pack station "+ packStationName + " under warehouse " + warehouseCode);
							logger.error("Exception :: addNewPrinterToPackStation(...)", e);
						}
					} else {
						response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_DUPLICATE);
						response.setStatus(SBConstant.TXN_STATUS_DUPLICATE);
						response.setUniqueKey(psInfo.getPackStationId().toString());
						response.setResponseMessage("Duplicate request for adding printer "+ printerType+" for pack station " + packStationName + " against warehouse = " + warehouseCode);
					}
				}
			} else {
				response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_NO_DATA);
				response.setStatus(SBConstant.TXN_STATUS_NO_DATA);
				response.setUniqueKey(null);
				response.setResponseMessage("Unable to find PS name = " + packStationName + " or PS Id = "+ packStationId+" against warehouse = " + warehouseCode);
			}
			logger.info("addNewPrinterToPackStation(...) : Response >>> "+response.toString());
			logger.info("addNewPrinterToPackStation(...)-------END");
			return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
		}
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Download JNLP file")
	@ApiImplicitParams(
			{ 
				@ApiImplicitParam(name = "RequestBody", dataType = "String", required = true, paramType = "query", value = "<table border=\"1\"><tr style=\"background: green !important;\"><td width=\"100\"><strong>Field Name </strong></td><td width=\"100\"><strong>Data Type </strong></td><td align=\"center\" width=\"100\"><strong>Mandatory </strong></td><td width=\"300\"><strong>Description </strong></td></tr><tr style=\"background: #ccc !important;\"><td>warehouseCode</td><td>Varchar(10)</td><td align=\"center\">Mandatory</td><td>eRetail Warehouse Code. API will validate warehouse code from WMS API configuration.</td></tr><tr style=\"background: #0d0 !important;\"><td>packStationName</td><td>Varchar(10)</td><td align=\"center\">Mandatory</td><td>Name of the pack station with respect to warehouse</td></tr><tr style=\"background: #ccc !important;\"><td>packStationIpaddress</td><td>Varchar(20)</td><td align=\"center\">Mandatory</td><td>Currect IP address of the pack station</td></tr><tr style=\"background: #0d0 !important;\"><td>packStationHostname</td><td>Varchar(20)</td><td align=\"center\">Optional</td><td>Name of the pack station hostname</td></tr><tr><td colspan=\"4\"><p><strong>Input JSON Format- <br/>{ <br/>&nbsp; &nbsp;\"warehouseCode\":\"XXXX\", <br/>&nbsp; &nbsp;\"packStationName\":\"xxxx\", <br/>&nbsp; &nbsp;\"packStationIpaddress\":\"xxx.xxx.xxxx.xxx\", <br/>&nbsp; &nbsp;\"packStationHostname\":\"XXXXXXXXXXXX\" <br/>}</strong></p></td></tr></table>"),
				@ApiImplicitParam(name = "guid", dataType = "String", required = true, paramType = "query", value = "API Owner"),
				@ApiImplicitParam(name = "token", dataType = "String", required = true, paramType = "query", value = "API Key")
			})
	@RequestMapping(value = "/downloadJnlpFile", method = {RequestMethod.GET, RequestMethod.POST}, consumes = {MediaType.ALL_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Map<String, String>> downloadJnlpFile(@RequestParam(name="guid") String guid, 
															@RequestParam(name="token") String token, 
															@RequestParam(name = "RequestBody") String body){
		logger.info("downloadJnlpFile(...)-------START");
		String jnlpString = null;
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		//HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		String remoteAddress = request.getRemoteAddr();
		System.out.println(remoteAddress);
		Map<String, Object> bodyMap = null;
		String warehouseCode		= null;
		String psName				= null;
		String requestId			= null;
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey(SBConstant.VAR_WAREHOUSE_CODE)) {
				warehouseCode = (String) bodyMap.get(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(bodyMap.containsKey(SBConstant.VAR_PS_NAME)) {
				psName = (String) bodyMap.get(SBConstant.VAR_PS_NAME);
			}
			if(bodyMap.containsKey(SBConstant.VAR_REQUEST_ID)) {
				requestId = (String) bodyMap.get(SBConstant.VAR_REQUEST_ID);
			} else {
				requestId = UUID.randomUUID().toString();
			}
		} catch (IOException e) {
			logger.error("IOException :: downloadJnlpFile(...)", e);
		}
		if(SBUtils.isNull(warehouseCode) || SBUtils.isNull(psName)) {
			StringBuffer args = new StringBuffer();
			if(SBUtils.isNull(warehouseCode)) {
				args.append(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(SBUtils.isNull(psName)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_PS_NAME);
			}
			logger.error(requestId+SBConstant.LOG_SEPRATOR+"SellerClientException :: downloadJnlpFile(...)", "Following arguments "+args.toString()+" is mandarory to register pack station.");
			throw new SellerClientException("Following arguments "+args.toString()+" is mandarory to register pack station.");
		} else {
			jnlpString = packStationService.downloadJnlp(requestId, warehouseCode, psName, token);
		}
		
	    Map<String, String> result = new HashMap<String, String>();
	    if(jnlpString != null) {
	    	result.put("JNLP", jnlpString);
	    } else {
	    	result.put("ERROR", "Unable to register packs station "+psName+", Please try again.");
	    }
		logger.info(requestId+SBConstant.LOG_SEPRATOR+"downloadJnlpFile(...)-------END");
		return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
	}
}
