package com.seller.box.controller;

import java.io.IOException;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seller.box.core.ManifestResponse;
import com.seller.box.dao.EdiPackStationInfoDao;
import com.seller.box.dao.EdiShipmentDao;
import com.seller.box.entities.EdiPackStationInfo;
import com.seller.box.entities.EdiShipmentHdr;
import com.seller.box.form.Shipment;
import com.seller.box.service.DocPrintService;
import com.seller.box.service.ManifestService;
import com.seller.box.service.OrderService;
import com.seller.box.utils.SBConstant;
import com.seller.box.utils.SBUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags= {"Manifest"}, position = 7, value="Manifest", consumes= "application/x-www-form-urlencoded", description="API to call amazon manifest API.")
@RequestMapping("/manifest")
public class ManifestController {
	private static final Logger logger = LogManager.getLogger(ManifestController.class);
	@Autowired
	OrderService orderService;
	@Autowired
	EdiShipmentDao ediShipmentDao;
	@Autowired
	DocPrintService docPrintService;
	@Autowired
    EdiPackStationInfoDao ediPSInfoDao;
	@Autowired
	ManifestService manifestService;
	@SuppressWarnings({ "unchecked"})
	@ApiOperation(value = "API to prepare amazon package for shipping.")
	@PostMapping(value = "/orderManifest", consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<ManifestResponse> orderManifest(@RequestParam(name="guid") String guid, 
														  @RequestParam(name="token") String token, 
														  @RequestParam(name = "RequestBody") String body){
		logger.info("orderManifest(...)"+SBConstant.LOG_SEPRATOR_WITH_START);
		ManifestResponse response = new ManifestResponse();
        /****************************************************
        * Calling PreparePackageForShipping API
        ****************************************************/
		Map<String, Object> bodyMap = null;
		String ediOrderId			= null;
		String warehouseCode		= null;
		String requestId			= null;
		String psName				= null;
		String psIpAddess			= null;
		int psListenPort			= 1331;
		String logPrefix 			= null; 
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			//HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
			
			String remoteAddress = request.getRemoteAddr();
			response.setManifestBy(guid);
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey(SBConstant.VAR_WAREHOUSE_CODE)) {
				warehouseCode = (String) bodyMap.get(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(bodyMap.containsKey(SBConstant.VAR_REQUEST_ID)) {
				requestId = (String) bodyMap.get(SBConstant.VAR_REQUEST_ID);
			} else {
				requestId = UUID.randomUUID().toString();
			}
			response.setRequestId(requestId);	
			logPrefix = requestId+SBConstant.LOG_SEPRATOR;
			if(bodyMap.containsKey(SBConstant.VAR_EDI_ORDER_ID)) {
				ediOrderId = (String) bodyMap.get(SBConstant.VAR_EDI_ORDER_ID);
				try {
					Long.parseLong(ediOrderId);
				} catch (NumberFormatException e) {
					ediOrderId = null;
				}
			} 
			if(bodyMap.containsKey(SBConstant.VAR_PS_NAME)) {
				psName = (String) bodyMap.get(SBConstant.VAR_PS_NAME);
			} 
			if(bodyMap.containsKey(SBConstant.VAR_PS_IP_ADDRESS)) {
				psIpAddess = (String) bodyMap.get(SBConstant.VAR_PS_IP_ADDRESS);
			}
			if(SBUtils.isNull(ediOrderId) || SBUtils.isNull(psName) || SBUtils.isNull(warehouseCode))  {
				StringBuffer args = new StringBuffer();
				if(SBUtils.isNull(ediOrderId)) {
					args.append("ediOrderId");
				}
				if(SBUtils.isNull(psName)) {
					args.append(args.length() > 0 ? ", " : "").append("packStationName");
				}
				if(SBUtils.isNull(SBConstant.VAR_WAREHOUSE_CODE)) {
					args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_WAREHOUSE_CODE);
				}
				if(args.length() > 0) {
					response.setManifestExceptionMessage("Arguments "+args+" is mandatory to manifest order.");
					response.setManifestStatus(SBConstant.TXN_STATUS_ARGS_MISSING);
				}
			}
		} catch (IOException e) {
			response.setManifestExceptionMessage("IOException occured");
			response.setManifestStatus(SBConstant.TXN_STATUS_EXCEPTION);
			logger.error(logPrefix+"IOException :: orderManifest(...)", e);
		} catch (Exception e) {
			response.setManifestExceptionMessage("General Exception occured");
			response.setManifestStatus(SBConstant.TXN_STATUS_EXCEPTION);
			logger.error(logPrefix+"General Exception :: orderManifest(...)", e);
		} finally {
			if(SBUtils.isNull(psIpAddess)) {
				if(SBUtils.isNotNull(psName)) {
					EdiPackStationInfo psInfo = ediPSInfoDao.findByPackStationNameAndPackStationLocation(psName, warehouseCode);
					psIpAddess 	= psInfo.getPackStationIpaddress();
					psListenPort= psInfo.getPsListenPort();
					response.setPsIpAddess(psIpAddess);
					response.setPsListenPort(psListenPort);
				}
			}
			
			if(ediOrderId != null) {
				EdiShipmentHdr sh = ediShipmentDao.findByEdiOrderId(Long.parseLong(ediOrderId));
				Shipment shipment = orderService.getShipmentForPacking(Long.parseLong(ediOrderId), sh);
				
				response.setEdiOrderId(sh.getEdiOrderId());
				response.setShipmentId(sh.getShipmentId());
				manifestService.manifestOrder(response, sh, shipment);
			}
		}
		logger.info(logPrefix+"orderManifest(...)"+SBConstant.LOG_SEPRATOR_WITH_END);
		return new ResponseEntity<ManifestResponse>(response, HttpStatus.OK);
	}
}
