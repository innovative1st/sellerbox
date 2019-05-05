package com.seller.box.amazon.gts.api;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.gtsexternalsecurity.model.ManifestPackagesByIdsRequest;
import com.amazonaws.services.gtsexternalsecurity.model.ManifestPackagesByIdsResult;
import com.seller.box.amazon.gts.data.ManifestPackageByIdsData;
import com.seller.box.form.Shipment;


public class GTSManifestPackagesByIds extends GTSService {
	private static final Logger logger = LogManager.getLogger(GTSManifestPackagesByIds.class);
    private ManifestPackagesByIdsRequest request;
    public void buildManifestPackageByIdsReq(Shipment ps) {
        this.request = ManifestPackageByIdsData.buildManifestPackagesByIDRequest(ps);
        logger.info("ManifestPackagesByIds Request>>>" + request.toString());
    }

    public ManifestPackagesByIdsResult callmanifestPackageByIds() {
        logger.info("callmanifestPackageByIds()-------- START");
        ManifestPackagesByIdsResult result = null;
        try {
            logger.info("ManifestPackagesByIds Request>>>>" + request.toString());
            result = gts.manifestPackagesByIds(this.request);
            logger.info("ManifestPackagesByIds Response>>>" + result.toString());
        } catch (AmazonServiceException ase) {
            logger.error("AmazonServiceException Occured, callmanifestPackageByIds()", ase);
        } catch (AmazonClientException ace) {
            logger.error("AmazonClientException Occured, callmanifestPackageByIds()", ace);
        } catch (Exception e) {
            logger.error("General Exception Occured, callmanifestPackageByIds()", e);
        }
        logger.info("callmanifestPackageByIds()-------- END");
        return result;
    }
}
