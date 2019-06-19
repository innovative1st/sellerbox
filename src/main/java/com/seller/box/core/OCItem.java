package com.seller.box.core;

public class OCItem {
	private String lineItemSequenceNumber;
    private String ItemId;
    private String SkuCode;
    private String ean;
    private String fnSku;
    private String productName;
    private String thumbnailUrl;
    private String itemType;
    private Integer quantityToCancel;
	public String getLineItemSequenceNumber() {
		return lineItemSequenceNumber;
	}
	public void setLineItemSequenceNumber(String lineItemSequenceNumber) {
		this.lineItemSequenceNumber = lineItemSequenceNumber;
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
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public Integer getQuantityToCancel() {
		return quantityToCancel;
	}
	public void setQuantityToCancel(Integer quantityToCancel) {
		this.quantityToCancel = quantityToCancel;
	}
	@Override
	public String toString() {
		return "OCItem [lineItemSequenceNumber=" + lineItemSequenceNumber + ", ItemId=" + ItemId + ", SkuCode="
				+ SkuCode + ", ean=" + ean + ", fnSku=" + fnSku + ", productName=" + productName + ", thumbnailUrl="
				+ thumbnailUrl + ", itemType=" + itemType + ", quantityToCancel=" + quantityToCancel + "]";
	}
}
