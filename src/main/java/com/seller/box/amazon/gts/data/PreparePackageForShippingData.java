package com.seller.box.amazon.gts.data;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.gtsexternalsecurity.model.Currency;
import com.amazonaws.services.gtsexternalsecurity.model.Dimension;
import com.amazonaws.services.gtsexternalsecurity.model.ItemInfoForShipping;
import com.amazonaws.services.gtsexternalsecurity.model.LabelDimensions;
import com.amazonaws.services.gtsexternalsecurity.model.LabelSpecification;
import com.amazonaws.services.gtsexternalsecurity.model.ManifestingOptions;
import com.amazonaws.services.gtsexternalsecurity.model.PackageInfoForShipping;
import com.amazonaws.services.gtsexternalsecurity.model.PickUpInfo;
import com.amazonaws.services.gtsexternalsecurity.model.PreparePackageForShippingRequest;
import com.amazonaws.services.gtsexternalsecurity.model.RequestProperties;
import com.amazonaws.services.gtsexternalsecurity.model.ServiceOptions;
import com.amazonaws.services.gtsexternalsecurity.model.ShipmentInfoForShipping;
import com.amazonaws.services.gtsexternalsecurity.model.Weight;
import com.seller.box.form.Shipment;
import com.seller.box.form.ShipmentItem;
import com.seller.box.utils.SBConstant;



public class PreparePackageForShippingData {
	private static final Logger logger = LogManager.getLogger(PreparePackageForShippingData.class);

    public static PreparePackageForShippingRequest buildPPFSRequest(String requestId, Shipment ps) {
        
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"buildPPFSRequest()------------------------------- START : "+ ps.getShipmentId());
        
        PreparePackageForShippingData ppsData = new PreparePackageForShippingData();
        //Shipping Request
        PreparePackageForShippingRequest request = new PreparePackageForShippingRequest();
        //Time Zone
        request.setLocalTimeZone(ps.getLocalTimeZone());
        //Operation Mode
        request.setOperationMode(ps.getOperationMode());
        //ServiceOption
        ServiceOptions serviceOptions = getServiceInfoDetails(requestId, ps.getPickUpType());
        request.setServiceOptions(serviceOptions);
        //Operation Wearhouse Id
        request.setOperatingWarehouseId(ps.getOperatingWarehouseCode());
        //Shipment Info
        ShipmentInfoForShipping shipmentInfo = ppsData.createShipmentInfoForShipping(requestId, ps);
        request.setShipmentInfo(shipmentInfo);
        //Label Info
        LabelSpecification labelSpecification = ppsData.createLabelSpecification(requestId, ps);
        request.setLabelSpecification(labelSpecification);
        //Manifesting Options
        ManifestingOptions manifestingOptions = new ManifestingOptions();
        manifestingOptions.setWillUseManifestingAPI(true);
        manifestingOptions.setManifestingTimeUTC(new Date());
        request.setManifestingOptions(manifestingOptions);

        RequestProperties requestproperty = new RequestProperties();
        requestproperty.setRequestId("ExternalClientRequest-" + System.currentTimeMillis());
        requestproperty.setSequenceNumber(1);
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"buildPPFSRequest()------------------------------- END : "+ ps.getShipmentId());
        
        return request;
    }
    
    //Service Info Details
    private static ServiceOptions getServiceInfoDetails(String requestId, String pickUpType) {
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"getServiceInfoDetails()------------------------------- START" );
        ServiceOptions serviceOptions = new ServiceOptions();
        PickUpInfo pickUpInfo = new PickUpInfo();
        pickUpInfo.setPickUpType(pickUpType);
        pickUpInfo.setReadyToPickUpTimeUTC(new Date());
        serviceOptions.setPickUpInfo(pickUpInfo);
        serviceOptions.setRequiresGuaranteedPromisedDelivery(Boolean.TRUE);
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"getServiceInfoDetails()------------------------------- END");
        return serviceOptions;
    }

    //Shipment Info
    private ShipmentInfoForShipping createShipmentInfoForShipping(String requestId, Shipment ps) {
        
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"createShipmentInfoForShipping()------------------------------- START : ");
        
        ShipmentInfoForShipping shipmentInfo = new ShipmentInfoForShipping();
        //Shipment Id
        Map<String, String> clientRefShipmentId = getShipmentId(requestId, ps.getShipmentId());
        shipmentInfo.setClientRefShipmentId(clientRefShipmentId);
        //MarketplaceId
        try {
            shipmentInfo.setMarketplaceId(Long.parseLong(ps.getMarketplaceId()));
        } catch (NumberFormatException nfe) {
            logger.error(requestId+SBConstant.LOG_SEPRATOR+"NumberFormatException while parsing MarketplaceId.");
        }
        //Warehouse ID
        shipmentInfo.setOriginWarehouseId(ps.getOriginWarehouseCode());
        //Prepare and Setup Package Info
        PackageInfoForShipping packageDetails = getPackageDetails(requestId, ps);
        shipmentInfo.setPackageInfo(packageDetails);     
        //shipmentInfo.setOrderId(ps.getShipmentId());
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"createShipmentInfoForShipping()------------------------------- END : ");
        return shipmentInfo;
    }
    
    //Prepare and Setup Package Info
    private PackageInfoForShipping getPackageDetails(String requestId, Shipment ps) {
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"getPackageDetails()------------------------------- START : "+ ps.getShipmentId());
        PackageInfoForShipping packageInfo = new PackageInfoForShipping();
        try {
            if (ps.getCurrencyCode() != null && ps.getBalanceDue() > 0) {
                Currency currency = new Currency();
                currency.setCurrencyCode(ps.getCurrencyCode());
                currency.setCurrencyValue(ps.getBalanceDue());
                packageInfo.setCodBalanceDue(currency);
            }
        } catch (Exception e) {
            logger.error(requestId+SBConstant.LOG_SEPRATOR+"Exception while providing CodBalanceDue");
        }
        
        Map<String, String> clientRefPackageId = new HashMap<String, String>();
        clientRefPackageId.put("PACKAGE_ID", ps.getPackageId());
        packageInfo.setClientRefPackageId(clientRefPackageId);
        
        Dimension height = new Dimension();
        height.setDimensionUnit(ps.getHeightDimensionUnit());
        height.setDimensionValue(ps.getHeightDimensionValue());
        packageInfo.setHeight(height);
        
        Dimension length = new Dimension();
        length.setDimensionUnit(ps.getLengthDimensionUnit());
        length.setDimensionValue(ps.getLengthDimensionValue());
        packageInfo.setLength(length);
        
        Dimension width = new Dimension();
        width.setDimensionUnit(ps.getWidthDimensionUnit());
        width.setDimensionValue(ps.getWidthDimensionValue());
        packageInfo.setWidth(width);
        
        Weight weight = new Weight();
        weight.setWeightUnit(ps.getWeightDiamensionUnit());
        weight.setWeightValue(ps.getWeightDiamensionValue());
        packageInfo.setWeight(weight);
        //Prepare and setup ItemInfo List
        getItemInfoList(requestId, ps, packageInfo);
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"getPackageDetails()------------------------------- END : "+ ps.getShipmentId());
        return packageInfo;
    }
    
    //ItemInfo List
    private void getItemInfoList(String requestId, Shipment ps, PackageInfoForShipping packageInfo) {
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"getItemInfoList()------------------------------- START : "+ ps.getShipmentId());
        List<ItemInfoForShipping> itemsInfo = new ArrayList<ItemInfoForShipping>();//Collections.singletonList(itemInfo);
        for(ShipmentItem i : ps.getShipmentItem()){
            ItemInfoForShipping iifs = new ItemInfoForShipping();
            iifs.setASIN(i.getItemId());
            iifs.setQuantity(i.getQuantity());
            if (ps.getHazmatDataAuthority() != null && !ps.getHazmatDataAuthority().isEmpty()) {
                iifs.setHazmatDataAuthority(ps.getHazmatDataAuthority()); //As suggested by Vineela
            }
            iifs.setCustomerOrderItemDetailId(i.getCustomerOrderItemDetailID());//As suggested by Vineela
            iifs.setCustomerOrderItemId(i.getCustomerOrderItemID());//As suggested by Vineela
            itemsInfo.add(iifs);
        }
        packageInfo.setItemsInfo(itemsInfo);
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"getItemInfoList()------------------------------- END : "+ ps.getShipmentId());
    }

    //Shipment Id
    public Map<String, String> getShipmentId(String requestId, String shipmentId) {
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"getShipmentId()------------------------------- START : "+ shipmentId);
        Map<String, String> clientRefShipmentId = new HashMap<String, String>();
        clientRefShipmentId.put("SHIPMENT_ID", shipmentId);
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"getShipmentId()------------------------------- END : "+ shipmentId);
        return clientRefShipmentId;
    }

    //Label Info
    private LabelSpecification createLabelSpecification(String requestId, Shipment ps) {
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"createLabelSpecification()------------------------------- START : "+ ps.getShipmentId());
        LabelSpecification labelSpecification = new LabelSpecification();
        labelSpecification.setClientRefBarCodeData(null); //As suggested by Vineela
        //labelSpecification.setClientRefBarCodeData(ps.getShipmentId());
        labelSpecification.setClientRefData(ps.getShipmentId());
        labelSpecification.setFormatType(ps.getLabelFormatType());
        LabelDimensions labelDimensions = new LabelDimensions();
        
        Dimension length = new Dimension();
        length.setDimensionUnit(ps.getLabelLengthDimensionUnit());
        length.setDimensionValue(ps.getLabelLengthDimensionValue());
        
        Dimension width = new Dimension();
        width.setDimensionUnit(ps.getLabelWidthDimensionUnit());
        width.setDimensionValue(ps.getLabelWidthDimensionValue());
        
        labelDimensions.setLength(length);
        labelDimensions.setWidth(width);
        labelSpecification.setLabelDimensions(labelDimensions);
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"createLabelSpecification()------------------------------- END : "+ ps.getShipmentId());
        return labelSpecification;
    }

}
