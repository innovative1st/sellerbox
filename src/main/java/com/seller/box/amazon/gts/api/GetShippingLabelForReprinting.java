package com.seller.box.amazon.gts.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.gtsexternalsecurity.model.GetShippingLabelsForReprintingRequest;
import com.amazonaws.services.gtsexternalsecurity.model.GetShippingLabelsForReprintingResult;
import com.amazonaws.services.gtsexternalsecurity.model.InvalidRequestException;
import com.amazonaws.services.gtsexternalsecurity.model.RecoverableException;
import com.amazonaws.services.gtsexternalsecurity.model.UnrecoverableException;
import com.seller.box.amazon.gts.data.GetShippingLabelForReprintingData;
import com.seller.box.form.Shipment;


public class GetShippingLabelForReprinting extends GTSService {
	private static final Logger logger = LogManager.getLogger(GetShippingLabelForReprinting.class);
	private GetShippingLabelsForReprintingRequest request;
	
//    public void buildReprintLabelRequest() {
//            this.request = GetShippingLabelForReprintingData.buildReprintRequest();
//            System.out.println("Reprint Request" + request.toString());
//    }
//    public void callGTSReprintLabel() {
//        GetShippingLabelsForReprintingResult result = gts.getShippingLabelsForReprinting(this.request);
//        System.out.println("Reprint Response" + result.toString());
//        PackageShippingLabel label = result.getLabels().get(0);
//        ByteBuffer labelStream = label.getLabelStream();
//        System.out.println("LabelFormatType : "+label.getLabelFormatType());
//        LabelDimensions labelDiamention = label.getLabelDimensions();
//        Dimension diamention = labelDiamention.getLength();
//        diamention.getDimensionUnit();
//        diamention.getDimensionValue();
//        System.out.println("LabelStream size"+ labelStream.array().length);
//        String labelString = DatatypeConverter.printBase64Binary(labelStream.array());
//        System.out.println("Label = \n" +labelString);
//    }

    public void buildReprintLabelRequest(Shipment ps) {
        logger.info("buildReprintLabelRequest()------------------------------- START : " + ps.getEdiOrderId());
        this.request = GetShippingLabelForReprintingData.buildReprintRequest(ps);
        logger.info("Reprint Request" + request.toString());
        logger.info("buildReprintLabelRequest()------------------------------- END : " + ps.getEdiOrderId());
    }

    public GetShippingLabelsForReprintingResult callGTSReprintLabel() {
        logger.info("callGTSReprintLabel()------------------------------- START : ");
        int retries = 0;
        boolean retry = false;
        try {
            GetShippingLabelsForReprintingResult result = gts.getShippingLabelsForReprinting(this.request);
            result.getLabels().get(0).getLabelFormatType();
            result.getLabels().get(0).getContainsSupplementaryLabels();
            result.getLabels().get(0).getLabelStream();
            return result;
        } catch (RecoverableException re) {
            logger.error("*******RecoverableException occured*******", re);
            do {
                GetShippingLabelsForReprintingResult result = gts.getShippingLabelsForReprinting(this.request);
                if (!result.getLabels().isEmpty()) {
                    retry = false;
                } else
                    retry = true;
            } while (retry && (retries++ < 3));
        } catch (UnrecoverableException ure) {
            logger.error("*******UnrecoverableException occured*******", ure);
        } catch (InvalidRequestException ire) {
            logger.error("*******InvalidRequestException occured*******", ire);
        }
        /*            System.out.println("Reprint Response" + result.toString());
            PackageShippingLabel label = result.getLabels().get(0);
            ByteBuffer labelStream = label.getLabelStream();
            System.out.println("LabelFormatType : "+label.getLabelFormatType());
            LabelDimensions labelDiamention = label.getLabelDimensions();
            Dimension diamention = labelDiamention.getLength();
            diamention.getDimensionUnit();
            diamention.getDimensionValue();
            System.out.println("LabelStream size"+ labelStream.array().length);
            String labelString = DatatypeConverter.printBase64Binary(labelStream.array());
            System.out.println("Label = \n" +labelString);
*/
        logger.info("callGTSReprintLabel()------------------------------- END : ");
        return null;
    }
}
