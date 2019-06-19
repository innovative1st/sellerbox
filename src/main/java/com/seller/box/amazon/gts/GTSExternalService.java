package com.seller.box.amazon.gts;

import com.amazonaws.services.gtsexternalsecurity.model.CancelPackageShippingResult;
import com.amazonaws.services.gtsexternalsecurity.model.GetDocumentForContainerResult;
import com.amazonaws.services.gtsexternalsecurity.model.GetPrintableManifestsForTrailerResult;
import com.amazonaws.services.gtsexternalsecurity.model.GetShippingLabelsForReprintingResult;
import com.amazonaws.services.gtsexternalsecurity.model.ManifestPackagesByIdsResult;
import com.amazonaws.services.gtsexternalsecurity.model.PreparePackageForShippingResult;
import com.seller.box.form.Shipment;

public interface GTSExternalService {

	/****************************************************
	* Calling PreparePackageForShipping API
	****************************************************/
	PreparePackageForShippingResult getPreparePackageForShipping(String requestId, Shipment sh);

	/****************************************************
	 * Calling GetShippingLabelsForReprinting API
	 ****************************************************/
	GetShippingLabelsForReprintingResult getShippingLabelForReprinting(Shipment sh);

	/****************************************************
	 * Calling GetDocumentForContainer API
	 ****************************************************/
	GetDocumentForContainerResult getDocumentForContainerResult(Shipment sh);

	/****************************************************
	* Calling ManifestPackagesByIds API
	****************************************************/
	ManifestPackagesByIdsResult menifestPackagesByIds(String requestId, Shipment sh);

	/****************************************************
	* Calling CancelPackageShipping API
	****************************************************/
	CancelPackageShippingResult gtsCancelPackageShipping(Shipment sh);

	/****************************************************
	* Calling GetPrintableManifestsForTrailer API
	****************************************************/
	GetPrintableManifestsForTrailerResult getPrintableManifestForTrailer(String requestId, Shipment sh);

}