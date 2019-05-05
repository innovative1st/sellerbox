package com.seller.box.amazon.gts.data;

import java.util.HashMap;
import java.util.Map;

public class ShipmentIdentifiers {
    public static String AmazonBarcode;

    public static Map<String, String> getClientRefShipmentId(String shipmentId) {
        Map<String, String> clientRefShipmentId = new HashMap<String, String>();
        clientRefShipmentId.put("SHIPMENT_ID", shipmentId);
        return clientRefShipmentId;
    }

    public static void setAmazonBarcode(String amazonBarcode) {
        AmazonBarcode = amazonBarcode;
    }

    public static String getAmazonBarcode() {
        return AmazonBarcode;
    }

    public static Map<String, String> getClientRefPackageIdId(String packageId) {
        Map<String, String> clientRefPackageId = new HashMap<String, String>();
        clientRefPackageId.put("PACKAGE_ID", packageId);
        return clientRefPackageId;
    }

    public static Map<String, Map<String, String>> getClientRefShipmentPackageIdId(String shipmentId, String packageId) {
        Map<String, Map<String, String>> clientRefShipmentPackageId = new HashMap<String, Map<String, String>>();
        clientRefShipmentPackageId.put("clientRefShipmentId", getClientRefShipmentId(shipmentId));
        clientRefShipmentPackageId.put("clientRefPackageId", getClientRefPackageIdId(packageId));
        return clientRefShipmentPackageId;
    }
}
