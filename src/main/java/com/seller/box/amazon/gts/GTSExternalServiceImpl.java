package com.seller.box.amazon.gts;


import java.util.Date;

import org.apache.log4j.Logger;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.gtsexternalsecurity.GTSExternalSecurityService;
import com.amazonaws.services.gtsexternalsecurity.GTSExternalSecurityServiceClient;
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
import com.seller.box.utils.SBConstant;
import com.seller.box.utils.SBUtils;

@Service
public class GTSExternalServiceImpl implements GTSExternalService {
	//private static final Logger logger = LogManager.getLogger(GTSExternalServiceImpl.class);
	private static final Logger logger = Logger.getLogger(GTSExternalServiceImpl.class);
	int retryCount = Integer.parseInt(SBUtils.getPropertyValue("menifest.retry.count"));
    /* (non-Javadoc)
	 * @see com.seller.box.amazon.gts.GTSExternalService#getPreparePackageForShipping(java.lang.String, com.seller.box.form.Shipment)
	 */
    @Override
	public PreparePackageForShippingResult getPreparePackageForShipping(String requestId, Shipment sh){
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"getPreparePackageForShipping(...) for EDI_ORDER_ID = "+ sh.getEdiOrderId()+" ------------ START");
        PreparePackageForShippingResult result = null;
        for (int i = 0; i < retryCount; i++) {
            try {
            	getGTSService();
                logger.info(requestId+SBConstant.LOG_SEPRATOR+"Shipment Id : " + sh.getShipmentId());
                GTSPreparePackageForShipping gtsLabel = new GTSPreparePackageForShipping();
                gtsLabel.buildLabelRequest(requestId, sh);
                result = gtsLabel.callPreparePackageForShipping(requestId, sh);
                if(result != null){
                    logger.info(requestId+SBConstant.LOG_SEPRATOR+"PreparePackageForShippingResult >>>>>>>>>>> " + result.toString());
                    break;
                }
            } catch (Exception e) {
                logger.error(requestId+SBConstant.LOG_SEPRATOR+"Exception Occured, getPreparePackageForShipping(...) for EDI_ORDER_ID = "+ sh.getEdiOrderId(), e);
            }
        }
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"getPreparePackageForShipping(...) for EDI_ORDER_ID = "+ sh.getEdiOrderId()+" ------------ END");
        return result;
    }
    
    /* (non-Javadoc)
	 * @see com.seller.box.amazon.gts.GTSExternalService#getShippingLabelForReprinting(com.seller.box.form.Shipment)
	 */
    @Override
	public GetShippingLabelsForReprintingResult getShippingLabelForReprinting(Shipment sh){
        GetShippingLabelsForReprintingResult result = null;
        
        for (int i = 0; i < retryCount; i++) {
            try {
                GetShippingLabelForReprinting gtsReprintLabel = new GetShippingLabelForReprinting();
                gtsReprintLabel.buildReprintLabelRequest(sh);
                result = gtsReprintLabel.callGTSReprintLabel();
                if (result != null) {
                    if (result.getLabels() != null) {
                        if (result.getLabels().size() > 0) {
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("Exception Occured, getShippingLabelForReprinting(...) ", e);
            }
        }
        return result;
    }
    
    /* (non-Javadoc)
	 * @see com.seller.box.amazon.gts.GTSExternalService#getDocumentForContainerResult(com.seller.box.form.Shipment)
	 */
    @Override
	public GetDocumentForContainerResult getDocumentForContainerResult(Shipment sh){
        GetDocumentForContainerResult containerRequest =  null;
        try {
            GTSGetDocumentForContainer gtsDocument = new GTSGetDocumentForContainer();
            gtsDocument.buildGetDocumentForContainerReq(sh);
            containerRequest = gtsDocument.callgetDocumentForContainer();
        } catch (Exception e) {
            logger.error("Exception Occured, getDocumentForContainerResult(...) ", e);
        }
        return containerRequest;
    }
    
    /* (non-Javadoc)
	 * @see com.seller.box.amazon.gts.GTSExternalService#menifestPackagesByIds(java.lang.String, com.seller.box.form.Shipment)
	 */
    @Override
	public ManifestPackagesByIdsResult menifestPackagesByIds(String requestId, Shipment sh){
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"menifestPackagesByIds(...) for EDI_ORDER_ID = "+ sh.getEdiOrderId()+SBConstant.LOG_SEPRATOR_WITH_START);
        ManifestPackagesByIdsResult result  = null;
        for (int i = 0; i < retryCount; i++) {
            try {
                if(sh.getManifestExceptionMessage() == null || sh.getManifestExceptionMessage().equalsIgnoreCase("RecoverableException")) {
                    GTSManifestPackagesByIds gtsManifestPackages = new GTSManifestPackagesByIds();
                    gtsManifestPackages.buildManifestPackageByIdsReq(requestId, sh);
                    result = gtsManifestPackages.callmanifestPackageByIds();
                    if (result != null) {
                        if (result.isCanManifest()) {
                            sh.setManifestExceptionMessage(null);
                            break;
                        }
                    }
                }
            } catch (RecoverableException e) {
                sh.setManifestExceptionMessage("RecoverableException");
                logger.error(requestId+SBConstant.LOG_SEPRATOR+"RecoverableException Occured, menifestPackagesByIds(...) ", e);
            } catch (UnrecoverableException e) {
                sh.setManifestExceptionMessage("UnrecoverableException");
                logger.error(requestId+SBConstant.LOG_SEPRATOR+"UnrecoverableException Occured, menifestPackagesByIds(...) ", e);
            } catch (InvalidRequestException e) {
                sh.setManifestExceptionMessage("InvalidRequestException");
                logger.error(requestId+SBConstant.LOG_SEPRATOR+"InvalidRequestException Occured, menifestPackagesByIds(...) ", e);
            } catch (DuplicateTrailerException e) {
                sh.setManifestExceptionMessage("DuplicateTrailerException");
                logger.error(requestId+SBConstant.LOG_SEPRATOR+"DuplicateTrailerException Occured, menifestPackagesByIds(...) ", e);
            } catch (PackagesAlreadyManifestedException e) {
                sh.setManifestExceptionMessage("PackagesAlreadyManifestedException");
                logger.error(requestId+SBConstant.LOG_SEPRATOR+"PackagesAlreadyManifestedException Occured, menifestPackagesByIds(...) ", e);
            } catch (Exception e) {
                sh.setManifestExceptionMessage("GeneralException");
                logger.error(requestId+SBConstant.LOG_SEPRATOR+"General Exception Occured, menifestPackagesByIds(...) ", e);
            }
        }
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"menifestPackagesByIds(...) for EDI_ORDER_ID = "+ sh.getEdiOrderId()+SBConstant.LOG_SEPRATOR_WITH_END);
        return result;
    }
    
    /* (non-Javadoc)
	 * @see com.seller.box.amazon.gts.GTSExternalService#gtsCancelPackageShipping(com.seller.box.form.Shipment)
	 */
    @Override
	public CancelPackageShippingResult gtsCancelPackageShipping(Shipment sh){
        CancelPackageShippingResult result = null;
        try {
            GTSCancelPackageShipping gtsCancelShipping = new GTSCancelPackageShipping();
            gtsCancelShipping.buildLabelCancelRequest(sh);
            result = gtsCancelShipping.callCancelPackageForShipping();
        } catch (Exception e) {
            logger.error("Exception Occured, gtsCancelPackageShipping(...) ", e);
        }
        return result;
    }
    
    /* (non-Javadoc)
	 * @see com.seller.box.amazon.gts.GTSExternalService#getPrintableManifestForTrailer(java.lang.String, com.seller.box.form.Shipment)
	 */
    @Override
	public GetPrintableManifestsForTrailerResult getPrintableManifestForTrailer(String requestId, Shipment sh){
        GetPrintableManifestsForTrailerResult result = null;
        for (int i = 0; i < retryCount; i++) {
            try {
                GTSGetPrintableManifestForTrailer gtsGetPrintable = new GTSGetPrintableManifestForTrailer();
                gtsGetPrintable.buildGetPrintableManifestRequest(requestId, sh);
                
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
                logger.error("Exception Occured, getPrintableManifestForTrailer(...) ", e);
            }
        }
        return result;
    }
    
    public GTSExternalSecurityService getGTSService() {
        logger.info("GTSService Start : " + new Date());
        GTSExternalSecurityService gts = null;
        try {
			String AWSAccessKey = SBUtils.getPropertyValue("AWSAccessKey");
			String AWSSecretKey = SBUtils.getPropertyValue("AWSSecretKey");
			String GTSEndpointURL = SBUtils.getPropertyValue("GTSEndpointURL");
			AWSCredentials awsCredentials = new BasicAWSCredentials(AWSAccessKey, AWSSecretKey);
			ClientConfiguration clientConfiguration = new ClientConfiguration();
			gts = new GTSExternalSecurityServiceClient(awsCredentials, clientConfiguration);
			gts.setEndpoint(GTSEndpointURL);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
        logger.info("GTSService End : " + new Date());
        return gts;
    }
}

