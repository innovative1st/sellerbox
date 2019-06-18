package com.seller.box.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

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

import com.amazonaws.services.gtsexternalsecurity.model.PreparePackageForShippingResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonSyntaxException;
import com.seller.box.amazon.gts.GTSExternalService;
import com.seller.box.core.ManifestResponse;
import com.seller.box.dao.EdiShipmentDao;
import com.seller.box.entities.EdiShipmentHdr;
import com.seller.box.form.Shipment;
import com.seller.box.form.ShipmentItem;
import com.seller.box.service.DocPrintService;
import com.seller.box.service.OrderService;
import com.seller.box.utils.SBConstant;
import com.seller.box.utils.SBUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags= {"Mamifest"}, position = 7, value="Mamifest", consumes= "application/x-www-form-urlencoded", description="API to call amazon manifest API.")
@RequestMapping("/mamifest")
public class ManifestController {
	private static final Logger logger = LogManager.getLogger(ManifestController.class);
	@Autowired
	OrderService orderService;
	@Autowired
	EdiShipmentDao ediShipmentDao;
	@Autowired
	DocPrintService docPrintService;
	@SuppressWarnings({ "unchecked"})
	@ApiOperation(value = "API to prepare amazon package for shipping.")
	@PostMapping(value = "/orderManifest", consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<ManifestResponse> callPreparePackageForShipping(@RequestParam(name="guid") String guid, 
																			  @RequestParam(name="token") String token, 
																			  @RequestParam(name = "RequestBody") String body){
		logger.info("callPreparePackageForShipping(...)"+SBConstant.LOG_SEPRATOR_WITH_START);
		ManifestResponse response = new ManifestResponse();
        /****************************************************
        * Calling PreparePackageForShipping API
        ****************************************************/
		Map<String, Object> bodyMap = null;
		int etailorId				= 0;
		String ediOrderId			= null;
		String warehouseCode		= null;
		String requestId			= null;
		String psName				= null;
		String psIpAddess			= null;
		
		//Shipment shipment 			= null;
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
			if(bodyMap.containsKey(SBConstant.VAR_WAREHOUSE_CODE)) {
				warehouseCode = (String) bodyMap.get(SBConstant.VAR_WAREHOUSE_CODE);
			} 
			if(bodyMap.containsKey(SBConstant.VAR_REQUEST_ID)) {
				requestId = (String) bodyMap.get(SBConstant.VAR_REQUEST_ID);
			} else {
				requestId = UUID.randomUUID().toString();
			}
//			if(bodyMap.containsKey("Shipment")) {
//				Gson g = new Gson();
//				shipment = g.fromJson((String)bodyMap.get("Shipment"), Shipment.class);
//				if(shipment == null) {
//					if(!SBUtils.isNull(ediOrderId)) {
//						shipment = orderService.getShipmentForPacking(Long.parseLong(ediOrderId));
//					} else {
//						logger.error("callPreparePackageForShipping(...) :: Shipment details and ediOrderId both is null.");
//					}
//				}
//				if(shipment != null) {
//					if (SBUtils.isNull(warehouseCode)) {
//						warehouseCode = shipment.getOriginWarehouseCode();
//					}
//					if(SBUtils.isNull(ediOrderId)) {
//						ediOrderId = String.valueOf(shipment.getEdiOrderId());
//					}
//				}
//			}
			if(SBUtils.isNull(ediOrderId) || (SBUtils.isNull(psName) && SBUtils.isNull(psIpAddess)) || SBUtils.isNull(etailorId) || SBUtils.isNull(warehouseCode))  {
				StringBuffer args = new StringBuffer();
				if(SBUtils.isNull(ediOrderId)) {
					args.append("ediOrderId");
				}
				if(SBUtils.isNull(psName) && SBUtils.isNull(psIpAddess)) {
					args.append(args.length() > 0 ? ", " : "").append("Either packStationName or packStationIpaddress");
				}
				if(SBUtils.isNull(SBConstant.VAR_ETAILOR_ID)) {
					args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_ETAILOR_ID);
				} 
				if(SBUtils.isNull(SBConstant.VAR_WAREHOUSE_CODE)) {
					args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_WAREHOUSE_CODE);
				}
				if(args.length() > 0) {
					response.setManifestExceptionMessage("Arguments "+args+" is mandatory to manifest order.");
					response.setManifestStatus(SBConstant.TXN_STATUS_ARGS_MISSING);
				}
			}
			
		} catch (JsonSyntaxException e) {
			response.setManifestExceptionMessage("JsonSyntaxException occured");
			response.setManifestStatus(SBConstant.TXN_STATUS_EXCEPTION);
			logger.info("JsonSyntaxException :: callPreparePackageForShipping(...)", e);
		} catch (NumberFormatException e) {
			response.setManifestExceptionMessage("NumberFormatException occured");
			response.setManifestStatus(SBConstant.TXN_STATUS_EXCEPTION);
			logger.info("NumberFormatException :: callPreparePackageForShipping(...)", e);
		} catch (IOException e) {
			response.setManifestExceptionMessage("IOException occured");
			response.setManifestStatus(SBConstant.TXN_STATUS_EXCEPTION);
			logger.info("IOException :: callPreparePackageForShipping(...)", e);
		} catch (Exception e) {
			response.setManifestExceptionMessage("General Exception occured");
			response.setManifestStatus(SBConstant.TXN_STATUS_EXCEPTION);
			logger.info("General Exception :: callPreparePackageForShipping(...)", e);
		} finally {
			if(ediOrderId != null) {
				EdiShipmentHdr sh = ediShipmentDao.findByEdiOrderId(Long.parseLong(ediOrderId));
				Shipment shipment = orderService.getShipmentForPacking(Long.parseLong(ediOrderId), sh);
				PreparePackageForShippingResult result = new GTSExternalService().getPreparePackageForShipping(requestId, shipment);
				if (result != null) {
                    logger.info(requestId+SBConstant.LOG_SEPRATOR+"PreparePackageForShipping success, TrackingId = "+ shipment.getTrackingId());
                    printDocuments(response, requestId, psIpAddess, shipment);
                    try {
						sh.setTrackingId(shipment.getTrackingId());
						sh.setBarcode(shipment.getBarcode());
						sh.setCarrierName(shipment.getCarrierName());
						sh.setPickupDate(shipment.getReadyToPickUpTimeUTC());
						sh.setShipLabelFilepath(shipment.getShiplabelFilepath());
						sh.setManifestDate(SBUtils.getTxnSysDateTime());
						sh.setPackedBy(guid);
						sh = ediShipmentDao.save(sh);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					//TODO
				}
			}
		}
		
		
		return new ResponseEntity<ManifestResponse>(response, HttpStatus.OK);
	}
	
	
	private void printDocuments(ManifestResponse response, String requestId, String psIpAddess, Shipment shipment) {
		
        /*************************************************************
        *     Calling Ship Label Print API                           *
        *************************************************************/
		if (shipment.getShiplabelFilepath() != null) {
			response.setShiplabelFilepath(shipment.getShiplabelFilepath());
			Properties status = docPrintService.printShipLabel(requestId, psIpAddess, shipment.getShipmentId(), shipment.getShiplabelFilepath());
			if(status.getProperty(SBConstant.PRINT_PROPERY_STATUS).equalsIgnoreCase(SBConstant.PRINT_STATUS_200_OK)) {
				response.setShiplabelPrintStatus(SBConstant.PRINT_STATUS_SUCCESS);
			} else {
				response.setShiplabelPrintStatus(SBConstant.PRINT_STATUS_FAILED);
			}
		} else {
			response.setShiplabelPrintStatus(SBConstant.PRINT_STATUS_FILE_NOT_FOUND);
		}
              
        /*************************************************************
        *     Calling Invoice Copy Print API                         *
        *************************************************************/
		if (shipment.getInvoiceFilepath() != null) {
			response.setInvoiceFilepath(shipment.getInvoiceFilepath());
			Properties status = docPrintService.printPackingSlip(requestId, psIpAddess, shipment.getShipmentId(), shipment.getInvoiceFilepath());
			if(status.getProperty(SBConstant.PRINT_PROPERY_STATUS).equalsIgnoreCase(SBConstant.PRINT_STATUS_200_OK)) {
				response.setInvoicePrintStatus(SBConstant.PRINT_STATUS_SUCCESS);
			} else {
				response.setInvoicePrintStatus(SBConstant.PRINT_STATUS_FAILED);
			}
		} else {
			response.setInvoicePrintStatus(SBConstant.PRINT_STATUS_FILE_NOT_FOUND);
		}
		
		/*************************************************************
	    *     Calling Gift Card Print API                            *
	    *************************************************************/
		if (shipment.getIsGift() > 0) {
			List<String> giftNoteCards = new ArrayList<String>();
			for(ShipmentItem item : shipment.getShipmentItem()) {
				if(item.getGiftLabelContent() != null) {
					giftNoteCards.add(item.getGiftLabelContent());
				}
			}
			if(giftNoteCards.size() > 0) {
		    	Properties status = docPrintService.printGiftNoteCard(requestId, psIpAddess, shipment.getShipmentId(), giftNoteCards);
		    	if(status.getProperty(SBConstant.PRINT_PROPERY_STATUS).equalsIgnoreCase(SBConstant.PRINT_STATUS_200_OK)) {
		    		response.setInvoicePrintStatus(SBConstant.PRINT_STATUS_SUCCESS);
		    	} else {
		    		response.setInvoicePrintStatus(SBConstant.PRINT_STATUS_FAILED);
		    	}
			} else {
		    	response.setGiftPrintStatus(SBConstant.PRINT_STATUS_NA);
		    }
		} else {
			response.setGiftPrintStatus(SBConstant.PRINT_STATUS_NA);
		}
	}
}
