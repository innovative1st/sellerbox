package com.seller.box.amazon.gts.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.gtsexternalsecurity.model.ClientRefShipmentPackageId;
import com.amazonaws.services.gtsexternalsecurity.model.ManifestPackagesByIdsRequest;
import com.seller.box.form.Shipment;
import com.seller.box.utils.SBConstant;



public class ManifestPackageByIdsData {
	private static final Logger logger = LogManager.getLogger(ManifestPackageByIdsData.class);
    public static ManifestPackagesByIdsRequest buildManifestPackagesByIDRequest(String requestId, Shipment ps) {
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"buildManifestPackagesByIDRequest(PackageForShipping ps)--------------START");
        ManifestPackagesByIdsRequest manifestPackagesByIdsRequest = null;
        try {
            manifestPackagesByIdsRequest = new ManifestPackagesByIdsRequest();
            List<ClientRefShipmentPackageId> request = buildClientRefShipmentPackageIdList(requestId, ps.getPackageId(), ps.getShipmentId());
            logger.info(requestId+SBConstant.LOG_SEPRATOR+"ClientRefShipmentPackageId Request>>>" + request.toString());
            manifestPackagesByIdsRequest.setClientRefShipmentPackageIdList(request);
            manifestPackagesByIdsRequest.setLoadId(ps.getLoadId());
            manifestPackagesByIdsRequest.setOperatingWarehouseId(ps.getOperatingWarehouseCode());
            manifestPackagesByIdsRequest.setTrailerId(ps.getTrailorId());
            manifestPackagesByIdsRequest.setAmazonBarcodes(buildAmazonBarcodeList());
            logger.info(requestId+SBConstant.LOG_SEPRATOR+"manifestPackagesByIdsRequest>>>" + manifestPackagesByIdsRequest.toString());
        } catch (Exception e) {
            logger.error(requestId+SBConstant.LOG_SEPRATOR+"General Exception Occured, buildManifestPackagesByIDRequest(PackageForShipping ps)", e);
        }
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"buildManifestPackagesByIDRequest(PackageForShipping ps)--------------END");
        return manifestPackagesByIdsRequest;
    }
    public static List<String> buildAmazonBarcodeList() {
        List<String> amazonBarcodeList=new ArrayList<String>();
        amazonBarcodeList.add(ShipmentIdentifiers.getAmazonBarcode());
        return amazonBarcodeList;
    }
    private static List<ClientRefShipmentPackageId> buildClientRefShipmentPackageIdList(String requestId, String packageId, String shipmentId) {
        List<ClientRefShipmentPackageId> clientRefShipmentPackageIdList = null;
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"buildClientRefShipmentPackageIdList(String packageId, String shipmentId)--------------START");
        try {
            clientRefShipmentPackageIdList = new ArrayList<ClientRefShipmentPackageId>();
            //Need to change according to packages that are manifested
            Map<String, String> clientRefPackageId = ShipmentIdentifiers.getClientRefPackageIdId(packageId);

            Map<String, String> clientRefShipmentId = ShipmentIdentifiers.getClientRefShipmentId(shipmentId);
            
            ClientRefShipmentPackageId clientRefShipmentPackageId = new ClientRefShipmentPackageId();
            clientRefShipmentPackageId.setClientRefPackageId(clientRefPackageId);
            clientRefShipmentPackageId.setClientRefShipmentId(clientRefShipmentId);
            clientRefShipmentPackageIdList.add(clientRefShipmentPackageId);
            logger.info(requestId+SBConstant.LOG_SEPRATOR+"clientRefShipmentPackageIdRequest>>>" + clientRefShipmentPackageId.toString());
        } catch (Exception e) {
            logger.error(requestId+SBConstant.LOG_SEPRATOR+"General Exception Occured, buildClientRefShipmentPackageIdList(String packageId, String shipmentId)", e);
        }
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"buildClientRefShipmentPackageIdList(String packageId, String shipmentId)--------------END");
        return clientRefShipmentPackageIdList;
    }
}