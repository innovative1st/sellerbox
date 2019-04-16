package com.seller.box.core;

public class CRN {
    private String inventoryLocation;
    private String itemType;
    private String itemId;
    private String returnCondition;
    private Integer quantity;
	private String customerOrderNumber;
    private String purchaseOrderNumber;
    private String requestDateTime;
    private String returnId;
    private String partyId;
    private String warehouseLocationId;
    private String crnFilePath;
    public String getInventoryLocation() {
		return inventoryLocation;
	}
	public void setInventoryLocation(String inventoryLocation) {
		this.inventoryLocation = inventoryLocation;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getReturnCondition() {
		return returnCondition;
	}
	public void setReturnCondition(String returnCondition) {
		this.returnCondition = returnCondition;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getCustomerOrderNumber() {
		return customerOrderNumber;
	}
	public void setCustomerOrderNumber(String customerOrderNumber) {
		this.customerOrderNumber = customerOrderNumber;
	}
	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}
	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}
	public String getRequestDateTime() {
		return requestDateTime;
	}
	public void setRequestDateTime(String requestDateTime) {
		this.requestDateTime = requestDateTime;
	}
	public String getReturnId() {
		return returnId;
	}
	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getWarehouseLocationId() {
		return warehouseLocationId;
	}
	public void setWarehouseLocationId(String warehouseLocationId) {
		this.warehouseLocationId = warehouseLocationId;
	}
	public String getCrnFilePath() {
		return crnFilePath;
	}
	public void setCrnFilePath(String crnFilePath) {
		this.crnFilePath = crnFilePath;
	}
}
