package com.seller.box.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "EDI_INVENTORY_IAN", catalog = "INVENTORY")
public class EdiInventoryIan implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "IAN_ID")
	private Long ianId;
	@Column(name = "ETAILOR_ID")
	private int etailorId;
	@Column(name = "LOCATION_CODE")
	private String locationCode;
	@Column(name = "TRANSMISSION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date transmissionDate;
	@Column(name = "TRANS_CREATION_DATE")
	private String transCreationDate;
	@Column(name = "TRANS_IS_TEST")
	private String transIsTest;
	@Column(name = "TRANS_CONTROL_NUMBER")
	private String transControlNumber;
	@Column(name = "MESSAGE_COUNT")
	private int messageCount;
	@Column(name = "TRANS_STRUCTURE_VERSION")
	private String transStructureVersion;
	@Column(name = "TRANS_RECEIVING_PARTY_ID")
	private String transReceivingPartyId;
	@Column(name = "TRANS_SENDING_PARTY_ID")
	private String transSendingPartyId;
	@Column(name = "MESSAGE_TYPE")
	private String messageType;
	@Column(name = "MESSAGE_IS_TEST")
	private String messageIsTest;
	@Column(name = "MESSAGE_RECEIVING_PARTY_ID")
	private String messageReceivingPartyId;
	@Column(name = "MESSAGE_SENDING_PARTY_ID")
	private String messageSendingPartyId;
	@Column(name = "MESSAGE_STRUCTURE_VERSION")
	private String messageStructureVersion;
	@Column(name = "MESSAGE_CREATION_DATE")
	private String messageCreationDate;
	@Column(name = "MESSAGE_CONTROL_NUMBER")
	private String messageControlNumber;
	@Column(name = "ADJUSTMENT_TYPE")
	private String adjustmentType;
	@Column(name = "ITEM_TYPE")
	private String itemType;
	@Column(name = "ITEM_ID")
	private String itemId;
	@Column(name = "SKU_CODE")
	private String skuCode;
	@Column(name = "QUANTITY")
	private int quantity;
	@Column(name = "UNIT_OF_MEASURE")
	private String unitOfMeasure;
	@Column(name = "VENDOR_PARTY_TYPE")
	private String vendorPartyType;
	@Column(name = "VENDOR_PARTY_ID")
	private String vendorPartyId;
	@Column(name = "ADJUSTMENT_CONTROL_ID")
	private String adjustmentControlId;
	@Column(name = "INVENTORY_EFFECTIVE_DATE_TIME")
	private String inventoryEffectiveDateTime;
	@Column(name = "PURCHASE_ORDER_NUMBER")
	private String purchaseOrderNumber;
	@Column(name = "INVENTORY_SOURCE_LOCATION")
	private String inventorySourceLocation;
	@Column(name = "INVENTORY_DESTINATION_LOCATION")
	private String inventoryDestinationLocation;
	@Column(name = "IAN_FILE_PATH")
	private String ianFilepath;
	@Column(name = "PROCESS_INSTANCE_ID")
	private String processInstanceId;
	@Column(name = "MESSAGE_ERROR")
	private String messageError;
	@Column(name = "RUN_STATUS")
	private int runStatus;
	@Column(name = "TXN_STATUS")
	private String txnStatus;
	@Column(name = "IS_INV_UPDATED")
	private int isIanUpdated;
	@Column(name = "INV_UPLOAD_ERROR_MSG")
	private String invUpdateErrorMsg;
	@Column(name = "CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	@Column(name = "CREATED_BY")
	private String createdBy;
	@Column(name = "REQUEST_ID")
	private String requestId;
	
	public Long getIanId() {
		return ianId;
	}
	public void setIanId(Long ianId) {
		this.ianId = ianId;
	}
	public int getEtailorId() {
		return etailorId;
	}
	public void setEtailorId(int etailorId) {
		this.etailorId = etailorId;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public Date getTransmissionDate() {
		return transmissionDate;
	}
	public void setTransmissionDate(Date transmissionDate) {
		this.transmissionDate = transmissionDate;
	}
	public String getTransCreationDate() {
		return transCreationDate;
	}
	public void setTransCreationDate(String transCreationDate) {
		this.transCreationDate = transCreationDate;
	}
	public String getTransIsTest() {
		return transIsTest;
	}
	public void setTransIsTest(String transIsTest) {
		this.transIsTest = transIsTest;
	}
	public String getTransControlNumber() {
		return transControlNumber;
	}
	public void setTransControlNumber(String transControlNumber) {
		this.transControlNumber = transControlNumber;
	}
	public int getMessageCount() {
		return messageCount;
	}
	public void setMessageCount(int messageCount) {
		this.messageCount = messageCount;
	}
	public String getTransStructureVersion() {
		return transStructureVersion;
	}
	public void setTransStructureVersion(String transStructureVersion) {
		this.transStructureVersion = transStructureVersion;
	}
	public String getTransReceivingPartyId() {
		return transReceivingPartyId;
	}
	public void setTransReceivingPartyId(String transReceivingPartyId) {
		this.transReceivingPartyId = transReceivingPartyId;
	}
	public String getTransSendingPartyId() {
		return transSendingPartyId;
	}
	public void setTransSendingPartyId(String transSendingPartyId) {
		this.transSendingPartyId = transSendingPartyId;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getMessageIsTest() {
		return messageIsTest;
	}
	public void setMessageIsTest(String messageIsTest) {
		this.messageIsTest = messageIsTest;
	}
	public String getMessageReceivingPartyId() {
		return messageReceivingPartyId;
	}
	public void setMessageReceivingPartyId(String messageReceivingPartyId) {
		this.messageReceivingPartyId = messageReceivingPartyId;
	}
	public String getMessageSendingPartyId() {
		return messageSendingPartyId;
	}
	public void setMessageSendingPartyId(String messageSendingPartyId) {
		this.messageSendingPartyId = messageSendingPartyId;
	}
	public String getMessageStructureVersion() {
		return messageStructureVersion;
	}
	public void setMessageStructureVersion(String messageStructureVersion) {
		this.messageStructureVersion = messageStructureVersion;
	}
	public String getMessageCreationDate() {
		return messageCreationDate;
	}
	public void setMessageCreationDate(String messageCreationDate) {
		this.messageCreationDate = messageCreationDate;
	}
	public String getMessageControlNumber() {
		return messageControlNumber;
	}
	public void setMessageControlNumber(String messageControlNumber) {
		this.messageControlNumber = messageControlNumber;
	}
	public String getAdjustmentType() {
		return adjustmentType;
	}
	public void setAdjustmentType(String adjustmentType) {
		this.adjustmentType = adjustmentType;
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
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
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
	public String getAdjustmentControlId() {
		return adjustmentControlId;
	}
	public void setAdjustmentControlId(String adjustmentControlId) {
		this.adjustmentControlId = adjustmentControlId;
	}
	public String getInventoryEffectiveDateTime() {
		return inventoryEffectiveDateTime;
	}
	public void setInventoryEffectiveDateTime(String inventoryEffectiveDateTime) {
		this.inventoryEffectiveDateTime = inventoryEffectiveDateTime;
	}
	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}
	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
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
	public String getIanFilepath() {
		return ianFilepath;
	}
	public void setIanFilepath(String ianFilepath) {
		this.ianFilepath = ianFilepath;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getMessageError() {
		return messageError;
	}
	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}
	public int getRunStatus() {
		return runStatus;
	}
	public void setRunStatus(int runStatus) {
		this.runStatus = runStatus;
	}
	public String getTxnStatus() {
		return txnStatus;
	}
	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}
	public int getIsIanUpdated() {
		return isIanUpdated;
	}
	public void setIsIanUpdated(int isIanUpdated) {
		this.isIanUpdated = isIanUpdated;
	}
	public String getInvUpdateErrorMsg() {
		return invUpdateErrorMsg;
	}
	public void setInvUpdateErrorMsg(String invUpdateErrorMsg) {
		this.invUpdateErrorMsg = invUpdateErrorMsg;
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
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}
