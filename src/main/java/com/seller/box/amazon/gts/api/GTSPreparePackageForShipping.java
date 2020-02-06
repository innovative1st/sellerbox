package com.seller.box.amazon.gts.api;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

//import javax.xml.bind.DatatypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.gtsexternalsecurity.model.PreparePackageForShippingRequest;
import com.amazonaws.services.gtsexternalsecurity.model.PreparePackageForShippingResult;
import com.seller.box.amazon.gts.data.PreparePackageForShippingData;
import com.seller.box.amazon.gts.data.ShipmentIdentifiers;
import com.seller.box.form.Shipment;
import com.seller.box.utils.SBConstant;
import com.seller.box.utils.SBUtils;



public class GTSPreparePackageForShipping extends GTSService {
	private static final Logger logger = LogManager.getLogger(GTSPreparePackageForShipping.class);
	private PreparePackageForShippingRequest request;

    public void buildLabelRequest(String requestId, Shipment sh) {
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"buildLabelRequest(PackageForShipping ps) for EDI_ORDER_ID = "+ sh.getEdiOrderId()+"/"+sh.getShipmentId()+" ------------ START");
        request = PreparePackageForShippingData.buildPPFSRequest(requestId, sh);
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"RequestObjectForPPFSE>>>" + request.toString());
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"buildLabelRequest(PackageForShipping ps) for EDI_ORDER_ID = "+ sh.getEdiOrderId()+"/"+sh.getShipmentId()+" ------------ END");
    }
    
    public PreparePackageForShippingResult callPreparePackageForShipping(String requestId, Shipment sh) {
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"callPreparePackageForShipping(EdiOrderId "+sh.getEdiOrderId()+") ------------ START");
        PreparePackageForShippingResult result = null;
        try {
            result = gts.preparePackageForShipping(this.request);
            if(result != null) {
                logger.info(requestId+SBConstant.LOG_SEPRATOR+"PPFSEResponse>>>" + result.toString());
                String shiplabelFilepath = printLabelData(result, sh.getShipmentId());
                ShipmentIdentifiers.setAmazonBarcode(result.getAmazonBarcode());
                sh.setShiplabelFilepath(shiplabelFilepath);
                sh.setBarcode(result.getAmazonBarcode());
                sh.setTrackingId(result.getTrackingId());
                sh.setReadyToPickUpTimeUTC(result.getShippingInfo().getPickUpDateUTC());
                sh.setCarrierName(result.getShippingInfo().getCarrierName());
            }
        } catch (AmazonServiceException ase) {
            System.out.println(ase.getErrorCode());
            System.out.println(ase.getErrorType());
            System.out.println(ase.getStatusCode());
            System.out.println(ase.getMessage());
            if(ase.getMessage().toLowerCase().contains("shipmentinfo.packageinfo.weight.weightunit")) {
            	sh.setManifestErrorMessage("Shipment Info : Weight measurment issue, Please correct the measurment and re-manifest.");
            } else {
	            String[] err = ase.getMessage().split(":");
	            String errm = err[err.length - 1].trim();
	            sh.setManifestErrorMessage(errm);
            }
            logger.error(requestId+SBConstant.LOG_SEPRATOR+"AmazonServiceException Occured, callPreparePackageForShipping(EdiOrderId "+sh.getEdiOrderId()+")", ase);
        } catch (AmazonClientException ace) {
            System.out.println(ace.getMessage());
            String[] err = ace.getMessage().split(":");
            String errm = err[err.length - 1].trim();
            sh.setManifestErrorMessage(errm);
            logger.error(requestId+SBConstant.LOG_SEPRATOR+"AmazonClientException Occured, callPreparePackageForShipping(EdiOrderId "+sh.getEdiOrderId()+")", ace);
        } catch (Exception e) {
            logger.error(requestId+SBConstant.LOG_SEPRATOR+"General Exception Occured, callPreparePackageForShipping(EdiOrderId "+sh.getEdiOrderId()+")", e);
        }
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"callPreparePackageForShipping(EdiOrderId "+sh.getEdiOrderId()+") ------------ END");
        return result;
    }

    @SuppressWarnings("resource")
	protected String printLabelData(PreparePackageForShippingResult result, String shipmentId) {
        logger.info("Got ship method: " + result.getShippingInfo().getShipMethod());
        logger.info("ShippingInfo: " + result.getShippingInfo());
        logger.info("Tracking Id: " + result.getTrackingId());
        logger.info("Amazon Barcode: " + result.getAmazonBarcode());
        ByteBuffer labelStream = result.getLabels().get(0).getLabelStream();
        //logger.info("Label Stram: " + labelStream.toString());
        //logger.info("LabelStream size" + labelStream.array().length);
        //String labelString = DatatypeConverter.printBase64Binary(labelStream.array());
        //logger.info("Label = " + labelString);
        String shipLabelPath = null;
        if(result.getLabels() != null){
            shipLabelPath = SBUtils.getPropertyValue("seller.edi.ship.label.path");
            if(!shipLabelPath.endsWith("\\")){
                shipLabelPath = shipLabelPath + "\\";
            }
            String dateFolder = new SimpleDateFormat("MMMyyyy").format(new Date());
            shipLabelPath = shipLabelPath + dateFolder+"\\";
            if(!new File(shipLabelPath).exists()) {
            	new File(shipLabelPath).mkdirs();
            }
            shipLabelPath = shipLabelPath + shipmentId+"_"+result.getTrackingId()+".txt";
            File file = new File(shipLabelPath);
            if(file.exists()){
                file.delete();
            }
            boolean append = false;
    
            FileChannel wChannel;
    
            try {
                wChannel = new FileOutputStream(file, append).getChannel();
                wChannel.write(labelStream);
                wChannel.close();
            } catch (FileNotFoundException e) {
                shipLabelPath = null;
                logger.error("FileNotFoundException Occured, printLabelData("+shipmentId+")", e);
            }catch (IOException e) {
                shipLabelPath = null;
                logger.error("IOException Occured, printLabelData("+shipmentId+")", e);
            }
        }
        return shipLabelPath;
    }
}
