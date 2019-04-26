package com.seller.box.form;

public class EdiInventoryIanForm {
	private String requestId;
	private String warehouseCode;
	private int etailorId;
	private String skuCode;
	private String fnsku;
	private int quantity;
	private String adjustmentType;
	private String purchaseOrderNumber;
	private String createdBy;
	private String inventorySourceLocation;
	private String inventoryDestinationLocation;
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public int getEtailorId() {
		return etailorId;
	}
	public void setEtailorId(int etailorId) {
		this.etailorId = etailorId;
	}
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
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
	@Override
	public String toString() {
		return "EdiInventoryIanForm [requestId=" + requestId + ", warehouseCode=" + warehouseCode + ", etailorId="
				+ etailorId + ", skuCode=" + skuCode + ", fnsku=" + fnsku + ", quantity=" + quantity
				+ ", adjustmentType=" + adjustmentType + ", purchaseOrderNumber=" + purchaseOrderNumber + ", createdBy="
				+ createdBy + ", inventorySourceLocation=" + inventorySourceLocation + ", inventoryDestinationLocation="
				+ inventoryDestinationLocation + "]";
	}
}
