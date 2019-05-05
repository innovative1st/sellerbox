package com.seller.box.amazon.gts.api;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.gtsexternalsecurity.model.GetLabelDataRequest;
import com.amazonaws.services.gtsexternalsecurity.model.GetLabelDataResult;
import com.seller.box.amazon.gts.config.PackageIdentifier;


public class GTSGetLabelData extends GTSService {

    public GTSGetLabelData() {
        super();
    }


    public GetLabelDataResult callgetGTSLabelData(String ps) {
        Map<String, String> pkgIdentifierMap = new HashMap<String, String>();
        pkgIdentifierMap.put(PackageIdentifier.SCANNABLE_ID.name(), ps);

        GetLabelDataRequest getLabelDataRequest = new GetLabelDataRequest();
        getLabelDataRequest.setPkgIdentifierMap(pkgIdentifierMap);
        GetLabelDataResult getLabelDataResult = gts.getLabelData(getLabelDataRequest);
        return getLabelDataResult;
    }
    
    public static void main(String[] a){
        GTSGetLabelData obj = new GTSGetLabelData();
        GetLabelDataResult res = obj.callgetGTSLabelData("DbV7gLNJN");
        System.out.println(res.getLabelData().getAmazonBarcode());
    }
}
