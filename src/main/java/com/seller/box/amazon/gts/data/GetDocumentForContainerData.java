package com.seller.box.amazon.gts.data;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.gtsexternalsecurity.model.ContainerDocumentType;
import com.amazonaws.services.gtsexternalsecurity.model.DocumentFormat;
import com.amazonaws.services.gtsexternalsecurity.model.GetDocumentForContainerRequest;
import com.seller.box.amazon.gts.GTSExternalServiceImpl;
import com.seller.box.form.Shipment;


public class GetDocumentForContainerData {
	private static final Logger logger = LogManager.getLogger(GTSExternalServiceImpl.class);   
    public GetDocumentForContainerData() {
        super();
    }
    
    public static GetDocumentForContainerRequest buildRequest(Shipment ps) {
        logger.info("buildRequest()------------------------------- START : "+ps.getShipmentId());        
        GetDocumentForContainerRequest request = new GetDocumentForContainerRequest();
        //clientRefShipmentId
        Map<String, String> clientRefShipmentId = getClientRefShipmentId(ps);
        request.setClientRefShipmentId(clientRefShipmentId);
        //clientRefPackageId
        Map<String, String> clientRefPackageId = getclientRefPackageId(ps);
        request.setClientRefPackageId(clientRefPackageId);
        //Amazon Barcode
        request.setAmazonBarcode(ps.getBarcode());
        //Document Format
        request.setDocumentFormat(DocumentFormat.PDF);
        //Document Type
        request.setDocumentType(ContainerDocumentType.PRINTABLE_COMMERCIAL_INVOICE);
        //Node Name
        request.setNodeName("XDEI");
        logger.info("buildRequest()------------------------------- END : "+ps.getShipmentId());             
        return request;
//            request.setClientRefShipmentId(ShipmentIdentifiers.getClientRefShipmentId());
//            request.setClientRefPackageId(ShipmentIdentifiers.getClientRefPackageIdId());
//            request.setDocumentFormat(DocumentFormat.PDF);
//            request.setAmazonBarcode(ShipmentIdentifiers.getAmazonBarcode());
//            request.setDocumentType(ContainerDocumentType.PRINTABLE_COMMERCIAL_INVOICE);
//            request.setNodeName("XDEI");
        
    }
    
    //clientRefPackageId
    private static Map<String, String> getclientRefPackageId(Shipment ps) {
        logger.info("getclientRefPackageId()------------------------------- START : "+ps.getShipmentId());
        Map<String, String> clientRefPackageId = new HashMap<String, String>();
        clientRefPackageId.put("PACKAGE_ID", ps.getPackageId());
        logger.info("getclientRefPackageId()------------------------------- END : "+ps.getShipmentId());
        return clientRefPackageId;
    }
    
    //clientRefShipmentId
    private static Map<String, String> getClientRefShipmentId(Shipment ps) {
        logger.info("getClientRefShipmentId()------------------------------- START : "+ps.getShipmentId());
        Map<String, String> clientRefShipmentId = new HashMap<String, String>();
        clientRefShipmentId.put("SHIPMENT_ID", ps.getShipmentId());
        logger.info("getClientRefShipmentId()------------------------------- END : "+ps.getShipmentId());
        return clientRefShipmentId;
    }
}
