package com.seller.box.core;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;

public class OF {
    private String purchaseOrder;
    private String customerOrder;
    private String billToEntityId;
    private String warehouseLocationId;
    private Date ordereDate;
    private Date expectedShipDate;
    private String boxType;
    private Integer isGift;
    private Integer isCod;
    private Integer isPriorityShipment;
    private String paymentType;
    private String currencyCode; //Comes from ordertotal tag
    private BigDecimal taxRate;
    private BigDecimal taxAmount;
    private BigDecimal shipChargeAmount;
    private BigDecimal subTotal;
    private BigDecimal orderTotal;
    private BigDecimal balanceDue;
    private Integer isInsertsRequired;
    private String buyerName;
    private String buyerAttentionLine;
    private String buyerAddressLine1;
    private String buyerCity;
    private String buyerState;
    private String buyerCountryCode;
    private String buyerPostalCode;
    private Long orderSiteId;
    private String shipToName;
    private String shipToAddressLine1;
    private String shipToAddressLine2;
    private String shipToCity;
    private String shipToState;
    private String shipToCountryCode;
    private String shipToCountryName;
    private String shipPostalCode;
    private String shipToContactPhone;
    private String shipMethod;
    private String billToName;
    private String billToAddressLine1;
    private String billToAddressLine2;
    private String billToCity;
    private String billToState;
    private String billToCountryCode;
    private String billToCountryName;
    private String billPostalCode;
    private String billToContactPhone;
    private String orderFilePath;
    private List<OFItem> shipmentItems;
    private String braaPpType; 
    private String braaPpTypeIdentifier ;
    private Long braaPpQuantity ;

    public void setPurchaseOrder(String purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public String getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setCustomerOrder(String customerOrder) {
        this.customerOrder = customerOrder;
    }

    public String getCustomerOrder() {
        return customerOrder;
    }

    public void setBillToEntityId(String billToEntityId) {
        this.billToEntityId = billToEntityId;
    }

    public String getBillToEntityId() {
        return billToEntityId;
    }

    public void setWarehouseLocationId(String warehouseLocationId) {
        this.warehouseLocationId = warehouseLocationId;
    }

    public String getWarehouseLocationId() {
        return warehouseLocationId;
    }

    public void setOrdereDate(Date ordereDate) {
        this.ordereDate = ordereDate;
    }

    public Date getOrdereDate() {
        return ordereDate;
    }

    public void setExpectedShipDate(Date expectedShipDate) {
        this.expectedShipDate = expectedShipDate;
    }

    public Date getExpectedShipDate() {
        return expectedShipDate;
    }

    public void setBoxType(String boxType) {
        this.boxType = boxType;
    }

    public String getBoxType() {
        return boxType;
    }

    public void setIsGift(Integer isGift) {
        this.isGift = isGift;
    }

    public Integer getIsGift() {
        return isGift;
    }

    public void setIsCod(Integer isCod) {
        this.isCod = isCod;
    }

    public Integer getIsCod() {
        return isCod;
    }
    
    public void setIsPriorityShipment(Integer isPriorityShipment) {
        this.isPriorityShipment = isPriorityShipment;
    }

    public Integer getIsPriorityShipment() {
        return isPriorityShipment;
    }
    
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setShipChargeAmount(BigDecimal shipChargeAmount) {
        this.shipChargeAmount = shipChargeAmount;
    }

    public BigDecimal getShipChargeAmount() {
        return shipChargeAmount;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setOrderTotal(BigDecimal orderTotal) {
        this.orderTotal = orderTotal;
    }

    public BigDecimal getOrderTotal() {
        return orderTotal;
    }

    public void setBalanceDue(BigDecimal balanceDue) {
        this.balanceDue = balanceDue;
    }

    public BigDecimal getBalanceDue() {
        return balanceDue;
    }

    public void setIsInsertsRequired(Integer isInsertsRequired) {
        this.isInsertsRequired = isInsertsRequired;
    }

    public Integer getIsInsertsRequired() {
        return isInsertsRequired;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerAttentionLine(String buyerAttentionLine) {
        this.buyerAttentionLine = buyerAttentionLine;
    }

    public String getBuyerAttentionLine() {
        return buyerAttentionLine;
    }

    public void setBuyerAddressLine1(String buyerAddressLine1) {
        this.buyerAddressLine1 = buyerAddressLine1;
    }

    public String getBuyerAddressLine1() {
        return buyerAddressLine1;
    }

    public void setBuyerCity(String buyerCity) {
        this.buyerCity = buyerCity;
    }

    public String getBuyerCity() {
        return buyerCity;
    }

    public void setBuyerState(String buyerState) {
        this.buyerState = buyerState;
    }

    public String getBuyerState() {
        return buyerState;
    }

    public void setBuyerCountryCode(String buyerCountryCode) {
        this.buyerCountryCode = buyerCountryCode;
    }

    public String getBuyerCountryCode() {
        return buyerCountryCode;
    }

    public void setBuyerPostalCode(String buyerPostalCode) {
        this.buyerPostalCode = buyerPostalCode;
    }

    public String getBuyerPostalCode() {
        return buyerPostalCode;
    }

    public void setOrderSiteId(Long orderSiteId) {
        this.orderSiteId = orderSiteId;
    }

    public Long getOrderSiteId() {
        return orderSiteId;
    }

    public void setShipToName(String shipToName) {
        this.shipToName = shipToName;
    }

    public String getShipToName() {
        return shipToName;
    }

    public void setShipToAddressLine1(String shipToAddressLine1) {
        this.shipToAddressLine1 = shipToAddressLine1;
    }

    public String getShipToAddressLine1() {
        return shipToAddressLine1;
    }

    public void setShipToAddressLine2(String shipToAddressLine2) {
        this.shipToAddressLine2 = shipToAddressLine2;
    }

    public String getShipToAddressLine2() {
        return shipToAddressLine2;
    }

    public void setShipToCity(String shipToCity) {
        this.shipToCity = shipToCity;
    }

    public String getShipToCity() {
        return shipToCity;
    }

    public void setShipToState(String shipToState) {
        this.shipToState = shipToState;
    }

    public String getShipToState() {
        return shipToState;
    }

    public void setShipToCountryCode(String shipToCountryCode) {
        this.shipToCountryCode = shipToCountryCode;
    }

    public String getShipToCountryCode() {
        return shipToCountryCode;
    }

    public void setShipToCountryName(String shipToCountryName) {
        this.shipToCountryName = shipToCountryName;
    }

    public String getShipToCountryName() {
        return shipToCountryName;
    }

    public void setShipPostalCode(String shipPostalCode) {
        this.shipPostalCode = shipPostalCode;
    }

    public String getShipPostalCode() {
        return shipPostalCode;
    }

    public void setShipToContactPhone(String shipToContactPhone) {
        this.shipToContactPhone = shipToContactPhone;
    }

    public String getShipToContactPhone() {
        return shipToContactPhone;
    }

    public void setShipMethod(String shipMethod) {
        this.shipMethod = shipMethod;
    }

    public String getShipMethod() {
        return shipMethod;
    }

    public void setBillToName(String billToName) {
        this.billToName = billToName;
    }

    public String getBillToName() {
        return billToName;
    }

    public void setBillToAddressLine1(String billToAddressLine1) {
        this.billToAddressLine1 = billToAddressLine1;
    }

    public String getBillToAddressLine1() {
        return billToAddressLine1;
    }

    public void setBillToAddressLine2(String billToAddressLine2) {
        this.billToAddressLine2 = billToAddressLine2;
    }

    public String getBillToAddressLine2() {
        return billToAddressLine2;
    }

    public void setBillToCity(String billToCity) {
        this.billToCity = billToCity;
    }

    public String getBillToCity() {
        return billToCity;
    }

    public void setBillToState(String billToState) {
        this.billToState = billToState;
    }

    public String getBillToState() {
        return billToState;
    }

    public void setBillToCountryCode(String billToCountryCode) {
        this.billToCountryCode = billToCountryCode;
    }

    public String getBillToCountryCode() {
        return billToCountryCode;
    }

    public void setBillToCountryName(String billToCountryName) {
        this.billToCountryName = billToCountryName;
    }

    public String getBillToCountryName() {
        return billToCountryName;
    }

    public void setBillPostalCode(String billPostalCode) {
        this.billPostalCode = billPostalCode;
    }

    public String getBillPostalCode() {
        return billPostalCode;
    }

    public void setBillToContactPhone(String billToContactPhone) {
        this.billToContactPhone = billToContactPhone;
    }

    public String getBillToContactPhone() {
        return billToContactPhone;
    }

    public void setOrderFilePath(String orderFilePath) {
        this.orderFilePath = orderFilePath;
    }

    public String getOrderFilePath() {
        return orderFilePath;
    }

    public void setShipmentItems(List<OFItem> shipmentItems) {
        this.shipmentItems = shipmentItems;
    }

    public List<OFItem> getShipmentItems() {
        return shipmentItems;
    }

    @Override
    public String toString() {
        return "Shipment [purchaseOrder=" + purchaseOrder +
            ", \ncustomerOrder=" + customerOrder + ", billToEntityId=" +
            billToEntityId + ",\n warehouseLocationId=" + warehouseLocationId +
            ", ordereDate=" + ordereDate + ",\n expectedShipDate=" +
            expectedShipDate + ",\n boxType=" + boxType + ",\n isGift=" + isGift + ",\n isCod=" + isCod +
            ",\n paymentType=" + paymentType + ",\n currencyCode=" + currencyCode +
            ",\n taxRate=" + taxRate + ",\n taxAmount=" + taxAmount +
            ",\n shipChargeAmount=" + shipChargeAmount + ",\n subTotal=" +
            subTotal + ",\n orderTotal=" + orderTotal + ",\n balanceDue=" +
            balanceDue + ",\n isInsertsRequired=" + isInsertsRequired +
            ",\n buyerName=" + buyerName + ",\n buyerAttentionLine=" +
            buyerAttentionLine + ",\n buyerAddressLine1=" + buyerAddressLine1 +
            ",\n buyerCity=" + buyerCity + ",\n buyerState=" + buyerState +
            ",\n buyerCountryCode=" + buyerCountryCode + ",\n buyerPostalCode=" +
            buyerPostalCode + ",\n orderSiteId=" + orderSiteId +
            ",\n shipToName=" + shipToName + ",\n shipToAddressLine1=" +
            shipToAddressLine1 + ",\n shipToAddressLine2=" + shipToAddressLine2 +
            ",\n shipToCity=" + shipToCity + ",\n shipToState=" + shipToState +
            ",\n shipToCountryCode=" + shipToCountryCode +
            ",\n shipToCountryName=" + shipToCountryName + ",\n shipPostalCode=" +
            shipPostalCode + ",\n shipToContactPhone=" + shipToContactPhone +
            ",\n shipMethod=" + shipMethod + ",\n billToName=" + billToName +
            ",\n billToAddressLine1=" + billToAddressLine1 +
            ",\n billToAddressLine2=" + billToAddressLine2 + ",\n billToCity=" +
            billToCity + ",\n billToState=" + billToState +
            ",\n billToCountryCode=" + billToCountryCode +
            ",\n billToCountryName=" + billToCountryName + ",\n billPostalCode=" +
            billPostalCode + ",\n billToContactPhone=" + billToContactPhone +
            ",\n orderFilePath=" + orderFilePath + ",\n shipmentItems=" +
            shipmentItems + ",\n braaPpType=" + braaPpType + ",\n braaPpTypeIdentifier=" + braaPpTypeIdentifier 
            + ",\n braaPpQuantity=" + braaPpQuantity + "]";
    }

    public void setBraaPpType(String braaPpType) {
        this.braaPpType = braaPpType;
    }

    public String getBraaPpType() {
        return braaPpType;
    }

    public void setBraaPpTypeIdentifier(String braaPpTypeIdentifier) {
        this.braaPpTypeIdentifier = braaPpTypeIdentifier;
    }

    public String getBraaPpTypeIdentifier() {
        return braaPpTypeIdentifier;
    }

    public void setBraaPpQuantity(Long braaPpQuantity) {
        this.braaPpQuantity = braaPpQuantity;
    }

    public Long getBraaPpQuantity() {
        return braaPpQuantity;
    }
}
