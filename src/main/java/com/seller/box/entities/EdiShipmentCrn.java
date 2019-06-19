package com.seller.box.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="EDI_SHIPMENT_CRN")
public class EdiShipmentCrn implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="CRN_ID")
	private Long crnId;
	@Column(name="ERETAILOR_ID")
	private int eretailorId;
	@Column(name="ERETAILOR_NAME")
	private String eretailorName;
	@Column(name="CUSTOMER_ORDER_NUMBER")
	private String customerOrderNumber;
	@Column(name="PURCHASE_ORDER_NUMBER")
	private String purchaseOrderNumber;
	@Column(name="ITEM_ID")
	private String itemId;
	@Column(name="ITEM_TYPE")
	private String itemType;
	@Column(name="SKU_CODE")
	private String skuCode;
	@Column(name="FNSKU")
	private String fnSku;
	@Column(name="EAN")
	private Long ean;
	@Column(name="PRODUCT_NAME")
	private String productName;
	@Column(name="THUMBNAIL_URL")
	private String thumbnailUrl;
	@Column(name="QUANTITY")
	private int quantity;
	@Column(name="WAREHOUSE_LOCATION_ID")
	private String warehouseLocationId;
	@Column(name="BAY_NO")
	private String bayNo;
	@Column(name="RACK_NO")
	private String rackNo;
	@Column(name="VENDOR_PARTY_TYPE")
	private String vendorPartyType;
	@Column(name="VENDOR_PARTY_ID")
	private String vendorPartyId;
	@Column(name="RETURN_REQUEST_DATE")
	private Date returnRequestDate;
	@Column(name="RETURN_CONDITION")
	private String returnCondition;
	@Column(name="ITEM_LOCATION")
	private String itemLocation;
	@Column(name="RETURN_ID")
	private String returnId;
	@Column(name="IS_OMS_NOTIFY")
	private int isOmsNotify;
	@Column(name="IS_EDI_RETURNED")
	private int isEdiReturn;
	@Column(name="CREATED_BY")
	private String createdBy;
	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date createdDate;
	@Column(name="PROCESS_INSTANCE_ID")
	private String processInstanceId;
	@Column(name="CRN_FILE_PATH")
	private String crnFilePath;
	@Column(name="IAN_ID")
	private long ianId;
	@Column(name="REF_INWARD_ID")
	private String refInwardId;
	public Long getCrnId() {
		return crnId;
	}
	public void setCrnId(Long crnId) {
		this.crnId = crnId;
	}
	public int getEretailorId() {
		return eretailorId;
	}
	public void setEretailorId(int eretailorId) {
		this.eretailorId = eretailorId;
	}
	public String getEretailorName() {
		return eretailorName;
	}
	public void setEretailorName(String eretailorName) {
		this.eretailorName = eretailorName;
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
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getFnSku() {
		return fnSku;
	}
	public void setFnSku(String fnSku) {
		this.fnSku = fnSku;
	}
	public Long getEan() {
		return ean;
	}
	public void setEan(Long ean) {
		this.ean = ean;
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
	public String getWarehouseLocationId() {
		return warehouseLocationId;
	}
	public void setWarehouseLocationId(String warehouseLocationId) {
		this.warehouseLocationId = warehouseLocationId;
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
	public String getVendorPartyType() {
		return vendorPartyType;
	}
	public void setVendorPartyType(String vendorPartyType) {
		this.vendorPartyType = vendorPartyType;
	}
	public String getVendorPartyId() {
		return vendorPartyId;
	}
	public void setVendorPartyId(String vendorPartyId) {
		this.vendorPartyId = vendorPartyId;
	}
	public Date getReturnRequestDate() {
		return returnRequestDate;
	}
	public void setReturnRequestDate(Date returnRequestDate) {
		this.returnRequestDate = returnRequestDate;
	}
	public String getReturnCondition() {
		return returnCondition;
	}
	public void setReturnCondition(String returnCondition) {
		this.returnCondition = returnCondition;
	}
	public String getItemLocation() {
		return itemLocation;
	}
	public void setItemLocation(String itemLocation) {
		this.itemLocation = itemLocation;
	}
	public String getReturnId() {
		return returnId;
	}
	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}
	public int getIsOmsNotify() {
		return isOmsNotify;
	}
	public void setIsOmsNotify(int isOmsNotify) {
		this.isOmsNotify = isOmsNotify;
	}
	public int getIsEdiReturn() {
		return isEdiReturn;
	}
	public void setIsEdiReturn(int isEdiReturn) {
		this.isEdiReturn = isEdiReturn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getCrnFilePath() {
		return crnFilePath;
	}
	public void setCrnFilePath(String crnFilePath) {
		this.crnFilePath = crnFilePath;
	}
	public long getIanId() {
		return ianId;
	}
	public void setIanId(long ianId) {
		this.ianId = ianId;
	}
	public String getRefInwardId() {
		return refInwardId;
	}
	public void setRefInwardId(String refInwardId) {
		this.refInwardId = refInwardId;
	}
	@Override
	public String toString() {
		return "EdiShipmentCrn [crnId=" + crnId + ", eretailorId=" + eretailorId + ", eretailorName=" + eretailorName
				+ ", customerOrderNumber=" + customerOrderNumber + ", purchaseOrderNumber=" + purchaseOrderNumber
				+ ", itemId=" + itemId + ", itemType=" + itemType + ", skuCode=" + skuCode + ", fnSku=" + fnSku
				+ ", ean=" + ean + ", productName=" + productName + ", thumbnailUrl=" + thumbnailUrl + ", quantity="
				+ quantity + ", warehouseLocationId=" + warehouseLocationId + ", bayNo=" + bayNo + ", rackNo=" + rackNo
				+ ", vendorPartyType=" + vendorPartyType + ", vendorPartyId=" + vendorPartyId + ", returnRequestDate="
				+ returnRequestDate + ", returnCondition=" + returnCondition + ", itemLocation=" + itemLocation
				+ ", returnId=" + returnId + ", isOmsNotify=" + isOmsNotify + ", isEdiReturn=" + isEdiReturn
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", processInstanceId="
				+ processInstanceId + ", crnFilePath=" + crnFilePath + ", ianId=" + ianId + ", refInwardId="
				+ refInwardId + "]";
	}
}