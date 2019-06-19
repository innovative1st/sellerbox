package com.seller.box.form;

import java.io.Serializable;

public class ShipmentItem implements Serializable{
	private static final long serialVersionUID = 1L;
    private int itemSeq;
    private String skuCode;
    private Long ean;
    private String itemId;
    private String fnsku;
    private String productName;
    private int quantity;
    private int quantityPacked;
    private String thumbnailUrl;
    private String customerOrderItemID;
    private String customerOrderItemDetailID;
    private String bayNo;
    private String rackNo;
    private int qntFromInv;
    private int qntFromProd;
    private int isGift;
    private int isGiftWrap;
    private String giftLabelContent;
	public int getItemSeq() {
		return itemSeq;
	}
	public void setItemSeq(int itemSeq) {
		this.itemSeq = itemSeq;
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
	public int getQuantityPacked() {
		return quantityPacked;
	}
	public void setQuantityPacked(int quantityPacked) {
		this.quantityPacked = quantityPacked;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
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
	public int getQntFromInv() {
		return qntFromInv;
	}
	public void setQntFromInv(int qntFromInv) {
		this.qntFromInv = qntFromInv;
	}
	public int getQntFromProd() {
		return qntFromProd;
	}
	public void setQntFromProd(int qntFromProd) {
		this.qntFromProd = qntFromProd;
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
	public String getGiftLabelContent() {
		return giftLabelContent;
	}
	public void setGiftLabelContent(String giftLabelContent) {
		this.giftLabelContent = giftLabelContent;
	}
}