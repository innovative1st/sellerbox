package com.seller.box.amazon.gts;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.gtsexternalsecurity.model.CancelPackageShippingResult;
import com.amazonaws.services.gtsexternalsecurity.model.DuplicateTrailerException;
import com.amazonaws.services.gtsexternalsecurity.model.GetDocumentForContainerResult;
import com.amazonaws.services.gtsexternalsecurity.model.GetPrintableManifestsForTrailerResult;
import com.amazonaws.services.gtsexternalsecurity.model.GetShippingLabelsForReprintingResult;
import com.amazonaws.services.gtsexternalsecurity.model.InvalidRequestException;
import com.amazonaws.services.gtsexternalsecurity.model.ManifestDocuments;
import com.amazonaws.services.gtsexternalsecurity.model.ManifestPackagesByIdsResult;
import com.amazonaws.services.gtsexternalsecurity.model.PackagesAlreadyManifestedException;
import com.amazonaws.services.gtsexternalsecurity.model.PreparePackageForShippingResult;
import com.amazonaws.services.gtsexternalsecurity.model.RecoverableException;
import com.amazonaws.services.gtsexternalsecurity.model.UnrecoverableException;
import com.seller.box.amazon.gts.api.GTSCancelPackageShipping;
import com.seller.box.amazon.gts.api.GTSGetDocumentForContainer;
import com.seller.box.amazon.gts.api.GTSGetPrintableManifestForTrailer;
import com.seller.box.amazon.gts.api.GTSManifestPackagesByIds;
import com.seller.box.amazon.gts.api.GTSPreparePackageForShipping;
import com.seller.box.amazon.gts.api.GetShippingLabelForReprinting;
import com.seller.box.form.Shipment;
import com.seller.box.utils.SBUtils;


public class GTSExternalService {
	private static final Logger logger = LogManager.getLogger(GTSExternalService.class);
    int retryCount = Integer.parseInt(SBUtils.getPropertyValue("menifest.retry.count"));
    /****************************************************
    * Calling PreparePackageForShipping API
    ****************************************************/
    public PreparePackageForShippingResult getPreparePackageForShipping(Shipment ps){
        logger.info("getPreparePackageForShipping(PackageForShipping ps) for EDI_ORDER_ID = "+ ps.getEdiOrderId()+" ------------ START");
        PreparePackageForShippingResult result = null;
        for (int i = 0; i < retryCount; i++) {
            try {
                logger.info("Shipment Id : " + ps.getShipmentId());
                GTSPreparePackageForShipping gtsLabel = new GTSPreparePackageForShipping();
                gtsLabel.buildLabelRequest(ps);
                result = gtsLabel.callPreparePackageForShipping(ps);
                if(result != null){
                    logger.info("PreparePackageForShippingResult >>>>>>>>>>> " + result.toString());
                    break;
                }
            } catch (Exception e) {
                logger.error("Exception Occured, getPreparePackageForShipping(PackageForShipping ps) for EDI_ORDER_ID = "+ ps.getEdiOrderId(), e);
            }
        }
        logger.info("getPreparePackageForShipping(PackageForShipping ps) for EDI_ORDER_ID = "+ ps.getEdiOrderId()+" ------------ END");
        return result;
    }
    
    /****************************************************
     * Calling GetShippingLabelsForReprinting API
     ****************************************************/
    public GetShippingLabelsForReprintingResult getShippingLabelForReprinting(Shipment ps){
        GetShippingLabelsForReprintingResult result = null;
        
        for (int i = 0; i < retryCount; i++) {
            try {
                GetShippingLabelForReprinting gtsReprintLabel = new GetShippingLabelForReprinting();
                gtsReprintLabel.buildReprintLabelRequest(ps);
                result = gtsReprintLabel.callGTSReprintLabel();
                if (result != null) {
                    if (result.getLabels() != null) {
                        if (result.getLabels().size() > 0) {
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("Exception Occured, getShippingLabelForReprinting(PackageForShipping ps) ", e);
            }
        }
        return result;
    }
    
    /****************************************************
     * Calling GetDocumentForContainer API
     ****************************************************/
    public GetDocumentForContainerResult getDocumentForContainerResult(Shipment ps){
        GetDocumentForContainerResult containerRequest =  null;
        try {
            GTSGetDocumentForContainer gtsDocument = new GTSGetDocumentForContainer();
            gtsDocument.buildGetDocumentForContainerReq(ps);
            containerRequest = gtsDocument.callgetDocumentForContainer();
        } catch (Exception e) {
            logger.error("Exception Occured, getDocumentForContainerResult(PackageForShipping ps) ", e);
        }
        return containerRequest;
    }
    
    /****************************************************
    * Calling ManifestPackagesByIds API
    ****************************************************/
    public ManifestPackagesByIdsResult menifestPackagesByIds(Shipment ps){
        logger.info("menifestPackagesByIds(PackageForShipping ps) for EDI_ORDER_ID = "+ ps.getEdiOrderId()+" ------------ START");
        ManifestPackagesByIdsResult result  = null;
        for (int i = 0; i < retryCount; i++) {
            try {
                if(ps.getManifestExceptionMessage() == null || ps.getManifestExceptionMessage().equalsIgnoreCase("RecoverableException")) {
                    GTSManifestPackagesByIds gtsManifestPackages = new GTSManifestPackagesByIds();
                    gtsManifestPackages.buildManifestPackageByIdsReq(ps);
                    result = gtsManifestPackages.callmanifestPackageByIds();
                    if (result != null) {
                        if (result.isCanManifest()) {
                            ps.setManifestExceptionMessage(null);
                            break;
                        }
                    }
                }
            } catch (RecoverableException e) {
                ps.setManifestExceptionMessage("RecoverableException");
                logger.error("RecoverableException Occured, menifestPackagesByIds(PackageForShipping ps) ", e);
            } catch (UnrecoverableException e) {
                ps.setManifestExceptionMessage("UnrecoverableException");
                logger.error("UnrecoverableException Occured, menifestPackagesByIds(PackageForShipping ps) ", e);
            } catch (InvalidRequestException e) {
                ps.setManifestExceptionMessage("InvalidRequestException");
                logger.error("InvalidRequestException Occured, menifestPackagesByIds(PackageForShipping ps) ", e);
            } catch (DuplicateTrailerException e) {
                ps.setManifestExceptionMessage("DuplicateTrailerException");
                logger.error("DuplicateTrailerException Occured, menifestPackagesByIds(PackageForShipping ps) ", e);
            } catch (PackagesAlreadyManifestedException e) {
                ps.setManifestExceptionMessage("PackagesAlreadyManifestedException");
                logger.error("PackagesAlreadyManifestedException Occured, menifestPackagesByIds(PackageForShipping ps) ", e);
            } catch (Exception e) {
                ps.setManifestExceptionMessage("GeneralException");
                logger.error("General Exception Occured, menifestPackagesByIds(PackageForShipping ps) ", e);
            }
        }
        //TODO ADFUtils.putValueInPageflow("PackageForShipping", ps);
        logger.info("menifestPackagesByIds(PackageForShipping ps) for EDI_ORDER_ID = "+ ps.getEdiOrderId()+" ------------ START");
        return result;
    }
    
    /****************************************************
    * Calling CancelPackageShipping API
    ****************************************************/
    public CancelPackageShippingResult gtsCancelPackageShipping(Shipment ps){
        CancelPackageShippingResult result = null;
        try {
            GTSCancelPackageShipping gtsCancelShipping = new GTSCancelPackageShipping();
            gtsCancelShipping.buildLabelCancelRequest(ps);
            result = gtsCancelShipping.callCancelPackageForShipping();
        } catch (Exception e) {
            logger.error("Exception Occured, gtsCancelPackageShipping(PackageForShipping ps) ", e);
        }
        return result;
    }
    
    /****************************************************
    * Calling GetPrintableManifestsForTrailer API
    ****************************************************/
    public GetPrintableManifestsForTrailerResult getPrintableManifestForTrailer(Shipment ps){
        GetPrintableManifestsForTrailerResult result = null;
        for (int i = 0; i < retryCount; i++) {
            try {
                GTSGetPrintableManifestForTrailer gtsGetPrintable = new GTSGetPrintableManifestForTrailer();
                gtsGetPrintable.buildGetPrintableManifestRequest(ps);
                
                result = gtsGetPrintable.callgetPrintableManifestForTrailer();
                if(result != null) {
                    ManifestDocuments manifestDocuments = result.getManifestDocumentsList().get(0);
                    //PrintableManifest printableManifest = result.getManifestDocumentsList().get(0).getPrintableManifestList().get(0);
                    logger.info("ManifestId : " + result.getManifestDocumentsList().get(0).getManifestId());
                    if (manifestDocuments != null) {
                        if (manifestDocuments.getManifestId() != null) {
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("Exception Occured, getPrintableManifestForTrailer(PackageForShipping ps) ", e);
            }
        }
        return result;
    }
    
    
    public static void main(String[] ar){
        GTSExternalService gtsExtService = new GTSExternalService();
        Shipment ps = new Shipment();
        ps.setPackageId("1");
        ps.setEdiOrderId(2L);
        ps.setShipmentId("DbYsJNSkN");
        ps.setLoadId("Repro-L0000-0000000002");
        ps.setOriginWarehouseCode("QNEV");
        ps.setTrailorId("Repro-T0000-0000000002");
        ps.setBarcode("22-DbYsJNSkN_001");
        ps.setTrackingId("237364516124");
        try {
            ManifestPackagesByIdsResult result = gtsExtService.menifestPackagesByIds(ps);
            System.out.println(result.isCanManifest());
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
    }
}

