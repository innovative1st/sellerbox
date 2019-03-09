package com.seller.box.form;

import java.io.Serializable;

public class ShipmentItem implements Serializable{
	private static final long serialVersionUID = 1L;
    private Long itemSeq;
    private String skuCode;
    private Long ean;
    private String asin;
    private String fnsku;
    private String title;
    private int quantity;
    private int quantityPacked;
    private String image;
    private String customerOrderItemID;
    private String customerOrderItemDetailID;
    private String bayNo;
    private String rackNo;
    private int qntFromInv;
    private int qntFromProd;
    private boolean isGift;
    private boolean isGiftWrap;
    private String giftLabelContent;
	public Long getItemSeq() {
		return itemSeq;
	}
	public void setItemSeq(Long itemSeq) {
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
	public String getAsin() {
		return asin;
	}
	public void setAsin(String asin) {
		this.asin = asin;
	}
	public String getFnsku() {
		return fnsku;
	}
	public void setFnsku(String fnsku) {
		this.fnsku = fnsku;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
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
	public boolean isGift() {
		return isGift;
	}
	public void setGift(boolean isGift) {
		this.isGift = isGift;
	}
	public boolean isGiftWrap() {
		return isGiftWrap;
	}
	public void setGiftWrap(boolean isGiftWrap) {
		this.isGiftWrap = isGiftWrap;
	}
	public String getGiftLabelContent() {
		return giftLabelContent;
	}
	public void setGiftLabelContent(String giftLabelContent) {
		this.giftLabelContent = giftLabelContent;
	}
}