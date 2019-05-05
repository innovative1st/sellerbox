package com.seller.box.amazon.gts.api;

import java.nio.ByteBuffer;

import com.amazonaws.services.gtsexternalsecurity.model.ManifestPackagesRequest;
import com.amazonaws.services.gtsexternalsecurity.model.ManifestPackagesResult;
import com.seller.box.amazon.gts.data.ManifestPackagesData;

public class GTSManfestPackages extends GTSService {


	public void callmanifestPackages() {
		ManifestPackagesRequest manifestPackagesRequest = ManifestPackagesData.buildManifestPackagesByIDRequest();

        ManifestPackagesResult result= gts.manifestPackages(manifestPackagesRequest);
        ByteBuffer printableDocument = result.getPrintableDocuments();
	}

}
