package com.seller.box.form;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.seller.box.utils.SBUtils;

public class Shipment implements Serializable{
	private static final long serialVersionUID = 1L;
    private Long ediOrderId;													//EdiOrderId for reference
    private String operationMode = SBUtils.getPropertyValue("amazon.manifest.opration.mode");//Operation Mode
    private String pickUpType = SBUtils.getPropertyValue("pickup.type");		//Pickup Type
    private Date readyToPickUpTimeUTC;
    private boolean requiresGuaranteedPromisedDelivery = Boolean.TRUE;			
    private String localTimeZone = SBUtils.getPropertyValue("local.time.zone");	//LocalTimeZone    
    private String operatingWarehouseCode;										//Operation warehouse Code
    private String shipmentId;													//Purchase Order Number
    private String orderId;														//Customer Order Number
    private String originWarehouseCode;
    private String packageId;													//PackageId
    private String heightDimensionUnit;    										//Package Dimension Height Unit 
    private double heightDimensionValue;    									//Package Dimension Height
    private String lengthDimensionUnit;     									//Package Dimension Length Unit 
    private double lengthDimensionValue;   										//Package Dimension Length
    private String widthDimensionUnit;     										//Package Dimension Width Unit 
    private double widthDimensionValue;    										//Package Dimension Width
    private String weightDiamensionUnit;   										//Package Dimension Weight Unit 
    private double weightDiamensionValue;  										//Package Dimension Weight
    private String labelLengthDimensionUnit;
    private double labelLengthDimensionValue;
    private String labelWidthDimensionUnit;
    private double labelWidthDimensionValue;    
    private String labelFormatType;												//Label Format type default = ZPL
    private String barcode;
    private String loadId;
    private String trailorId;
    private String trackingId;
    private String canManifest;
    private String carrierName;
    private String invoiceFilepath;												//absolute file-path of invoice copy
    private String shiplabelFilepath;											//absolute file-path of ship-label copy
    private String manifestId;
    private String manifestErrorMessage;										//Error message during manifest
    private String paymentType;
    private double balanceDue;
    private String currencyCode;
    private String marketplaceId; 												//OrderSiteId in OF
    private String hazmatDataAuthority = SBUtils.getPropertyValue("hazmat.data.authority");
    private String braaPpType; 
    private String braaPpTypeIdentifier ;
    private int braaPpQuantity ;
    private int orderCancelled;
    private int isGift;
    private int isGiftWrap;
    private int isFastTrack;
    private int isPaperDunnage;
    private String shipMethod;
    private String boxType;
    private double packageLengthValue;
	private String packageLengthUnit;
	private double packageWidthValue;
	private String packageWidthUnit;
	private double packageHeightValue;
	private String packageHeightUnit;
	private double packageWeightValue;
	private String packageWeightUnit;
    private String manifestExceptionMessage;
    private List<ShipmentItem> shipmentItem;									//Shipment associated items list 
	public Long getEdiOrderId() {
		return ediOrderId;
	}
	public void setEdiOrderId(Long ediOrderId) {
		this.ediOrderId = ediOrderId;
	}
	public String getOperationMode() {
		return operationMode;
	}
	public void setOperationMode(String operationMode) {
		this.operationMode = operationMode;
	}
	public String getPickUpType() {
		return pickUpType;
	}
	public void setPickUpType(String pickUpType) {
		this.pickUpType = pickUpType;
	}
	public Date getReadyToPickUpTimeUTC() {
		return readyToPickUpTimeUTC;
	}
	public void setReadyToPickUpTimeUTC(Date readyToPickUpTimeUTC) {
		this.readyToPickUpTimeUTC = readyToPickUpTimeUTC;
	}
	public boolean isRequiresGuaranteedPromisedDelivery() {
		return requiresGuaranteedPromisedDelivery;
	}
	public void setRequiresGuaranteedPromisedDelivery(boolean requiresGuaranteedPromisedDelivery) {
		this.requiresGuaranteedPromisedDelivery = requiresGuaranteedPromisedDelivery;
	}
	public String getLocalTimeZone() {
		return localTimeZone;
	}
	public void setLocalTimeZone(String localTimeZone) {
		this.localTimeZone = localTimeZone;
	}
	public String getOperatingWarehouseCode() {
		return operatingWarehouseCode;
	}
	public void setOperatingWarehouseCode(String operatingWarehouseCode) {
		this.operatingWarehouseCode = operatingWarehouseCode;
	}
	public String getShipmentId() {
		return shipmentId;
	}
	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOriginWarehouseCode() {
		return originWarehouseCode;
	}
	public void setOriginWarehouseCode(String originWarehouseCode) {
		this.originWarehouseCode = originWarehouseCode;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getHeightDimensionUnit() {
		return heightDimensionUnit;
	}
	public void setHeightDimensionUnit(String heightDimensionUnit) {
		this.heightDimensionUnit = heightDimensionUnit;
	}
	public double getHeightDimensionValue() {
		return heightDimensionValue;
	}
	public void setHeightDimensionValue(double heightDimensionValue) {
		this.heightDimensionValue = heightDimensionValue;
	}
	public String getLengthDimensionUnit() {
		return lengthDimensionUnit;
	}
	public void setLengthDimensionUnit(String lengthDimensionUnit) {
		this.lengthDimensionUnit = lengthDimensionUnit;
	}
	public double getLengthDimensionValue() {
		return lengthDimensionValue;
	}
	public void setLengthDimensionValue(double lengthDimensionValue) {
		this.lengthDimensionValue = lengthDimensionValue;
	}
	public String getWidthDimensionUnit() {
		return widthDimensionUnit;
	}
	public void setWidthDimensionUnit(String widthDimensionUnit) {
		this.widthDimensionUnit = widthDimensionUnit;
	}
	public double getWidthDimensionValue() {
		return widthDimensionValue;
	}
	public void setWidthDimensionValue(double widthDimensionValue) {
		this.widthDimensionValue = widthDimensionValue;
	}
	public String getWeightDiamensionUnit() {
		return weightDiamensionUnit;
	}
	public void setWeightDiamensionUnit(String weightDiamensionUnit) {
		this.weightDiamensionUnit = weightDiamensionUnit;
	}
	public double getWeightDiamensionValue() {
		return weightDiamensionValue;
	}
	public void setWeightDiamensionValue(double weightDiamensionValue) {
		this.weightDiamensionValue = weightDiamensionValue;
	}
	public String getLabelLengthDimensionUnit() {
		return labelLengthDimensionUnit;
	}
	public void setLabelLengthDimensionUnit(String labelLengthDimensionUnit) {
		this.labelLengthDimensionUnit = labelLengthDimensionUnit;
	}
	public double getLabelLengthDimensionValue() {
		return labelLengthDimensionValue;
	}
	public void setLabelLengthDimensionValue(double labelLengthDimensionValue) {
		this.labelLengthDimensionValue = labelLengthDimensionValue;
	}
	public String getLabelWidthDimensionUnit() {
		return labelWidthDimensionUnit;
	}
	public void setLabelWidthDimensionUnit(String labelWidthDimensionUnit) {
		this.labelWidthDimensionUnit = labelWidthDimensionUnit;
	}
	public double getLabelWidthDimensionValue() {
		return labelWidthDimensionValue;
	}
	public void setLabelWidthDimensionValue(double labelWidthDimensionValue) {
		this.labelWidthDimensionValue = labelWidthDimensionValue;
	}
	public String getLabelFormatType() {
		return labelFormatType;
	}
	public void setLabelFormatType(String labelFormatType) {
		this.labelFormatType = labelFormatType;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getLoadId() {
		return loadId;
	}
	public void setLoadId(String loadId) {
		this.loadId = loadId;
	}
	public String getTrailorId() {
		return trailorId;
	}
	public void setTrailorId(String trailorId) {
		this.trailorId = trailorId;
	}
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public String getCanManifest() {
		return canManifest;
	}
	public void setCanManifest(String canManifest) {
		this.canManifest = canManifest;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public String getInvoiceFilepath() {
		return invoiceFilepath;
	}
	public void setInvoiceFilepath(String invoiceFilepath) {
		this.invoiceFilepath = invoiceFilepath;
	}
	public String getShiplabelFilepath() {
		return shiplabelFilepath;
	}
	public void setShiplabelFilepath(String shiplabelFilepath) {
		this.shiplabelFilepath = shiplabelFilepath;
	}
	public String getManifestId() {
		return manifestId;
	}
	public void setManifestId(String manifestId) {
		this.manifestId = manifestId;
	}
	public String getManifestErrorMessage() {
		return manifestErrorMessage;
	}
	public void setManifestErrorMessage(String manifestErrorMessage) {
		this.manifestErrorMessage = manifestErrorMessage;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public double getBalanceDue() {
		return balanceDue;
	}
	public void setBalanceDue(double balanceDue) {
		this.balanceDue = balanceDue;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getMarketplaceId() {
		return marketplaceId;
	}
	public void setMarketplaceId(String marketplaceId) {
		this.marketplaceId = marketplaceId;
	}
	public String getHazmatDataAuthority() {
		return hazmatDataAuthority;
	}
	public void setHazmatDataAuthority(String hazmatDataAuthority) {
		this.hazmatDataAuthority = hazmatDataAuthority;
	}
	public String getBraaPpType() {
		return braaPpType;
	}
	public void setBraaPpType(String braaPpType) {
		this.braaPpType = braaPpType;
	}
	public String getBraaPpTypeIdentifier() {
		return braaPpTypeIdentifier;
	}
	public void setBraaPpTypeIdentifier(String braaPpTypeIdentifier) {
		this.braaPpTypeIdentifier = braaPpTypeIdentifier;
	}
	public int getBraaPpQuantity() {
		return braaPpQuantity;
	}
	public void setBraaPpQuantity(int braaPpQuantity) {
		this.braaPpQuantity = braaPpQuantity;
	}
	public int getOrderCancelled() {
		return orderCancelled;
	}
	public void setOrderCancelled(int orderCancelled) {
		this.orderCancelled = orderCancelled;
	}
	public int getIsGift() {
		return isGift;
	}
	public void setIsGift(int isGift) {
		this.isGift = isGift;
	}
	public int getIsGiftWrap() {
		return isGiftWrap;
	}
	public void setIsGiftWrap(int isGiftWrap) {
		this.isGiftWrap = isGiftWrap;
	}
	public int getIsFastTrack() {
		return isFastTrack;
	}
	public void setIsFastTrack(int isFastTrack) {
		this.isFastTrack = isFastTrack;
	}
	public int getIsPaperDunnage() {
		return isPaperDunnage;
	}
	public void setIsPaperDunnage(int isPaperDunnage) {
		this.isPaperDunnage = isPaperDunnage;
	}
	public String getShipMethod() {
		return shipMethod;
	}
	public void setShipMethod(String shipMethod) {
		this.shipMethod = shipMethod;
	}
	public String getBoxType() {
		return boxType;
	}
	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}
	public double getPackageLengthValue() {
		return packageLengthValue;
	}
	public void setPackageLengthValue(double packageLengthValue) {
		this.packageLengthValue = packageLengthValue;
	}
	public String getPackageLengthUnit() {
		return packageLengthUnit;
	}
	public void setPackageLengthUnit(String packageLengthUnit) {
		this.packageLengthUnit = packageLengthUnit;
	}
	public double getPackageWidthValue() {
		return packageWidthValue;
	}
	public void setPackageWidthValue(double packageWidthValue) {
		this.packageWidthValue = packageWidthValue;
	}
	public String getPackageWidthUnit() {
		return packageWidthUnit;
	}
	public void setPackageWidthUnit(String packageWidthUnit) {
		this.packageWidthUnit = packageWidthUnit;
	}
	public double getPackageHeightValue() {
		return packageHeightValue;
	}
	public void setPackageHeightValue(double packageHeightValue) {
		this.packageHeightValue = packageHeightValue;
	}
	public String getPackageHeightUnit() {
		return packageHeightUnit;
	}
	public void setPackageHeightUnit(String packageHeightUnit) {
		this.packageHeightUnit = packageHeightUnit;
	}
	public double getPackageWeightValue() {
		return packageWeightValue;
	}
	public void setPackageWeightValue(double packageWeightValue) {
		this.packageWeightValue = packageWeightValue;
	}
	public String getPackageWeightUnit() {
		return packageWeightUnit;
	}
	public void setPackageWeightUnit(String packageWeightUnit) {
		this.packageWeightUnit = packageWeightUnit;
	}
	public String getManifestExceptionMessage() {
		return manifestExceptionMessage;
	}
	public void setManifestExceptionMessage(String manifestExceptionMessage) {
		this.manifestExceptionMessage = manifestExceptionMessage;
	}
	public List<ShipmentItem> getShipmentItem() {
		return shipmentItem;
	}
	public void setShipmentItem(List<ShipmentItem> shipmentItem) {
		this.shipmentItem = shipmentItem;
	}
}