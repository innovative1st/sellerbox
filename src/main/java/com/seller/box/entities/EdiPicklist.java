package com.seller.box.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EDI_PICKLIST", catalog = "SELLER")
public class EdiPicklist  implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "PICKLIST_ID")
	private Long picklistId;
	@Column(name = "PICKLIST_NUMBER")
	private String picklistNumber;
	@Column(name = "WAREHOUSE_CODE")
	private String warehouseCode;
	@Column(name = "ERETAILOR_ID")
	private int etailorId;
	@Column(name = "CREATED_ON")
	private Date createdDate;
	@Column(name = "CREATED_BY")
	private String createdBy;
	@Column(name = "ASSIGNED_TO")
	private String assignedTo;
	@Column(name = "EXSD_AFTER")
	private Date exsdAfter;
	@Column(name = "EXSD_BEFORE")
	private Date exsdBefore;
	@Column(name = "PICKUP_DATE")
	private Date pickupDate;
	@Column(name = "IS_FASTTRACK")
	private String isFasttrack;
	@Column(name = "IS_GIFT_MESSAGE")
	private String isGiftMessage;
	@Column(name = "IS_GIFTWRAPE")
	private String isGiftWrape;
	@Column(name = "SINGLE_MULTI_TYPE")
	private String singleMultiType;
	@Column(name = "FULFILMENT_TYPE")
	private String fulfilmentType;
	@Column(name = "NO_OF_TOTAL_ORDER")
	private int noOfTotalOrder;
	@Column(name = "NO_OF_PACKED_ORDER")
	private int noOfPackedOrder;
	@Column(name = "NO_OF_CANCELLED_ORDER")
	private int noOfCancelledOrder;
	@Column(name = "NO_OF_SIDELINE_ORDER")
	private int noOfSidelineOrder;
	@Column(name = "STATUS")
	private String status;
	@Column(name = "IS_ASN")
	private int isASN;
	@Column(name = "ASN_STATUS")
	private String asnStatus;
	@Column(name = "IS_NOTIFY")
	private String isNotify;
	public Long getPicklistId() {
		return picklistId;
	}
	public void setPicklistId(Long picklistId) {
		this.picklistId = picklistId;
	}
	public String getPicklistNumber() {
		return picklistNumber;
	}
	public void setPicklistNumber(String picklistNumber) {
		this.picklistNumber = picklistNumber;
	}
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public int getEtailorId() {
		return etailorId;
	}
	public void setEtailorId(int etailorId) {
		this.etailorId = etailorId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	public Date getExsdAfter() {
		return exsdAfter;
	}
	public void setExsdAfter(Date exsdAfter) {
		this.exsdAfter = exsdAfter;
	}
	public Date getExsdBefore() {
		return exsdBefore;
	}
	public void setExsdBefore(Date exsdBefore) {
		this.exsdBefore = exsdBefore;
	}
	public Date getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}
	public String getIsFasttrack() {
		return isFasttrack;
	}
	public void setIsFasttrack(String isFasttrack) {
		this.isFasttrack = isFasttrack;
	}
	public String getIsGiftMessage() {
		return isGiftMessage;
	}
	public void setIsGiftMessage(String isGiftMessage) {
		this.isGiftMessage = isGiftMessage;
	}
	public String getIsGiftWrape() {
		return isGiftWrape;
	}
	public void setIsGiftWrape(String isGiftWrape) {
		this.isGiftWrape = isGiftWrape;
	}
	public String getSingleMultiType() {
		return singleMultiType;
	}
	public void setSingleMultiType(String singleMultiType) {
		this.singleMultiType = singleMultiType;
	}
	public String getFulfilmentType() {
		return fulfilmentType;
	}
	public void setFulfilmentType(String fulfilmentType) {
		this.fulfilmentType = fulfilmentType;
	}
	public int getNoOfTotalOrder() {
		return noOfTotalOrder;
	}
	public void setNoOfTotalOrder(int noOfTotalOrder) {
		this.noOfTotalOrder = noOfTotalOrder;
	}
	public int getNoOfPackedOrder() {
		return noOfPackedOrder;
	}
	public void setNoOfPackedOrder(int noOfPackedOrder) {
		this.noOfPackedOrder = noOfPackedOrder;
	}
	public int getNoOfCancelledOrder() {
		return noOfCancelledOrder;
	}
	public void setNoOfCancelledOrder(int noOfCancelledOrder) {
		this.noOfCancelledOrder = noOfCancelledOrder;
	}
	public int getNoOfSidelineOrder() {
		return noOfSidelineOrder;
	}
	public void setNoOfSidelineOrder(int noOfSidelineOrder) {
		this.noOfSidelineOrder = noOfSidelineOrder;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getIsASN() {
		return isASN;
	}
	public void setIsASN(int isASN) {
		this.isASN = isASN;
	}
	public String getAsnStatus() {
		return asnStatus;
	}
	public void setAsnStatus(String asnStatus) {
		this.asnStatus = asnStatus;
	}
	public String getIsNotify() {
		return isNotify;
	}
	public void setIsNotify(String isNotify) {
		this.isNotify = isNotify;
	}
}
