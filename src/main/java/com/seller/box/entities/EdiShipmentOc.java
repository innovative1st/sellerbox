package com.seller.box.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="EDI_SHIPMENT_OC", catalog = "SELLER")
public class EdiShipmentOc implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="OC_ID")
	private Long ocId;
	@Column(name="ERETAILOR_ID")
	private int eretailorId;
	@Column(name="CUSTOMER_ORDER_NUMBER")
	private String customerOrderNumber;
	@Column(name="PURCHASE_ORDER_NUMBER")
	private String purchaseOrderNumber;
	@Column(name="CANCEL_REQUEST_TYPE")
	private String cancelRequestType;
	@Column(name="WAREHOUSE_LOCATION_ID")
	private String warehouseLocationId;
	@Column(name="VENDOR_PARTY_TYPE")
	private String vendorPartyType;
	@Column(name="VENDOR_PARTY_ID")
	private String vendorPartyId;
	@Column(name="EDI_ORDER_ID")
	private Long ediOrderId;
	@Column(name="IS_CANCEL_DONE")
	private int isCancelDone;
	@Column(name="IS_IAN_DONE")
	private int isIanDone;
	@Column(name="IS_OCR_DONE")
	private int isOcrDone;
	@Column(name="OC_FILE_PATH")
	private String ocFilePath;
	@Column(name="RESPONSE_CONDITION")
	private String responseCondition;
	@Column(name="RESULT_CODE")
	private String resultCode;
	@Column(name="CREATED_BY")
	private String createdBy;
	@Column(name="CREATED_DATE")
	private Date createdDate;
	@Column(name="PROCESS_INSTANCE_ID")
	private String processInstanceId;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "OC_ID", nullable = false)
	private Set<EdiShipmentOcItems> ediShipmentOcItems;
	public Long getOcId() {
		return ocId;
	}
	public void setOcId(Long ocId) {
		this.ocId = ocId;
	}
	public int getEretailorId() {
		return eretailorId;
	}
	public void setEretailorId(int eretailorId) {
		this.eretailorId = eretailorId;
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
	public String getCancelRequestType() {
		return cancelRequestType;
	}
	public void setCancelRequestType(String cancelRequestType) {
		this.cancelRequestType = cancelRequestType;
	}
	public String getWarehouseLocationId() {
		return warehouseLocationId;
	}
	public void setWarehouseLocationId(String warehouseLocationId) {
		this.warehouseLocationId = warehouseLocationId;
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
	public Long getEdiOrderId() {
		return ediOrderId;
	}
	public void setEdiOrderId(Long ediOrderId) {
		this.ediOrderId = ediOrderId;
	}
	public int getIsCancelDone() {
		return isCancelDone;
	}
	public void setIsCancelDone(int isCancelDone) {
		this.isCancelDone = isCancelDone;
	}
	public int getIsIanDone() {
		return isIanDone;
	}
	public void setIsIanDone(int isIanDone) {
		this.isIanDone = isIanDone;
	}
	public int getIsOcrDone() {
		return isOcrDone;
	}
	public void setIsOcrDone(int isOcrDone) {
		this.isOcrDone = isOcrDone;
	}
	public String getOcFilePath() {
		return ocFilePath;
	}
	public void setOcFilePath(String ocFilePath) {
		this.ocFilePath = ocFilePath;
	}
	public String getResponseCondition() {
		return responseCondition;
	}
	public void setResponseCondition(String responseCondition) {
		this.responseCondition = responseCondition;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
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
	public Set<EdiShipmentOcItems> getEdiShipmentOcItems() {
		return ediShipmentOcItems;
	}
	public void setEdiShipmentOcItems(Set<EdiShipmentOcItems> ediShipmentOcItems) {
		this.ediShipmentOcItems = ediShipmentOcItems;
	}
	@Override
	public String toString() {
		return "EdiShipmentOc [ocId=" + ocId + ", eretailorId=" + eretailorId + ", customerOrderNumber="
				+ customerOrderNumber + ", purchaseOrderNumber=" + purchaseOrderNumber + ", cancelRequestType="
				+ cancelRequestType + ", warehouseLocationId=" + warehouseLocationId + ", vendorPartyType="
				+ vendorPartyType + ", vendorPartyId=" + vendorPartyId + ", ediOrderId=" + ediOrderId
				+ ", isCancelDone=" + isCancelDone + ", isIanDone=" + isIanDone + ", isOcrDone=" + isOcrDone
				+ ", ocFilePath=" + ocFilePath + ", responseCondition=" + responseCondition + ", resultCode="
				+ resultCode + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", processInstanceId="
				+ processInstanceId + ", ediShipmentOcItems=" + ediShipmentOcItems + "]";
	}
}