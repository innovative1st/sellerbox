package com.seller.box.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ShipmentHdrWithItem implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "EDI_ORDER_ID")
	private Long ediOrderId;
	@Column(name = "ERETAILOR_ID")
	private int etailorId;
	@Column(name = "PURCHASE_ORDER_NUMBER")
	private String shipmentId;
	@Column(name = "CUSTOMER_ORDER_NUMBER")
	private String orderId;
	@Column(name = "BILL_TO_ENTITY_ID")
	private String billToEntityId;
	@Column(name = "WAREHOUSE_CODE")
	private String warehouseCode;
	@Column(name = "ORDERE_DATE")
	private Date orderDate;
	@Column(name = "EXPECTED_SHIP_DATE")
	private Date exSdDate;
	@Column(name = "ORDER_RECEIVE_DATE")
	private Date orderReceiveDate;
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
	@Column(name = "PAYMENT_TYPE")
	private String paymentType;
	@Column(name = "SHIP_CHARGE_AMOUNT")
	private double shipChargeAmount;
	@Column(name = "ORDER_TOTAL")
	private double orderTotal;
	@Column(name = "BALANCE_DUE")
	private double balanceDue;
	@Column(name = "ORDER_SITE_ID")
	private int orderSiteId;
	@Column(name = "BATCH_ID")
	private String batchId;
	@Column(name = "ORDER_STATUS")
	private int orderStatus;
	@Column(name = "NO_OF_ITEMS")
	private int noOfItems;
	@Column(name = "FULFILMENT_TYPE")
	private String fulfilmentType;
	@Column(name = "LINE_ITEM_SEQ")
	private int lineItemSeq;
	@Column(name = "ITEM_ID")
	private String itemId;
	@Column(name = "SKU_CODE")
	private String skuCode;
	@Column(name = "EAN")
	private Long ean;
	@Column(name = "PRODUCT_NAME")
	private String productName;
	@Column(name = "QUANTITY")
	private int quantity;
	@Column(name = "QNT_FROM_INV")
	private int quantityFromInv;
	@Column(name = "QNT_FROM_VIR")
	private int quantityFromVir;
	@Column(name = "BAY_NO")
	private String bayNo;
	@Column(name = "RACK_NO")
	private String rackNo;
	@Column(name = "THUMBNAIL_URL")
	private String thumbnailUrl;
	@Column(name = "IS_CANCELED")
	private int isCanceled;
	@Column(name = "IS_ITEM_UPLOAD")
	private int isItemUpload;
	@Column(name = "REF_ORDER_ID")
	private String refOrderId;
	@Column(name = "IS_OMS_UPLOAD")
	private int isOmsUpload;
	@Column(name = "OMS_UPLOAD_ERROR_MSG")
	private String omsUploadErrorMsg;
	
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
	public String getBillToEntityId() {
		return billToEntityId;
	}
	public void setBillToEntityId(String billToEntityId) {
		this.billToEntityId = billToEntityId;
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
	public Date getExSdDate() {
		return exSdDate;
	}
	public void setExSdDate(Date exSdDate) {
		this.exSdDate = exSdDate;
	}
	public Date getOrderReceiveDate() {
		return orderReceiveDate;
	}
	public void setOrderReceiveDate(Date orderReceiveDate) {
		this.orderReceiveDate = orderReceiveDate;
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
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public double getShipChargeAmount() {
		return shipChargeAmount;
	}
	public void setShipChargeAmount(double shipChargeAmount) {
		this.shipChargeAmount = shipChargeAmount;
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
	public int getOrderSiteId() {
		return orderSiteId;
	}
	public void setOrderSiteId(int orderSiteId) {
		this.orderSiteId = orderSiteId;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
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
	public int getLineItemSeq() {
		return lineItemSeq;
	}
	public void setLineItemSeq(int lineItemSeq) {
		this.lineItemSeq = lineItemSeq;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public Long getEan() {
		return ean;
	}
	public void setEan(Long ean) {
		this.ean = ean;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getQuantityFromInv() {
		return quantityFromInv;
	}
	public void setQuantityFromInv(int quantityFromInv) {
		this.quantityFromInv = quantityFromInv;
	}
	public int getQuantityFromVir() {
		return quantityFromVir;
	}
	public void setQuantityFromVir(int quantityFromVir) {
		this.quantityFromVir = quantityFromVir;
	}
	public String getBayNo() {
		return bayNo;
	}
	public void setBayNo(String bayNo) {
		this.bayNo = bayNo;
	}
	public String getRackNo() {
		return rackNo;
	}
	public void setRackNo(String rackNo) {
		this.rackNo = rackNo;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public int getIsCanceled() {
		return isCanceled;
	}
	public void setIsCanceled(int isCanceled) {
		this.isCanceled = isCanceled;
	}
	public int getIsItemUpload() {
		return isItemUpload;
	}
	public void setIsItemUpload(int isItemUpload) {
		this.isItemUpload = isItemUpload;
	}
	public String getRefOrderId() {
		return refOrderId;
	}
	public void setRefOrderId(String refOrderId) {
		this.refOrderId = refOrderId;
	}
	public int getIsOmsUpload() {
		return isOmsUpload;
	}
	public void setIsOmsUpload(int isOmsUpload) {
		this.isOmsUpload = isOmsUpload;
	}
	public String getOmsUploadErrorMsg() {
		return omsUploadErrorMsg;
	}
	public void setOmsUploadErrorMsg(String omsUploadErrorMsg) {
		this.omsUploadErrorMsg = omsUploadErrorMsg;
	}
}
