package com.seller.box.amazon.gts.api;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.gtsexternalsecurity.model.PreparePackageForShippingRequest;
import com.amazonaws.services.gtsexternalsecurity.model.PreparePackageForShippingResult;
import com.seller.box.amazon.gts.data.PreparePackageForShippingData;
import com.seller.box.amazon.gts.data.ShipmentIdentifiers;
import com.seller.box.form.Shipment;
import com.seller.box.utils.SBUtils;



public class GTSPreparePackageForShipping extends GTSService {
	private static final Logger logger = LogManager.getLogger(GTSPreparePackageForShipping.class);
    private PreparePackageForShippingRequest request;

    public void buildLabelRequest(Shipment ps) {
        logger.info("buildLabelRequest(PackageForShipping ps) for EDI_ORDER_ID = "+ ps.getEdiOrderId()+"/"+ps.getShipmentId()+" ------------ START");
        request = PreparePackageForShippingData.buildPPFSRequest(ps);
        logger.info("RequestObjectForPPFSE>>>" + request.toString());
        logger.info("buildLabelRequest(PackageForShipping ps) for EDI_ORDER_ID = "+ ps.getEdiOrderId()+"/"+ps.getShipmentId()+" ------------ END");
    }
    
    public PreparePackageForShippingResult callPreparePackageForShipping(Shipment ps) {
        logger.info("callPreparePackageForShipping(EdiOrderId "+ps.getEdiOrderId()+") ------------ START");
        PreparePackageForShippingResult result = null;
        try {
            result = gts.preparePackageForShipping(this.request);
            if(result != null) {
                logger.info("PPFSEResponse>>>" + result.toString());
                String shiplabelFilepath = printLabelData(result, ps.getShipmentId());
                ShipmentIdentifiers.setAmazonBarcode(result.getAmazonBarcode());
                ps.setShiplabelFilepath(shiplabelFilepath);
                ps.setBarcode(result.getAmazonBarcode());
                ps.setTrackingId(result.getTrackingId());
                ps.setReadyToPickUpTimeUTC(result.getShippingInfo().getPickUpDateUTC());
                ps.setCarrierName(result.getShippingInfo().getCarrierName());
                //TODO ADFUtils.putValueInPageflow("PackageForShipping", ps);
            }
        } catch (AmazonServiceException ase) {
            System.out.println(ase.getErrorCode());
            System.out.println(ase.getErrorType());
            System.out.println(ase.getStatusCode());
            System.out.println(ase.getMessage());
            String[] err = ase.getMessage().split(":");
            String errm = err[err.length - 1].trim();
            ps.setManifestErrorMessage(errm);
            logger.error("AmazonServiceException Occured, callPreparePackageForShipping(EdiOrderId "+ps.getEdiOrderId()+")", ase);
        } catch (AmazonClientException ace) {
            System.out.println(ace.getMessage());
            String[] err = ace.getMessage().split(":");
            String errm = err[err.length - 1].trim();
            ps.setManifestErrorMessage(errm);
            logger.error("AmazonClientException Occured, callPreparePackageForShipping(EdiOrderId "+ps.getEdiOrderId()+")", ace);
        } catch (Exception e) {
            logger.error("General Exception Occured, callPreparePackageForShipping(EdiOrderId "+ps.getEdiOrderId()+")", e);
        }
        logger.info("callPreparePackageForShipping(EdiOrderId "+ps.getEdiOrderId()+") ------------ END");
        return result;
    }

    @SuppressWarnings("resource")
	protected String printLabelData(PreparePackageForShippingResult result, String shipmentId) {
        logger.info("Got ship method: " + result.getShippingInfo().getShipMethod());
        logger.info("ShippingInfo: " + result.getShippingInfo());
        logger.info("Tracking Id: " + result.getTrackingId());
        logger.info("Amazon Barcode: " + result.getAmazonBarcode());
        //logger.info("Label Stram: " + result.getLabels().get(0).getLabelStream().toString());
        ByteBuffer labelStream = result.getLabels().get(0).getLabelStream();
        //logger.info("LabelStream size" + labelStream.array().length);
        String labelString = DatatypeConverter.printBase64Binary(labelStream.array());
        logger.info("Label = " + labelString);
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
            shipLabelPath = shipLabelPath + shipmentId+".txt";
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