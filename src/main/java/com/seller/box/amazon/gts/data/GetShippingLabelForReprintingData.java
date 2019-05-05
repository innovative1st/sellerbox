package com.seller.box.amazon.gts.data;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.gtsexternalsecurity.model.Dimension;
import com.amazonaws.services.gtsexternalsecurity.model.GetShippingLabelsForReprintingRequest;
import com.amazonaws.services.gtsexternalsecurity.model.LabelDimensions;
import com.amazonaws.services.gtsexternalsecurity.model.LabelSpecification;
import com.seller.box.amazon.gts.api.GetShippingLabelForReprinting;
import com.seller.box.form.Shipment;

public class GetShippingLabelForReprintingData {
	private static final Logger logger = LogManager.getLogger(GetShippingLabelForReprinting.class);
    public static GetShippingLabelsForReprintingRequest buildReprintRequest(Shipment ps) {
        logger.info("buildReprintRequest()------------------------------- START : " + ps.getShipmentId());
        GetShippingLabelsForReprintingRequest reprintingRequest = new GetShippingLabelsForReprintingRequest();

        //clientRefShipmentId
        Map<String, String> clientRefShipmentId = getClientRefShipmentId(ps);
        reprintingRequest.setClientRefShipmentId(clientRefShipmentId);
        //clientRefPackageId
        Map<String, String> clientRefPackageId = getclientRefPackageId(ps);
        reprintingRequest.setClientRefPackageId(clientRefPackageId);
        //Amazon Barcode
        reprintingRequest.setAmazonBarcode(ps.getBarcode());
        // Operating Ware House Id
        reprintingRequest.setOperatingWarehouseId(ps.getOperatingWarehouseCode());
        // Label Specification object
        GetShippingLabelForReprintingData fetShipLabel = new GetShippingLabelForReprintingData();
        LabelSpecification labelSpecification = fetShipLabel.createLabelSpecification(ps);
        reprintingRequest.setLabelSpecification(labelSpecification);

        logger.info("buildReprintRequest()------------------------------- END : " + ps.getShipmentId());
        return reprintingRequest;
    }

    //clientRefShipmentId
    private static Map<String, String> getClientRefShipmentId(Shipment ps) {
        logger.info("getClientRefShipmentId()------------------------------- START : " + ps.getShipmentId());
        Map<String, String> clientRefShipmentId = new HashMap<String, String>();
        clientRefShipmentId.put("SHIPMENT_ID", ps.getShipmentId());
        logger.info("getClientRefShipmentId()------------------------------- END : " + ps.getShipmentId());
        return clientRefShipmentId;
    }

    //clientRefPackageId
    private static Map<String, String> getclientRefPackageId(Shipment ps) {
        logger.info("getclientRefPackageId()------------------------------- START : " + ps.getShipmentId());
        Map<String, String> clientRefPackageId = new HashMap<String, String>();
        clientRefPackageId.put("PACKAGE_ID", ps.getPackageId());
        logger.info("getclientRefPackageId()------------------------------- END : " + ps.getShipmentId());
        return clientRefPackageId;
    }

    //Label Info
    private LabelSpecification createLabelSpecification(Shipment ps) {
        logger.info("createLabelSpecification()------------------------------- START : " + ps.getShipmentId());
        LabelSpecification labelSpecification = new LabelSpecification();
        labelSpecification.setFormatType(ps.getLabelFormatType());
        LabelDimensions labelDimensions = new LabelDimensions();

        Dimension length = new Dimension();
        length.setDimensionUnit(ps.getLabelLengthDimensionUnit());
        length.setDimensionValue(ps.getLabelLengthDimensionValue());

        Dimension width = new Dimension();
        width.setDimensionUnit(ps.getLabelWidthDimensionUnit());
        width.setDimensionValue(ps.getLabelWidthDimensionValue());

        labelDimensions.setLength(length);
        labelDimensions.setWidth(width);
        labelSpecification.setLabelDimensions(labelDimensions);
        logger.info("createLabelSpecification()------------------------------- END : " + ps.getShipmentId());
        return labelSpecification;
    }

    /*
        private static LabelSpecification createLabelSpecification() {
        LabelSpecification labelSpecification = new LabelSpecification();
        labelSpecification.setFormatType("ZPL");
        LabelDimensions labelDimensions = new LabelDimensions();
        Dimension length = new Dimension();
        length.setDimensionUnit("IN");
        length.setDimensionValue(6.0);
        Dimension width = new Dimension();
        width.setDimensionUnit("IN");
        width.setDimensionValue(4.0);
        labelDimensions.setLength(length);
        labelDimensions.setWidth(width);
        labelSpecification.setLabelDimensions(labelDimensions);
        return labelSpecification;
    }
*/
}