package com.seller.box.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.gtsexternalsecurity.model.GetPrintableManifestsForTrailerResult;
import com.amazonaws.services.gtsexternalsecurity.model.ManifestDocuments;
import com.amazonaws.services.gtsexternalsecurity.model.ManifestPackagesByIdsResult;
import com.amazonaws.services.gtsexternalsecurity.model.PreparePackageForShippingResult;
import com.seller.box.amazon.gts.GTSExternalService;
import com.seller.box.controller.ManifestController;
import com.seller.box.core.ManifestResponse;
import com.seller.box.dao.EdiShipmentDao;
import com.seller.box.entities.EdiShipmentHdr;
import com.seller.box.form.Shipment;
import com.seller.box.form.ShipmentItem;
import com.seller.box.service.DocPrintService;
import com.seller.box.service.ManifestService;
import com.seller.box.service.OrderService;
import com.seller.box.utils.SBConstant;
import com.seller.box.utils.SBUtils;

@Service
public class ManifestServiceImpl implements ManifestService {
	private static final Logger logger = LogManager.getLogger(ManifestController.class);
	@Autowired
	OrderService orderService;
	@Autowired
	EdiShipmentDao ediShipmentDao;
	@Autowired
	DocPrintService docPrintService;
	@Autowired
	GTSExternalService gtsExternalService;
	//private GTSExternalSecurityService gts = null;
	@Override
	public void manifestOrder(ManifestResponse response, EdiShipmentHdr sh, Shipment shipment) {
		String logPrefix  = response.getRequestId()+SBConstant.LOG_SEPRATOR;
		logger.info(logPrefix+"manifestOrder(...)"+SBConstant.LOG_SEPRATOR_WITH_START);
		try {
			if (sh == null) {
				sh = ediShipmentDao.findByEdiOrderId(response.getEdiOrderId());
			}
			if (shipment == null) {
				shipment = orderService.getShipmentForPacking(response.getEdiOrderId(), sh);
			}
			//if(sh.getEdiOrderId() == 94)-------------//TODO for channels integration
            if (response.getTrackingId() == null || response.getShiplabelFilepath() == null) {            	
				/****************************************************
		        * Calling PreparePackageForShipping API
		        ****************************************************/
				preparePackageForShipping(response, sh, shipment);
			}
		} catch (Exception e) {
			logger.error(logPrefix+"Exception :: manifestOrder(...)", e);
			response.setManifestErrorMessage("Manifest failed, General exception occured...!!!");
			response.setManifestStatus(SBConstant.TXN_STATUS_FAILURE);
		} finally {
			if(response.getManifestStatus().equalsIgnoreCase(SBConstant.TXN_STATUS_SUCCESS)) {
                if (!response.isCanManifest()) {
					menifestPackagesById(response, sh, shipment);
				}
				if (SBUtils.isNull(response.getManifestId())) {
					//TODO enable once live callPrintableManifestForTrailer(response, sh, shipment);
				}
			}
		}
		logger.info(logPrefix+"manifestOrder(...)"+SBConstant.LOG_SEPRATOR_WITH_END);
	}

    /****************************************************
    * Calling PreparePackageForShipping API
    ****************************************************/
	@Override
	public void preparePackageForShipping(ManifestResponse response, EdiShipmentHdr sh, Shipment shipment) {
		String logPrefix  = response.getRequestId()+SBConstant.LOG_SEPRATOR;
		boolean flag = false;
		try {
			if (response.getTrackingId() == null || response.getShiplabelFilepath() == null) {
				PreparePackageForShippingResult result = gtsExternalService.getPreparePackageForShipping(response.getRequestId(), shipment);
				if(result != null) {
					flag = true;
					response.setManifestStatus(SBConstant.TXN_STATUS_SUCCESS);
					response.setShipmentId(shipment.getShipmentId());
					response.setTrackingId(shipment.getTrackingId());
					response.setShiplabelFilepath(shipment.getShiplabelFilepath());
					response.setBarcode(shipment.getBarcode());
					response.setCarrierName(shipment.getCarrierName());
					response.setPickupDate(shipment.getReadyToPickUpTimeUTC());
				} 
			} else {
				flag = true;
				response.setManifestStatus(SBConstant.TXN_STATUS_SUCCESS);
			}
			if (flag) {
			    logger.info(logPrefix+"PreparePackageForShipping success, TrackingId = "+ shipment.getTrackingId());
			    printDocuments(response, shipment);
			    try {
					sh.setTrackingId(shipment.getTrackingId());
					sh.setBarcode(shipment.getBarcode());
					sh.setCarrierName(shipment.getCarrierName());
					sh.setPickupDate(shipment.getReadyToPickUpTimeUTC());
					sh.setShipLabelFilepath(shipment.getShiplabelFilepath());
					sh.setPackedBy(response.getManifestBy());
					sh = ediShipmentDao.save(sh);
					
					logger.info(logPrefix+"TrackingId		: "+shipment.getTrackingId());
					logger.info(logPrefix+"Barcode			: "+shipment.getBarcode());
					logger.info(logPrefix+"CarrierName		: "+shipment.getCarrierName());
					logger.info(logPrefix+"Pickup date		: "+shipment.getReadyToPickUpTimeUTC());
					logger.info(logPrefix+"Shiplabel Path	: "+shipment.getShiplabelFilepath());
				} catch (Exception e) {
					logger.error(logPrefix+"General Exception :: update order manifest details failed...!!!", e);
				} 
			} else {
				response.setManifestErrorMessage("Manifest failed, "+ shipment.getManifestErrorMessage());
				response.setManifestStatus(SBConstant.TXN_STATUS_FAILURE);
			}
		} catch (Exception e) {
			response.setManifestErrorMessage("Manifest failed, Skip order and retry after some time.");
			response.setManifestStatus(SBConstant.TXN_STATUS_EXCEPTION);
		} 
	}

	/****************************************************
     * Calling GetPrintableManifestsForTrailer API       *
     ****************************************************/
	@Override
	public void callPrintableManifestForTrailer(ManifestResponse response, EdiShipmentHdr sh, Shipment shipment) {
		String logPrefix  = response.getRequestId()+SBConstant.LOG_SEPRATOR;
		GetPrintableManifestsForTrailerResult printableManifestForTrailer =  gtsExternalService.getPrintableManifestForTrailer(response.getRequestId(), shipment);
		 
		if (printableManifestForTrailer != null || true) {
		     ManifestDocuments manifestDocuments = printableManifestForTrailer.getManifestDocumentsList().get(0);
		     String manifestId = manifestDocuments.getManifestId();
		     logger.info(logPrefix+"ManifestId : "+manifestDocuments.getManifestId()+" of EdiOrderId : " + shipment.getEdiOrderId());
		     if (manifestId != null) {
		         logger.info(logPrefix+"ManifestId : " + manifestId);
		         response.setManifestId(manifestId);
		         
		         if(manifestId != null) {
		        	 try {
						String manifestDate = SBUtils.getTxnSysDateTime();
						 sh.setManifestDate(manifestDate);
						 sh.setManifestId(manifestId);
						 sh.setOrderStatus(SBConstant.ORDER_STATUS_MANIFESTED);
						 sh = ediShipmentDao.save(sh);
						 response.setManifestDate(manifestDate);
						 response.setManifestId(manifestId);
						 logger.info("Manifest failed, Manifest Id = " + manifestId + " updated in DB...!!!!");
					} catch (Exception e) {
						logger.error("Manifest failed, Manifest Id = " + manifestId + " update in DB failed...!!!!", e);
					}
		         }
		     } else {
		         //make_side_line order------------------------------------------------------------------------------------------------:)
		    	 response.setManifestErrorMessage("Manifest failed, "+ shipment.getManifestErrorMessage());
		    	 response.setManifestStatus(SBConstant.TXN_STATUS_FAILURE);
		     }
		 }
	}

	/****************************************************
     * Calling ManifestPackagesByIds API
     ****************************************************/
	@Override
	public void menifestPackagesById(ManifestResponse response, EdiShipmentHdr sh, Shipment shipment) {
		String logPrefix = response.getRequestId()+SBConstant.LOG_SEPRATOR;
		ManifestPackagesByIdsResult manifest = gtsExternalService.menifestPackagesByIds(response.getRequestId(), shipment);
		if (manifest != null) {
		    logger.info(logPrefix+"isCanManifest() : " + manifest.isCanManifest());
		    response.setCanManifest(manifest.isCanManifest());
		    if(manifest.isCanManifest()) {
		    	try {
					sh.setCanManifest("true");
					sh = ediShipmentDao.save(sh);
					logger.info(logPrefix+"MenifestPackagesByIds Response updated in DB...!!!");
				} catch (Exception e) {
					logger.error(logPrefix+"MenifestPackagesByIds Response update in DB failed...!!!", e);
				}
		    }
		}
	}
	
	private void printDocuments(ManifestResponse response, Shipment shipment) {
        /*************************************************************
        *     Calling Ship Label Print API                           *
        *************************************************************/
		if (shipment.getShiplabelFilepath() != null) {
			response.setShiplabelFilepath(shipment.getShiplabelFilepath());
			Properties status = docPrintService.printShipLabel(response, shipment.getShiplabelFilepath());
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
			Properties status = docPrintService.printPackingSlip(response, shipment.getInvoiceFilepath());
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
		    	Properties status = docPrintService.printGiftNoteCard(response, giftNoteCards);
		    	if(status.getProperty(SBConstant.PRINT_PROPERY_STATUS).equalsIgnoreCase(SBConstant.PRINT_STATUS_200_OK)) {
		    		response.setGiftPrintStatus(SBConstant.PRINT_STATUS_SUCCESS);
		    	} else {
		    		response.setGiftPrintStatus(SBConstant.PRINT_STATUS_FAILED);
		    	}
			} else {
		    	response.setGiftPrintStatus(SBConstant.PRINT_STATUS_NA);
		    }
		} else {
			response.setGiftPrintStatus(SBConstant.PRINT_STATUS_NA);
		}
	}
	
//    private GTSExternalSecurityService getGTSService() {
//        logger.info("GTSService Start : " + new Date());
//		String AWSAccessKey = SBUtils.getPropertyValue("AWSAccessKey");
//		String AWSSecretKey = SBUtils.getPropertyValue("AWSSecretKey");
//		String GTSEndpointURL = SBUtils.getPropertyValue("GTSEndpointURL");
//		AWSCredentials awsCredentials = new BasicAWSCredentials(AWSAccessKey, AWSSecretKey);
//		ClientConfiguration clientConfiguration = new ClientConfiguration();
//		GTSExternalSecurityService gts = new GTSExternalSecurityServiceClient(awsCredentials, clientConfiguration);
//		gts.setEndpoint(GTSEndpointURL);
//        logger.info("GTSService End : " + new Date());
//		return gts;
//    }
}
