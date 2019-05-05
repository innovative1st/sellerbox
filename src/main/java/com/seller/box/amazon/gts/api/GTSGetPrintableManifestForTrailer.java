package com.seller.box.amazon.gts.api;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.gtsexternalsecurity.model.GetPrintableManifestsForTrailerRequest;
import com.amazonaws.services.gtsexternalsecurity.model.GetPrintableManifestsForTrailerResult;
import com.seller.box.form.Shipment;


public class GTSGetPrintableManifestForTrailer extends GTSService {
	private static final Logger logger = LogManager.getLogger(GTSGetPrintableManifestForTrailer.class);
    private GetPrintableManifestsForTrailerRequest request;
    
    public void buildGetPrintableManifestRequest(Shipment ps) {
        logger.info("buildGetPrintableManifestRequest(Shipment ps)---- START");
        logger.info("Shipment Id : " + ps.getShipmentId());
        try {
            this.request = new GetPrintableManifestsForTrailerRequest();
            request.setTrailerId(ps.getTrailorId());
            request.setLoadId(ps.getLoadId());
            request.setWarehouseId(ps.getOperatingWarehouseCode());
        } catch (Exception e) {
            logger.error("General Exception Occured, buildGetPrintableManifestRequest(Shipment ps)", e);
        }
        logger.info("GetPrintable Request>>>" + request.toString());
        logger.info("buildGetPrintableManifestRequest(Shipment ps)---- END");
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
