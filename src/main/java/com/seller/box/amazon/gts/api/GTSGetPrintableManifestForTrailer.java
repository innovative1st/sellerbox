package com.seller.box.amazon.gts.api;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.gtsexternalsecurity.model.GetPrintableManifestsForTrailerRequest;
import com.amazonaws.services.gtsexternalsecurity.model.GetPrintableManifestsForTrailerResult;
import com.seller.box.form.Shipment;
import com.seller.box.utils.SBConstant;


public class GTSGetPrintableManifestForTrailer extends GTSService {
	private static final Logger logger = LogManager.getLogger(GTSGetPrintableManifestForTrailer.class);
    private GetPrintableManifestsForTrailerRequest request;
    
    public void buildGetPrintableManifestRequest(String requestId, Shipment sh) {
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"buildGetPrintableManifestRequest(Shipment sh)---- START");
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"Shipment Id : " + sh.getShipmentId());
        try {
            this.request = new GetPrintableManifestsForTrailerRequest();
            request.setTrailerId(sh.getTrailorId());
            request.setLoadId(sh.getLoadId());
            request.setWarehouseId(sh.getOperatingWarehouseCode());
        } catch (Exception e) {
            logger.error(requestId+SBConstant.LOG_SEPRATOR+"General Exception Occured, buildGetPrintableManifestRequest(Shipment sh)", e);
        }
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"GetPrintable Request>>>" + request.toString());
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"buildGetPrintableManifestRequest(Shipment sh)---- END");
    }
    public GetPrintableManifestsForTrailerResult callgetPrintableManifestForTrailer() {
        GetPrintableManifestsForTrailerResult result = null;
        logger.info("callgetPrintableManifestForTrailer()---- START");
        try {
            result = gts.getPrintableManifestsForTrailer(request);
            logger.info("Get Printable Response>>>" + result.toString());
        } catch (AmazonServiceException ase) {
            logger.error("AmazonServiceException Occured, callgetPrintableManifestForTrailer()", ase);
        } catch (AmazonClientException ace) {
            logger.error("AmazonClientException Occured, callgetPrintableManifestForTrailer()", ace);
        } catch (Exception e) {
            logger.error("General Exception Occured, callgetPrintableManifestForTrailer()", e);
        }
        logger.info("callgetPrintableManifestForTrailer()---- END");
        return result;
    }

}
