package com.seller.box.core;

import java.util.List;

public class OC {
    private String partyId;
    private String warehouseLocationId;
    private String purchaseOrderNumber;
    private String customerOrderNumber;
    private String cancelRequestType;
    private String ocFilePath;
    private List<OCItem> ocItems;
//    private String lineItemSequenceNumber;
//    private String itemId;
//    private String itemType;
//    private String quantity;

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setWarehouseLocationId(String warehouseLocationId) {
        this.warehouseLocationId = warehouseLocationId;
    }

    public String getWarehouseLocationId() {
        return warehouseLocationId;
    }

    public void setPurchaseOrderNumber(String purchaseOrderNumber) {
        this.purchaseOrderNumber = purchaseOrderNumber;
    }

    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }

    public void setCustomerOrderNumber(String customerOrderNumber) {
        this.customerOrderNumber = customerOrderNumber;
    }

    public String getCustomerOrderNumber() {
        return customerOrderNumber;
    }

    public void setCancelRequestType(String cancelRequestType) {
        this.cancelRequestType = cancelRequestType;
    }

    public String getCancelRequestType() {
        return cancelRequestType;
    }

//    public void setLineItemSequenceNumber(String lineItemSequenceNumber) {
//        this.lineItemSequenceNumber = lineItemSequenceNumber;
//    }
//
//    public String getLineItemSequenceNumber() {
//        return lineItemSequenceNumber;
//    }

//    public void setItemId(String itemId) {
//        this.itemId = itemId;
//    }
//
//    public String getItemId() {
//        return itemId;
//    }
//
//    public void setItemType(String itemType) {
//        this.itemType = itemType;
//    }
//
//    public String getItemType() {
//        return itemType;
//    }
//
//    public void setQuantity(String quantity) {
//        this.quantity = quantity;
//    }
//
//    public String getQuantity() {
//        return quantity;
//    }

    public void setOcItems(List<OCItem> ocItems) {
        this.ocItems = ocItems;
    }

    public String getOcFilePath() {
		return ocFilePath;
	}

	public void setOcFilePath(String ocFilePath) {
		this.ocFilePath = ocFilePath;
	}

	public List<OCItem> getOcItems() {
        return ocItems;
    }
}
