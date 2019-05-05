package com.seller.box.amazon.gts.api;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.gtsexternalsecurity.model.CancelPackageShippingRequest;
import com.amazonaws.services.gtsexternalsecurity.model.CancelPackageShippingResult;
import com.seller.box.amazon.gts.data.PrepareCancelPackageShippingData;
import com.seller.box.form.Shipment;



public class GTSCancelPackageShipping extends GTSService {
	private static final Logger logger = LogManager.getLogger(GTSCancelPackageShipping.class);
    private CancelPackageShippingRequest request;

    public void buildLabelCancelRequest(Shipment ps) {
        this.request = PrepareCancelPackageShippingData.buildCancelPackageForShippingRequest(ps);
        logger.info("CancelShippingLabel Request>>>" + request.toString());
    }

    public CancelPackageShippingResult callCancelPackageForShipping() {
        CancelPackageShippingResult result = gts.cancelPackageShipping(this.request);
        logger.info("CancelShippingLabel result>>>" + result.toString());
        return result;
    }

}
