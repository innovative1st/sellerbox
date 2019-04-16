package com.seller.box.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "EDI_SHIPMENT_HDR", catalog = "SELLER")
public class EdiShipmentHdr implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "EDI_ORDER_ID")
	private Long ediOrderId;
	@Column(name = "ERETAILOR_ID")
	private int etailorId;
	//@Column(name = "ERETAILOR_NAME")
	//private String ERETAILOR_NAME;
	@Column(name = "PURCHASE_ORDER_NUMBER", unique = true, updatable = false)
	private String shipmentId;
	@Column(name = "CUSTOMER_ORDER_NUMBER")
	private String orderId;
	@Column(name = "BILL_TO_ENTITY_ID")
	private String billtoEntityId;
	@Column(name = "WAREHOUSE_CODE")
	private String warehouseCode;
	//@Column(name = "WAREHOUSE_ID")
	//private String WAREHOUSE_ID;
	@Column(name = "ORDERE_DATE")
	private Date orderDate;
	@Column(name = "EXPECTED_SHIP_DATE")
	private Date exsdDate;
	@Column(name = "BOX_TYPE")
	private String boxType;
	@Column(name = "IS_GIFT")
	private int isGift;
	@Column(name = "IS_GIFT_WRAP")
	private int isGiftWrap;
	@Column(name = "IS_PRIORITY_SHIPMENT")
	private int isPriorityShipment;
	@Column(name = "IS_COD")
	private int isCod;
	@Column(name = "IS_INSERTS_REQUIRED")
	private int isInsertsRequired;
	@Column(name = "PAYMENT_TYPE")
	private String paymentType;
	@Column(name = "CURRENCY_CODE")
	private String currencyCode;
	@Column(name = "TAX_RATE")
	private double taxRate;
	@Column(name = "TAX_AMOUNT")
	private double taxAmount;
	@Column(name = "SHIP_CHARGE_AMOUNT")
	private double shipChargeAmount;
	@Column(name = "SUB_TOTAL")
	private double subTotal;
	@Column(name = "ORDER_TOTAL")
	private double orderTotal;
	@Column(name = "BALANCE_DUE")
	private double balanceDue;
	@Column(name = "BUYER_NAME")
	private String buyerName;
	@Column(name = "BUYER_ATTENTION_LINE")
	private String buyerAttentionLine;
	@Column(name = "BUYER_ADDRESS_LINE1")
	private String buyerAddressLine1;
	@Column(name = "BUYER_CITY")
	private String buyerCity;
	@Column(name = "BUYER_STATE")
	private String buyerState;
	@Column(name = "BUYER_COUNTRY_CODE")
	private String buyerCountryCode;
	@Column(name = "BUYER_POSTAL_CODE")
	private String buyerPostalCode;
	@Column(name = "ORDER_SITE_ID")
	private int orderSiteId;
	@Column(name = "SHIP_TO_NAME")
	private String shipToName;
	@Column(name = "SHIP_TO_ADDRESS_LINE1")
	private String shipToAddressLine1;
	@Column(name = "SHIP_TO_ADDRESS_LINE2")
	private String shipToAddressLine2;
	@Column(name = "SHIP_TO_CITY")
	private String shipToCity;
	@Column(name = "SHIP_TO_STATE")
	private String shipToState;
	@Column(name = "SHIP_TO_COUNTRY_CODE")
	private String shipToCountryCode;
	@Column(name = "SHIP_TO_COUNTRY_NAME")
	private String shipToCountryName;
	@Column(name = "SHIP_POSTAL_CODE")
	private String shipPostalCode;
	@Column(name = "SHIP_TO_CONTACT_PHONE")
	private String shipToContactPhone;
	@Column(name = "SHIP_METHOD")
	private String shipMethod;
	@Column(name = "BILL_TO_NAME")
	private String billToName;
	@Column(name = "BILL_TO_ADDRESS_LINE1")
	private String billToAddressLine1;
	@Column(name = "BILL_TO_ADDRESS_LINE2")
	private String billToAddressLine2;
	@Column(name = "BILL_TO_CITY")
	private String billToCity;
	@Column(name = "BILL_TO_STATE")
	private String billToState;
	@Column(name = "BILL_TO_COUNTRY_CODE")
	private String billToCountryCode;
	@Column(name = "BILL_TO_COUNTRY_NAME")
	private String billToCountryName;
	@Column(name = "BILL_POSTAL_CODE")
	private String billPostalCode;
	@Column(name = "BILL_TO_CONTACT_PHONE")
	private String billToContactPhone;
	@Column(name = "PACKAGE_ID")
	private int packageId;
	@Column(name = "TRACKING_ID")
	private String trackingId;
	@Column(name = "BARCODE")
	private String barcode;
	@Column(name = "TRAILER_ID")
	private String trailorId;
	@Column(name = "LOAD_ID")
	private String loadId;
	@Column(name = "CAN_MANIFEST")
	private String canManifest;
	@Column(name = "MANIFEST_ID")
	private String manifestId;
	@Column(name = "CARRIER_NAME")
	private String carrierName;
	@Column(name = "LOCAL_TIME_ZONE")
	private String localTimeZone;
	@Column(name = "PICKUP_DATE")
	private Date pickupDate;
	@Column(name = "LABEL_CONTENT")
	private String labelContent;
	@Column(name = "LABEL_FORMAT_TYPE")
	private String labelFormatType;
	@Column(name = "LABEL_WIDTH_VALUE")
	private int labelWidthValue;
	@Column(name = "LABEL_WIDTH_UNIT")
	private String labelWidthUnit;
	@Column(name = "LABEL_LENGTH_VALUE")
	private int labelLengthValue;
	@Column(name = "LABEL_LENGTH_UNIT")
	private String labelLengthUnit;
	@Column(name = "PACKAGE_LENGTH_VALUE")
	private double packageLengthValue;
	@Column(name = "PACKAGE_LENGTH_UNIT")
	private String packageLengthUnit;
	@Column(name = "PACKAGE_WIDTH_VALUE")
	private double packageWidthValue;
	@Column(name = "PACKAGE_WIDTH_UNIT")
	private String packageWidthUnit;
	@Column(name = "PACKAGE_HEIGHT_VALUE")
	private double packageHeightValue;
	@Column(name = "PACKAGE_HEIGHT_UNIT")
	private String packageHeightUnit;
	@Column(name = "PACKAGE_WEIGHT_VALUE")
	private double packageWeightValue;
	@Column(name = "PACKAGE_WEIGHT_UNIT")
	private String packageWeightUnit;
	@Column(name = "BRAA_PP_TYPE")
	private String braaPpType;
	@Column(name = "BRAA_PP_TYPE_IDENTIFIER")
	private String braaPpTypeIdentifier;
	@Column(name = "BRAA_PP_QUANTITY")
	private int braaPpQuantity;
	@Column(name = "ORDER_STATUS")
	private int orderStatus;
	@Column(name = "PICKLIST_NUMBER")
	private String picklistNumber;
	@Column(name = "NO_OF_ITEMS")
	private int noOfItems;
	@Column(name = "FULFILMENT_TYPE")
	private String fulfilmentType;
	@Column(name = "PROCESS_INSTANCE_ID")
	private String processInstanceId;
	@Column(name = "ORDER_FILE_PATH")
	private String orderFilePath;
	//@Column(name = "INVOICE_FILE_PATH")
	//private String INVOICE_FILE_PATH;
	@Column(name = "SHIP_LABEL_FILE_PATH")
	private String shipLabelFilepath;
	@Column(name = "CREATED_BY")
	private String createdBy;
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	@Column(name = "MANIFEST_DATE")
	private String manifestDate;
	@Column(name = "PACKED_BY")
	private String packedBy;
	@Column(name = "IS_ACCEPTED")
	private int isAccepted;
	@Column(name = "IS_CANCELED")
	private int isCanceled;
	@Column(name = "IS_ASN_SEND")
	private int isAsnSend;
	@Column(name = "IS_OMS_UPLOAD")
	private int isOmsUpload;
	@Column(name = "IS_MEASURE_DONE")
	private int isMeasureDone;
	@Column(name = "OMS_UPLOAD_ERROR_MSG")
	private String omsUploadErrorMsg;
	@Column(name = "BATCH_ID")
	private String batchId;
	@Column(name = "REF_ORDER_ID")
	private String refOrderId;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "EDI_ORDER_ID", nullable = false)
	private Set<EdiShipmentItems> ediShipmentItems;
	
//	@OneToOne(fetch = FetchType.LAZY, optional = false)
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    @JoinColumn(name = "PURCHASE_ORDER_NUMBER", nullable = false)
//	private EdiShipmentInvoice ediShipmentInvoice;

	public Long getEdiOrderId() {
		return ediOrderId;
	}

	public void setEdiOrderId(Long ediOrderId) {
		this.ediOrderId = ediOrderId;
	}

	public int getEtailorId() {
		return etailorId;
	}

	public void setEtailorId(int etailorId) {
		this.etailorId = etailorId;
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

	public String getBilltoEntityId() {
		return billtoEntityId;
	}

	public void setBilltoEntityId(String billtoEntityId) {
		this.billtoEntityId = billtoEntityId;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getExsdDate() {
		return exsdDate;
	}

	public void setExsdDate(Date exsdDate) {
		this.exsdDate = exsdDate;
	}

	public String getBoxType() {
		return boxType;
	}

	public void setBoxType(String boxType) {
		this.boxType = boxType;
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

	public int getIsPriorityShipment() {
		return isPriorityShipment;
	}

	public void setIsPriorityShipment(int isPriorityShipment) {
		this.isPriorityShipment = isPriorityShipment;
	}

	public int getIsCod() {
		return isCod;
	}

	public void setIsCod(int isCod) {
		this.isCod = isCod;
	}

	public int getIsInsertsRequired() {
		return isInsertsRequired;
	}

	public void setIsInsertsRequired(int isInsertsRequired) {
		this.isInsertsRequired = isInsertsRequired;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(double taxRate) {
		this.taxRate = taxRate;
	}

	public double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public double getShipChargeAmount() {
		return shipChargeAmount;
	}

	public void setShipChargeAmount(double shipChargeAmount) {
		this.shipChargeAmount = shipChargeAmount;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public double getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(double orderTotal) {
		this.orderTotal = orderTotal;
	}

	public double getBalanceDue() {
		return balanceDue;
	}

	public void setBalanceDue(double balanceDue) {
		this.balanceDue = balanceDue;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerAttentionLine() {
		return buyerAttentionLine;
	}

	public void setBuyerAttentionLine(String buyerAttentionLine) {
		this.buyerAttentionLine = buyerAttentionLine;
	}

	public String getBuyerAddressLine1() {
		return buyerAddressLine1;
	}

	public void setBuyerAddressLine1(String buyerAddressLine1) {
		this.buyerAddressLine1 = buyerAddressLine1;
	}

	public String getBuyerCity() {
		return buyerCity;
	}

	public void setBuyerCity(String buyerCity) {
		this.buyerCity = buyerCity;
	}

	public String getBuyerState() {
		return buyerState;
	}

	public void setBuyerState(String buyerState) {
		this.buyerState = buyerState;
	}

	public String getBuyerCountryCode() {
		return buyerCountryCode;
	}

	public void setBuyerCountryCode(String buyerCountryCode) {
		this.buyerCountryCode = buyerCountryCode;
	}

	public String getBuyerPostalCode() {
		return buyerPostalCode;
	}

	public void setBuyerPostalCode(String buyerPostalCode) {
		this.buyerPostalCode = buyerPostalCode;
	}

	public int getOrderSiteId() {
		return orderSiteId;
	}

	public void setOrderSiteId(int orderSiteId) {
		this.orderSiteId = orderSiteId;
	}

	public String getShipToName() {
		return shipToName;
	}

	public void setShipToName(String shipToName) {
		this.shipToName = shipToName;
	}

	public String getShipToAddressLine1() {
		return shipToAddressLine1;
	}

	public void setShipToAddressLine1(String shipToAddressLine1) {
		this.shipToAddressLine1 = shipToAddressLine1;
	}

	public String getShipToAddressLine2() {
		return shipToAddressLine2;
	}

	public void setShipToAddressLine2(String shipToAddressLine2) {
		this.shipToAddressLine2 = shipToAddressLine2;
	}

	public String getShipToCity() {
		return shipToCity;
	}

	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}

	public String getShipToState() {
		return shipToState;
	}

	public void setShipToState(String shipToState) {
		this.shipToState = shipToState;
	}

	public String getShipToCountryCode() {
		return shipToCountryCode;
	}

	public void setShipToCountryCode(String shipToCountryCode) {
		this.shipToCountryCode = shipToCountryCode;
	}

	public String getShipToCountryName() {
		return shipToCountryName;
	}

	public void setShipToCountryName(String shipToCountryName) {
		this.shipToCountryName = shipToCountryName;
	}

	public String getShipPostalCode() {
		return shipPostalCode;
	}

	public void setShipPostalCode(String shipPostalCode) {
		this.shipPostalCode = shipPostalCode;
	}

	public String getShipToContactPhone() {
		return shipToContactPhone;
	}

	public void setShipToContactPhone(String shipToContactPhone) {
		this.shipToContactPhone = shipToContactPhone;
	}

	public String getShipMethod() {
		return shipMethod;
	}

	public void setShipMethod(String shipMethod) {
		this.shipMethod = shipMethod;
	}

	public String getBillToName() {
		return billToName;
	}

	public void setBillToName(String billToName) {
		this.billToName = billToName;
	}

	public String getBillToAddressLine1() {
		return billToAddressLine1;
	}

	public void setBillToAddressLine1(String billToAddressLine1) {
		this.billToAddressLine1 = billToAddressLine1;
	}

	public String getBillToAddressLine2() {
		return billToAddressLine2;
	}

	public void setBillToAddressLine2(String billToAddressLine2) {
		this.billToAddressLine2 = billToAddressLine2;
	}

	public String getBillToCity() {
		return billToCity;
	}

	public void setBillToCity(String billToCity) {
		this.billToCity = billToCity;
	}

	public String getBillToState() {
		return billToState;
	}

	public void setBillToState(String billToState) {
		this.billToState = billToState;
	}

	public String getBillToCountryCode() {
		return billToCountryCode;
	}

	public void setBillToCountryCode(String billToCountryCode) {
		this.billToCountryCode = billToCountryCode;
	}

	public String getBillToCountryName() {
		return billToCountryName;
	}

	public void setBillToCountryName(String billToCountryName) {
		this.billToCountryName = billToCountryName;
	}

	public String getBillPostalCode() {
		return billPostalCode;
	}

	public void setBillPostalCode(String billPostalCode) {
		this.billPostalCode = billPostalCode;
	}

	public String getBillToContactPhone() {
		return billToContactPhone;
	}

	public void setBillToContactPhone(String billToContactPhone) {
		this.billToContactPhone = billToContactPhone;
	}

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getTrailorId() {
		return trailorId;
	}

	public void setTrailorId(String trailorId) {
		this.trailorId = trailorId;
	}

	public String getLoadId() {
		return loadId;
	}

	public void setLoadId(String loadId) {
		this.loadId = loadId;
	}

	public String getCanManifest() {
		return canManifest;
	}

	public void setCanManifest(String canManifest) {
		this.canManifest = canManifest;
	}

	public String getManifestId() {
		return manifestId;
	}

	public void setManifestId(String manifestId) {
		this.manifestId = manifestId;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getLocalTimeZone() {
		return localTimeZone;
	}

	public void setLocalTimeZone(String localTimeZone) {
		this.localTimeZone = localTimeZone;
	}

	public Date getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}

	public String getLabelContent() {
		return labelContent;
	}

	public void setLabelContent(String labelContent) {
		this.labelContent = labelContent;
	}

	public String getLabelFormatType() {
		return labelFormatType;
	}

	public void setLabelFormatType(String labelFormatType) {
		this.labelFormatType = labelFormatType;
	}

	public int getLabelWidthValue() {
		return labelWidthValue;
	}

	public void setLabelWidthValue(int labelWidthValue) {
		this.labelWidthValue = labelWidthValue;
	}

	public String getLabelWidthUnit() {
		return labelWidthUnit;
	}

	public void setLabelWidthUnit(String labelWidthUnit) {
		this.labelWidthUnit = labelWidthUnit;
	}

	public int getLabelLengthValue() {
		return labelLengthValue;
	}

	public void setLabelLengthValue(int labelLengthValue) {
		this.labelLengthValue = labelLengthValue;
	}

	public String getLabelLengthUnit() {
		return labelLengthUnit;
	}

	public void setLabelLengthUnit(String labelLengthUnit) {
		this.labelLengthUnit = labelLengthUnit;
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

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPicklistNumber() {
		return picklistNumber;
	}

	public void setPicklistNumber(String picklistNumber) {
		this.picklistNumber = picklistNumber;
	}

	public int getNoOfItems() {
		return noOfItems;
	}

	public void setNoOfItems(int noOfItems) {
		this.noOfItems = noOfItems;
	}

	public String getFulfilmentType() {
		return fulfilmentType;
	}

	public void setFulfilmentType(String fulfilmentType) {
		this.fulfilmentType = fulfilmentType;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getOrderFilePath() {
		return orderFilePath;
	}

	public void setOrderFilePath(String orderFilePath) {
		this.orderFilePath = orderFilePath;
	}

	public String getShipLabelFilepath() {
		return shipLabelFilepath;
	}

	public void setShipLabelFilepath(String shipLabelFilepath) {
		this.shipLabelFilepath = shipLabelFilepath;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getManifestDate() {
		return manifestDate;
	}

	public void setManifestDate(String manifestDate) {
		this.manifestDate = manifestDate;
	}

	public String getPackedBy() {
		return packedBy;
	}

	public void setPackedBy(String packedBy) {
		this.packedBy = packedBy;
	}

	public int getIsAccepted() {
		return isAccepted;
	}

	public void setIsAccepted(int isAccepted) {
		this.isAccepted = isAccepted;
	}

	public int getIsCanceled() {
		return isCanceled;
	}

	public void setIsCanceled(int isCanceled) {
		this.isCanceled = isCanceled;
	}

	public int getIsAsnSend() {
		return isAsnSend;
	}

	public void setIsAsnSend(int isAsnSend) {
		this.isAsnSend = isAsnSend;
	}

	public int getIsOmsUpload() {
		return isOmsUpload;
	}

	public void setIsOmsUpload(int isOmsUpload) {
		this.isOmsUpload = isOmsUpload;
	}

	public int getIsMeasureDone() {
		return isMeasureDone;
	}

	public void setIsMeasureDone(int isMeasureDone) {
		this.isMeasureDone = isMeasureDone;
	}

	public String getOmsUploadErrorMsg() {
		return omsUploadErrorMsg;
	}

	public void setOmsUploadErrorMsg(String omsUploadErrorMsg) {
		this.omsUploadErrorMsg = omsUploadErrorMsg;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getRefOrderId() {
		return refOrderId;
	}

	public void setRefOrderId(String refOrderId) {
		this.refOrderId = refOrderId;
	}

	public Set<EdiShipmentItems> getEdiShipmentItems() {
		return ediShipmentItems;
	}

	public void setEdiShipmentItems(Set<EdiShipmentItems> ediShipmentItems) {
		this.ediShipmentItems = ediShipmentItems;
	}

	@Override
	public String toString() {
		return "EdiShipmentHdr [ediOrderId=" + ediOrderId + ", etailorId=" + etailorId + ", shipmentId=" + shipmentId
				+ ", orderId=" + orderId + ", billtoEntityId=" + billtoEntityId + ", warehouseCode=" + warehouseCode
				+ ", orderDate=" + orderDate + ", exsdDate=" + exsdDate + ", boxType=" + boxType + ", isGift=" + isGift
				+ ", isGiftWrap=" + isGiftWrap + ", isPriorityShipment=" + isPriorityShipment + ", isCod=" + isCod
				+ ", isInsertsRequired=" + isInsertsRequired + ", paymentType=" + paymentType + ", currencyCode="
				+ currencyCode + ", taxRate=" + taxRate + ", taxAmount=" + taxAmount + ", shipChargeAmount="
				+ shipChargeAmount + ", subTotal=" + subTotal + ", orderTotal=" + orderTotal + ", balanceDue="
				+ balanceDue + ", buyerName=" + buyerName + ", buyerAttentionLine=" + buyerAttentionLine
				+ ", buyerAddressLine1=" + buyerAddressLine1 + ", buyerCity=" + buyerCity + ", buyerState=" + buyerState
				+ ", buyerCountryCode=" + buyerCountryCode + ", buyerPostalCode=" + buyerPostalCode + ", orderSiteId="
				+ orderSiteId + ", shipToName=" + shipToName + ", shipToAddressLine1=" + shipToAddressLine1
				+ ", shipToAddressLine2=" + shipToAddressLine2 + ", shipToCity=" + shipToCity + ", shipToState="
				+ shipToState + ", shipToCountryCode=" + shipToCountryCode + ", shipToCountryName=" + shipToCountryName
				+ ", shipPostalCode=" + shipPostalCode + ", shipToContactPhone=" + shipToContactPhone + ", shipMethod="
				+ shipMethod + ", billToName=" + billToName + ", billToAddressLine1=" + billToAddressLine1
				+ ", billToAddressLine2=" + billToAddressLine2 + ", billToCity=" + billToCity + ", billToState="
				+ billToState + ", billToCountryCode=" + billToCountryCode + ", billToCountryName=" + billToCountryName
				+ ", billPostalCode=" + billPostalCode + ", billToContactPhone=" + billToContactPhone + ", packageId="
				+ packageId + ", trackingId=" + trackingId + ", barcode=" + barcode + ", trailorId=" + trailorId
				+ ", loadId=" + loadId + ", canManifest=" + canManifest + ", manifestId=" + manifestId
				+ ", carrierName=" + carrierName + ", localTimeZone=" + localTimeZone + ", pickupDate=" + pickupDate
				+ ", labelContent=" + labelContent + ", labelFormatType=" + labelFormatType + ", labelWidthValue="
				+ labelWidthValue + ", labelWidthUnit=" + labelWidthUnit + ", labelLengthValue=" + labelLengthValue
				+ ", labelLengthUnit=" + labelLengthUnit + ", packageLengthValue=" + packageLengthValue
				+ ", packageLengthUnit=" + packageLengthUnit + ", packageWidthValue=" + packageWidthValue
				+ ", packageWidthUnit=" + packageWidthUnit + ", packageHeightValue=" + packageHeightValue
				+ ", packageHeightUnit=" + packageHeightUnit + ", packageWeightValue=" + packageWeightValue
				+ ", packageWeightUnit=" + packageWeightUnit + ", braaPpType=" + braaPpType + ", braaPpTypeIdentifier="
				+ braaPpTypeIdentifier + ", braaPpQuantity=" + braaPpQuantity + ", orderStatus=" + orderStatus
				+ ", picklistNumber=" + picklistNumber + ", noOfItems=" + noOfItems + ", fulfilmentType="
				+ fulfilmentType + ", processInstanceId=" + processInstanceId + ", orderFilePath=" + orderFilePath
				+ ", shipLabelFilepath=" + shipLabelFilepath + ", createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", manifestDate=" + manifestDate + ", packedBy=" + packedBy + ", isAccepted="
				+ isAccepted + ", isCanceled=" + isCanceled + ", isAsnSend=" + isAsnSend + ", isOmsUpload="
				+ isOmsUpload + ", isMeasureDone=" + isMeasureDone + ", omsUploadErrorMsg=" + omsUploadErrorMsg
				+ ", batchId=" + batchId + ", refOrderId=" + refOrderId + ", ediShipmentItems=" + ediShipmentItems
				+ "]";
	}
}
