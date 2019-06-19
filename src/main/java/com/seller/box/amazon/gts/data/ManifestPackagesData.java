package com.seller.box.amazon.gts.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.gtsexternalsecurity.model.ClientRefShipmentPackageId;
import com.amazonaws.services.gtsexternalsecurity.model.ManifestPackagesRequest;
import com.amazonaws.services.gtsexternalsecurity.model.PrintableDocumentsSpecification;

public class ManifestPackagesData {
	
	public static ManifestPackagesRequest buildManifestPackagesByIDRequest() {
		ManifestPackagesData sampleData = new ManifestPackagesData();
		
		ManifestPackagesRequest manifestPackagesRequest=new ManifestPackagesRequest();
        List<String> carrierList;
        carrierList = Arrays.asList("UPS");
        manifestPackagesRequest.setCarrierList(carrierList);

        manifestPackagesRequest.setOperatingWarehouseId("MSN1");

        PrintableDocumentsSpecification printableDocumentsSpecification = sampleData.createTestPrintableDocumentsSpecification();
        manifestPackagesRequest.setPrintableDocumentsSpecification(printableDocumentsSpecification);
        List<ClientRefShipmentPackageId> clientRefShipmentPackageIdList = buildClientRefShipmentPackageIdList();
        
        manifestPackagesRequest.setClientRefShipmentPackageIdList(clientRefShipmentPackageIdList);
        return manifestPackagesRequest;
	}
	
	private static List<ClientRefShipmentPackageId> buildClientRefShipmentPackageIdList() {
		List<ClientRefShipmentPackageId> clientRefShipmentPackageIdList = new ArrayList<ClientRefShipmentPackageId>();
		
		//Need to change according to packages that are manifested
		Map<String, String> clientRefPackageId = new HashMap<String, String>();
		clientRefPackageId.put("PACKAGE_ID", "1");
		Map<String, String> clientRefShipmentId = new HashMap<String, String>();
		clientRefShipmentId.put("SHIPMENT_ID", "123456789");
		
		
		ClientRefShipmentPackageId clientRefShipmentPackageId = new ClientRefShipmentPackageId();
		clientRefShipmentPackageId.setClientRefPackageId(clientRefPackageId);
		clientRefShipmentPackageId.setClientRefShipmentId(clientRefShipmentId);
		clientRefShipmentPackageIdList.add(clientRefShipmentPackageId);
		return clientRefShipmentPackageIdList;
	}
	
	private PrintableDocumentsSpecification createTestPrintableDocumentsSpecification() {
        PrintableDocumentsSpecification specification=new PrintableDocumentsSpecification();
        specification.setFormatType("PNG");
        return specification;
    }

}