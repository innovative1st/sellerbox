package com.seller.box.amazon.gts.data;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.gtsexternalsecurity.model.CancelPackageShippingRequest;
import com.seller.box.amazon.gts.api.GTSCancelPackageShipping;
import com.seller.box.form.Shipment;


public class PrepareCancelPackageShippingData {
	private static final Logger logger = LogManager.getLogger(GTSCancelPackageShipping.class);
    public static CancelPackageShippingRequest buildCancelPackageForShippingRequest(Shipment ps) {
    	logger.info("buildCancelPackageForShippingRequest(Shipment ps)-----------START : " + ps.getShipmentId());
    	CancelPackageShippingRequest request = null;
		try {
			request = new CancelPackageShippingRequest ();
			request.setClientRefShipmentId(ShipmentIdentifiers.getClientRefShipmentId(ps.getShipmentId()));
			request.setClientRefPackageId(ShipmentIdentifiers.getClientRefPackageIdId(ps.getPackageId()));
			request.setAmazonBarcode(ShipmentIdentifiers.getAmazonBarcode());
			request.setOperatingWarehouseId(ps.getOperatingWarehouseCode());
		} catch (Exception e) {
			logger.error("Exception :: buildCancelPackageForShippingRequest(Shipment ps)", e);
		}
		logger.info("buildCancelPackageForShippingRequest(Shipment ps)-----------END : " + ps.getShipmentId());
        return request;
    }

}
