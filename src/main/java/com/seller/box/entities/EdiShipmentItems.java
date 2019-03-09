package com.seller.box.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EDI_SHIPMENT_ITEMS", catalog = "SELLER")
public class EdiShipmentItems implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "EDI_ITEM_ID")
	private Long ediItemId;
	@Column(name = "EDI_ORDER_ID", updatable = false, insertable = false)
	private Long ediOrderId;
	@Column(name = "EAN")
	private Long ean;
	@Column(name = "LINE_ITEM_SEQ")
	private int lineItemSeq;
	@Column(name = "WAREHOUSE_CODE")
	private String warehouseCode;
	@Column(name = "ITEM_ID")
	private String itemId;
	@Column(name = "FNSKU")
	private String fnsku;
	@Column(name = "SKU_CODE")
	private String skuCode;
	@Column(name = "PRODUCT_NAME")
	private String productName;
	@Column(name = "THUMBNAIL_URL")
	private String thumbnailUrl;
	@Column(name = "QUANTITY")
	private int quantity;
	@Column(name = "QNT_FROM_INV")
	private int qntFromInv;
	@Column(name = "QNT_FROM_VIR")
	private int qntFromVir;
	@Column(name = "CURRENCY_CODE")
	private String currencyCode;
	@Column(name = "CUSTOMER_PRICE")
	private double customerPrice;
	@Column(name = "LINE_ITEM_TOTAL")
	private double lineItemTotal;
	@Column(name = "BAY_NO")
	private String bayNo;
	@Column(name = "RACK_NO")
	private String rackNo;
	@Column(name = "GIFT_LABEL_CONTENT")
	private String giftLabelContent;
	@Column(name = "GIFT_WRAP_ID")
	private String giftWrapId;
	@Column(name = "SELLER_OF_RECORD_NAME")
	private String sellerOfRecordName;
	@Column(name = "SELLER_OF_RECORD_ID")
	private int sellerOfRecordId;
	@Column(name = "MERCHANT_ID")
	private String merchantId;
	@Column(name = "ORDER_ITEM_ID")
	private String orderItemId;
	@Column(name = "CUSTOMER_ORDER_ITEM_DETAIL_ID")
	private int customerOrderItemDetailId;
	@Column(name = "IS_CANCELED")
	private int isCanceled;
	@Column(name = "IS_ITEM_UPLOAD")
	private int isItemUpload;
	@Column(name = "IS_INV_DEDUCT")
	private int isInvDeduct;
	@Column(name = "INV_DEDUCT_ERROR_MSG")
	private String invDeductErrorMsg;
	@Column(name = "REF_ITEM_ID")
	private String refItemId;
	
	public Long getEdiItemId() {
		return ediItemId;
	}
	public void setEdiItemId(Long ediItemId) {
		this.ediItemId = ediItemId;
	}
	public Long getEdiOrderId() {
		return ediOrderId;
	}
	public void setEdiOrderId(Long ediOrderId) {
		this.ediOrderId = ediOrderId;
	}
	public Long getEan() {
		return ean;
	}
	public void setEan(Long ean) {
		this.ean = ean;
	}
	public int getLineItemSeq() {
		return lineItemSeq;
	}
	public void setLineItemSeq(int lineItemSeq) {
		this.lineItemSeq = lineItemSeq;
	}
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getFnsku() {
		return fnsku;
	}
	public void setFnsku(String fnsku) {
		this.fnsku = fnsku;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getQntFromInv() {
		return qntFromInv;
	}
	public void setQntFromInv(int qntFromInv) {
		this.qntFromInv = qntFromInv;
	}
	public int getQntFromVir() {
		return qntFromVir;
	}
	public void setQntFromVir(int qntFromVir) {
		this.qntFromVir = qntFromVir;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public double getCustomerPrice() {
		return customerPrice;
	}
	public void setCustomerPrice(double customerPrice) {
		this.customerPrice = customerPrice;
	}
	public double getLineItemTotal() {
		return lineItemTotal;
	}
	public void setLineItemTotal(double lineItemTotal) {
		this.lineItemTotal = lineItemTotal;
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
	public String getGiftLabelContent() {
		return giftLabelContent;
	}
	public void setGiftLabelContent(String giftLabelContent) {
		this.giftLabelContent = giftLabelContent;
	}
	public String getGiftWrapId() {
		return giftWrapId;
	}
	public void setGiftWrapId(String giftWrapId) {
		this.giftWrapId = giftWrapId;
	}
	public String getSellerOfRecordName() {
		return sellerOfRecordName;
	}
	public void setSellerOfRecordName(String sellerOfRecordName) {
		this.sellerOfRecordName = sellerOfRecordName;
	}
	public int getSellerOfRecordId() {
		return sellerOfRecordId;
	}
	public void setSellerOfRecordId(int sellerOfRecordId) {
		this.sellerOfRecordId = sellerOfRecordId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}
	public int getCustomerOrderItemDetailId() {
		return customerOrderItemDetailId;
	}
	public void setCustomerOrderItemDetailId(int customerOrderItemDetailId) {
		this.customerOrderItemDetailId = customerOrderItemDetailId;
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
	public int getIsInvDeduct() {
		return isInvDeduct;
	}
	public void setIsInvDeduct(int isInvDeduct) {
		this.isInvDeduct = isInvDeduct;
	}
	public String getInvDeductErrorMsg() {
		return invDeductErrorMsg;
	}
	public void setInvDeductErrorMsg(String invDeductErrorMsg) {
		this.invDeductErrorMsg = invDeductErrorMsg;
	}
	public String getRefItemId() {
		return refItemId;
	}
	public void setRefItemId(String refItemId) {
		this.refItemId = refItemId;
	}

}
