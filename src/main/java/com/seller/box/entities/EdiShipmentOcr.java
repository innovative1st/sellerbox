package com.seller.box.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EDI_SHIPMENT_OCR", catalog="SELLER")
public class EdiShipmentOcr implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="OCR_ID")
	private Long ocrId;
	@Column(name="OC_ID")
	private Long ocId;
	@Column(name="ERETAILOR_ID")
	private int eretailorId;
	@Column(name="TRANS_CREATION_DATE")
	private String transCreationDate;
	@Column(name="TRANS_IS_TEST")
	private String transIsTest;
	@Column(name="TRANS_CONTROL_NUMBER")
	private String transControlNumber;
	@Column(name="MESSAGE_COUNT")
	private int messageCount;
	@Column(name="TRANS_STRUCTURE_VERSION")
	private String transStructureVersion;
	@Column(name="TRANS_RECEIVING_PARTY_ID")
	private String transReceivingPartyId;
	@Column(name="TRANS_SENDING_PARTY_ID")
	private String transSendingPartyId;
	@Column(name="MESSAGE_TYPE")
	private String messageType;
	@Column(name="MESSAGE_IS_TEST")
	private String messageIsTest;
	@Column(name="MESSAGE_RECEIVING_PARTY_ID")
	private String messageReceivingPartyId;
	@Column(name="MESSAGE_SENDING_PARTY_ID")
	private String messageSendingPartyId;
	@Column(name="MESSAGE_STRUCTURE_VERSION")
	private String messageStructureVersion;
	@Column(name="MESSAGE_CREATION_DATE")
	private String messageCreationDate;
	@Column(name="MESSAGE_CONTROL_NUMBER")
	private String messageControlNumber;
	@Column(name="VENDOR_PARTY_TYPE")
	private String vendorPartyType;
	@Column(name="VENDOR_PARTY_ID")
	private String vendorPartyId;
	@Column(name="WAREHOUSE_LOCATION_ID")
	private String warehouseLocationId;
	@Column(name="PURCHASE_ORDER_NUMBER")
	private String purchaseOrderNumber;
	@Column(name="CUSTOMER_ORDER_NUMBER")
	private String customerOrderNumber;
	@Column(name="CANCEL_REQUEST_TYPE")
	private String cancelRequestType;
	@Column(name="RESPONSE_CONDITION")
	private String responseCondition;
	@Column(name="RESULT_CODE")
	private String resultCode;
	//@Column(name="RESULT_DESCRIPTION")
	//private String resultDescription;
	@Column(name="RUN_STATUS")
	private int runStatus;
	@Column(name="TXN_STATUS")
	private String txnStatus;
	@Column(name="PROCESS_INSTANCE_ID")
	private String processInstanceId;
	@Column(name="OCR_FILE_PATH")
	private String ocrFilePath;
	@Column(name="TXN_ERROR_MSG")
	private String txnErrorMsg;
	@Column(name="CREATED_DATE")
	private Date createdDate;
	@Column(name="CREATED_BY")
	private String createdBy;
	@Column(name = "OUTBOUND_DATE")
	private Date outboundDate;
	public Long getOcrId() {
		return ocrId;
	}
	public void setOcrId(Long ocrId) {
		this.ocrId = ocrId;
	}
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
	public String getWarehouseLocationId() {
		return warehouseLocationId;
	}
	public void setWarehouseLocationId(String warehouseLocationId) {
		this.warehouseLocationId = warehouseLocationId;
	}
	public String getPurchaseOrderNumber() {
		return purchaseOrderNumber;
	}
	public void setPurchaseOrderNumber(String purchaseOrderNumber) {
		this.purchaseOrderNumber = purchaseOrderNumber;
	}
	public String getCustomerOrderNumber() {
		return customerOrderNumber;
	}
	public void setCustomerOrderNumber(String customerOrderNumber) {
		this.customerOrderNumber = customerOrderNumber;
	}
	public String getCancelRequestType() {
		return cancelRequestType;
	}
	public void setCancelRequestType(String cancelRequestType) {
		this.cancelRequestType = cancelRequestType;
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
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getOcrFilePath() {
		return ocrFilePath;
	}
	public void setOcrFilePath(String ocrFilePath) {
		this.ocrFilePath = ocrFilePath;
	}
	public String getTxnErrorMsg() {
		return txnErrorMsg;
	}
	public void setTxnErrorMsg(String txnErrorMsg) {
		this.txnErrorMsg = txnErrorMsg;
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
	public Date getOutboundDate() {
		return outboundDate;
	}
	public void setOutboundDate(Date outboundDate) {
		this.outboundDate = outboundDate;
	}
	@Override
	public String toString() {
		return "EdiShipmentOcr [ocrId=" + ocrId + ", ocId=" + ocId + ", eretailorId=" + eretailorId
				+ ", transCreationDate=" + transCreationDate + ", transIsTest=" + transIsTest + ", transControlNumber="
				+ transControlNumber + ", messageCount=" + messageCount + ", transStructureVersion="
				+ transStructureVersion + ", transReceivingPartyId=" + transReceivingPartyId + ", transSendingPartyId="
				+ transSendingPartyId + ", messageType=" + messageType + ", messageIsTest=" + messageIsTest
				+ ", messageReceivingPartyId=" + messageReceivingPartyId + ", messageSendingPartyId="
				+ messageSendingPartyId + ", messageStructureVersion=" + messageStructureVersion
				+ ", messageCreationDate=" + messageCreationDate + ", messageControlNumber=" + messageControlNumber
				+ ", vendorPartyType=" + vendorPartyType + ", vendorPartyId=" + vendorPartyId + ", warehouseLocationId="
				+ warehouseLocationId + ", purchaseOrderNumber=" + purchaseOrderNumber + ", customerOrderNumber="
				+ customerOrderNumber + ", cancelRequestType=" + cancelRequestType + ", responseCondition="
				+ responseCondition + ", resultCode=" + resultCode + ", runStatus=" + runStatus + ", txnStatus="
				+ txnStatus + ", processInstanceId=" + processInstanceId + ", ocrFilePath=" + ocrFilePath
				+ ", txnErrorMsg=" + txnErrorMsg + ", createdDate=" + createdDate + ", createdBy=" + createdBy
				+ ", outboundDate=" + outboundDate + "]";
	}
}