package com.seller.box.form;

public class EdiInventoryIanForm {
	private String locationCode;
	private int etailorId;
	private String skuCode;
	private String fnsku;
	private int quantity;
	private String adjustmentType;
	private String purchaseOrderNumber;
	private String createdBy;
	private String inventorySourceLocation;
	private String inventoryDestinationLocation;
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public int getEtailorId() {
		return etailorId;
	}
	public void setEtailorId(int etailorId) {
		this.etailorId = etailorId;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getFnsku() {
		return fnsku;
	}
	public void setFnsku(String fnsku) {
		this.fnsku = fnsku;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getAdjustmentType() {
		return adjustmentType;
	}
	public void setAdjustmentType(String adjustmentType) {
		this.adjustmentType = adjustmentType;
	}
	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}
	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getInventorySourceLocation() {
		return inventorySourceLocation;
	}
	public void setInventorySourceLocation(String inventorySourceLocation) {
		this.inventorySourceLocation = inventorySourceLocation;
	}
	public String getInventoryDestinationLocation() {
		return inventoryDestinationLocation;
	}
	public void setInventoryDestinationLocation(String inventoryDestinationLocation) {
		this.inventoryDestinationLocation = inventoryDestinationLocation;
	}
}
