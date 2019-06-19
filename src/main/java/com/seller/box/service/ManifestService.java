package com.seller.box.service;

import com.seller.box.core.ManifestResponse;
import com.seller.box.entities.EdiShipmentHdr;
import com.seller.box.form.Shipment;

public interface ManifestService {
	public void manifestOrder(ManifestResponse response, EdiShipmentHdr sh, Shipment shipment);
	public void preparePackageForShipping(ManifestResponse response, EdiShipmentHdr sh, Shipment shipment);
	public void callPrintableManifestForTrailer(ManifestResponse response, EdiShipmentHdr sh, Shipment shipment);
	public void menifestPackagesById(ManifestResponse response, EdiShipmentHdr sh, Shipment shipment);
}
