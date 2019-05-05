package com.seller.box.amazon.gts.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.gtsexternalsecurity.model.ClientRefShipmentPackageId;
import com.amazonaws.services.gtsexternalsecurity.model.ManifestPackagesByIdsRequest;
import com.seller.box.form.Shipment;



public class ManifestPackageByIdsData {
	private static final Logger logger = LogManager.getLogger(ManifestPackageByIdsData.class);
    public static ManifestPackagesByIdsRequest buildManifestPackagesByIDRequest(Shipment ps) {
        logger.info("buildManifestPackagesByIDRequest(PackageForShipping ps)--------------START");
        ManifestPackagesByIdsRequest manifestPackagesByIdsRequest = null;
        try {
            manifestPackagesByIdsRequest = new ManifestPackagesByIdsRequest();
            List<ClientRefShipmentPackageId> request = buildClientRefShipmentPackageIdList(ps.getPackageId(), ps.getShipmentId());
            logger.info("ClientRefShipmentPackageId Request>>>" + request.toString());
            manifestPackagesByIdsRequest.setClientRefShipmentPackageIdList(request);
            manifestPackagesByIdsRequest.setLoadId(ps.getLoadId());
            manifestPackagesByIdsRequest.setOperatingWarehouseId(ps.getOperatingWarehouseCode());
            manifestPackagesByIdsRequest.setTrailerId(ps.getTrailorId());
            manifestPackagesByIdsRequest.setAmazonBarcodes(buildAmazonBarcodeList());
            logger.info("manifestPackagesByIdsRequest>>>" + manifestPackagesByIdsRequest.toString());
        } catch (Exception e) {
            logger.error("General Exception Occured, buildManifestPackagesByIDRequest(PackageForShipping ps)", e);
        }
        logger.info("buildManifestPackagesByIDRequest(PackageForShipping ps)--------------END");
        return manifestPackagesByIdsRequest;
    }
    public static List<String> buildAmazonBarcodeList() {
        List<String> amazonBarcodeList=new ArrayList<String>();
        amazonBarcodeList.add(ShipmentIdentifiers.getAmazonBarcode());
        return amazonBarcodeList;
    }
    private static List<ClientRefShipmentPackageId> buildClientRefShipmentPackageIdList(String packageId, String shipmentId) {
        List<ClientRefShipmentPackageId> clientRefShipmentPackageIdList = null;
        logger.info("buildClientRefShipmentPackageIdList(String packageId, String shipmentId)--------------START");
        try {
            clientRefShipmentPackageIdList = new ArrayList<ClientRefShipmentPackageId>();
            //Need to change according to packages that are manifested
            Map<String, String> clientRefPackageId = ShipmentIdentifiers.getClientRefPackageIdId(packageId);

            Map<String, String> clientRefShipmentId = ShipmentIdentifiers.getClientRefShipmentId(shipmentId);
            
            ClientRefShipmentPackageId clientRefShipmentPackageId = new ClientRefShipmentPackageId();
            clientRefShipmentPackageId.setClientRefPackageId(clientRefPackageId);
            clientRefShipmentPackageId.setClientRefShipmentId(clientRefShipmentId);
            clientRefShipmentPackageIdList.add(clientRefShipmentPackageId);
            logger.info("clientRefShipmentPackageIdRequest>>>" + clientRefShipmentPackageId.toString());
        } catch (Exception e) {
            logger.error("General Exception Occured, buildClientRefShipmentPackageIdList(String packageId, String shipmentId)", e);
        }
        logger.info("buildClientRefShipmentPackageIdList(String packageId, String shipmentId)--------------END");
        return clientRefShipmentPackageIdList;
    }
}