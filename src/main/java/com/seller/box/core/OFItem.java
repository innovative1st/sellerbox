package com.seller.box.core;

import java.math.BigDecimal;

public class OFItem {
	private Long LineItemSeq;
    private String ItemId;
    private String SkuCode;
    private String ean;
    private String fnSku;
    private String productName;
    private String thumbnailUrl;
    private Integer quantity;
    private String currencyCode;
    private BigDecimal customerPrice;
    private BigDecimal lineItemTotal;
    private String sellerOfRecordName;
    private Long sellerOfRecordId;
    private String merchantId;
    private String giftMessage;
    private String giftLabelContent;
    private String  giftWrapId;
    private String customerOrderItemID;
    private String customerOrderItemDetailID;
	public Long getLineItemSeq() {
		return LineItemSeq;
	}
	public void setLineItemSeq(Long lineItemSeq) {
		LineItemSeq = lineItemSeq;
	}
	public String getItemId() {
		return ItemId;
	}
	public void setItemId(String itemId) {
		ItemId = itemId;
	}
	public String getSkuCode() {
		return SkuCode;
	}
	public void setSkuCode(String skuCode) {
		SkuCode = skuCode;
	}
	public String getEan() {
		return ean;
	}
	public void setEan(String ean) {
		this.ean = ean;
	}
	public String getFnSku() {
		return fnSku;
	}
	public void setFnSku(String fnSku) {
		this.fnSku = fnSku;
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
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public BigDecimal getCustomerPrice() {
		return customerPrice;
	}
	public void setCustomerPrice(BigDecimal customerPrice) {
		this.customerPrice = customerPrice;
	}
	public BigDecimal getLineItemTotal() {
		return lineItemTotal;
	}
	public void setLineItemTotal(BigDecimal lineItemTotal) {
		this.lineItemTotal = lineItemTotal;
	}
	public String getSellerOfRecordName() {
		return sellerOfRecordName;
	}
	public void setSellerOfRecordName(String sellerOfRecordName) {
		this.sellerOfRecordName = sellerOfRecordName;
	}
	public Long getSellerOfRecordId() {
		return sellerOfRecordId;
	}
	public void setSellerOfRecordId(Long sellerOfRecordId) {
		this.sellerOfRecordId = sellerOfRecordId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getGiftMessage() {
		return giftMessage;
	}
	public void setGiftMessage(String giftMessage) {
		this.giftMessage = giftMessage;
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
	public String getCustomerOrderItemID() {
		return customerOrderItemID;
	}
	public void setCustomerOrderItemID(String customerOrderItemID) {
		this.customerOrderItemID = customerOrderItemID;
	}
	public String getCustomerOrderItemDetailID() {
		return customerOrderItemDetailID;
	}
	public void setCustomerOrderItemDetailID(String customerOrderItemDetailID) {
		this.customerOrderItemDetailID = customerOrderItemDetailID;
	}

	@Override
	public String toString() {
		return "OFItem [LineItemSeq=" + LineItemSeq + ", ItemId=" + ItemId + ", SkuCode=" + SkuCode + ", ean=" + ean
				+ ", fnSku=" + fnSku + ", productName=" + productName + ", thumbnailUrl=" + thumbnailUrl + ", quantity="
				+ quantity + ", currencyCode=" + currencyCode + ", customerPrice=" + customerPrice + ", lineItemTotal="
				+ lineItemTotal + ", sellerOfRecordName=" + sellerOfRecordName + ", sellerOfRecordId="
				+ sellerOfRecordId + ", merchantId=" + merchantId + ", giftMessage=" + giftMessage
				+ ", giftLabelContent=" + giftLabelContent + ", giftWrapId=" + giftWrapId + ", customerOrderItemID="
				+ customerOrderItemID + ", customerOrderItemDetailID=" + customerOrderItemDetailID
				+ ", getLineItemSeq()=" + getLineItemSeq() + ", getItemId()=" + getItemId() + ", getSkuCode()="
				+ getSkuCode() + ", getEan()=" + getEan() + ", getFnSku()=" + getFnSku() + ", getProductName()="
				+ getProductName() + ", getThumbnailUrl()=" + getThumbnailUrl() + ", getQuantity()=" + getQuantity()
				+ ", getCurrencyCode()=" + getCurrencyCode() + ", getCustomerPrice()=" + getCustomerPrice()
				+ ", getLineItemTotal()=" + getLineItemTotal() + ", getSellerOfRecordName()=" + getSellerOfRecordName()
				+ ", getSellerOfRecordId()=" + getSellerOfRecordId() + ", getMerchantId()=" + getMerchantId()
				+ ", getGiftMessage()=" + getGiftMessage() + ", getGiftLabelContent()=" + getGiftLabelContent()
				+ ", getGiftWrapId()=" + getGiftWrapId() + ", getCustomerOrderItemID()=" + getCustomerOrderItemID()
				+ ", getCustomerOrderItemDetailID()=" + getCustomerOrderItemDetailID() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
}
