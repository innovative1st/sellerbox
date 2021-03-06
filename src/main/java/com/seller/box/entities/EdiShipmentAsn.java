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
@Table(name = "EDI_SHIPMENT_ASN", catalog = "SELLER")
public class EdiShipmentAsn implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="EDI_ORDER_ID", unique = true)
	private Long ediOrderId;
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
	@Column(name="MESSAGE_SENDING_PARTY_ID")
	private String messageSendingPartyId;
	@Column(name="MESSAGE_RECEIVING_PARTY_ID")
	private String messageReceivingPartyId;
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
	@Column(name="RESPONSE_CONDITION")
	private String responseCondition;
	@Column(name="RESULT_CODE")
	private String resultCode;
	@Column(name="RESULT_DESCRIPTION")
	private String resultDescription;
	@Column(name="PACKAGE_ID")
	private int packageId;
	@Column(name="TRACKING_ID")
	private String trackingId;
	@Column(name="MANIFEST_ID")
	private String manifestId;
	@Column(name="READY_TO_SHIP_DATE")
	private String readyToShipDate;
	@Column(name="MANIFEST_DATE")
	private String manifestDate;
	@Column(name="PACKAGE_WEIGHT_UNIT")
	private String packageWeightUnit;
	@Column(name="PACKAGE_WEIGHT_VALUE")
	private double packageWeightValue;
	@Column(name="SHIP_METHOD")
	private String shipMethod;
	@Column(name="RUN_STATUS")
	private int runStatus;
	@Column(name="TXN_STATUS")
	private String txnStatus;
	@Column(name="PROCESS_INSTANCE_ID")
	private String processInstanceId;
	@Column(name="ASN_FILE_PATH")
	private String asnFilePath;
	@Column(name="TXN_ERROR_MSG")
	private String txnErrorMsg;
	@Column(name = "CREATED_DATE")
	@Temporal(value = TemporalType.DATE)
	private Date createdDate;
	@Column(name = "OUTBOUND_DATE")
	private Date outboundDate;
	public Long getEdiOrderId() {
		return ediOrderId;
	}
	public void setEdiOrderId(Long ediOrderId) {
		this.ediOrderId = ediOrderId;
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
	public String getMessageSendingPartyId() {
		return messageSendingPartyId;
	}
	public void setMessageSendingPartyId(String messageSendingPartyId) {
		this.messageSendingPartyId = messageSendingPartyId;
	}
	public String getMessageReceivingPartyId() {
		return messageReceivingPartyId;
	}
	public void setMessageReceivingPartyId(String messageReceivingPartyId) {
		this.messageReceivingPartyId = messageReceivingPartyId;
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
	public String getResultDescription() {
		return resultDescription;
	}
	public void setResultDescription(String resultDescription) {
		this.resultDescription = resultDescription;
	}
	public int getPackageId() {
		return packageId;
	}
	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public String getManifestId() {
		return manifestId;
	}
	public void setManifestId(String manifestId) {
		this.manifestId = manifestId;
	}
	public String getReadyToShipDate() {
		return readyToShipDate;
	}
	public void setReadyToShipDate(String readyToShipDate) {
		this.readyToShipDate = readyToShipDate;
	}
	public String getManifestDate() {
		return manifestDate;
	}
	public void setManifestDate(String manifestDate) {
		this.manifestDate = manifestDate;
	}
	public String getPackageWeightUnit() {
		return packageWeightUnit;
	}
	public void setPackageWeightUnit(String packageWeightUnit) {
		this.packageWeightUnit = packageWeightUnit;
	}
	public double getPackageWeightValue() {
		return packageWeightValue;
	}
	public void setPackageWeightValue(double packageWeightValue) {
		this.packageWeightValue = packageWeightValue;
	}
	public String getShipMethod() {
		return shipMethod;
	}
	public void setShipMethod(String shipMethod) {
		this.shipMethod = shipMethod;
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
	public String getAsnFilePath() {
		return asnFilePath;
	}
	public void setAsnFilePath(String asnFilePath) {
		this.asnFilePath = asnFilePath;
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
	public Date getOutboundDate() {
		return outboundDate;
	}
	public void setOutboundDate(Date outboundDate) {
		this.outboundDate = outboundDate;
	}
	@Override
	public String toString() {
		return "EdiShipmentAsn [ediOrderId=" + ediOrderId + ", eretailorId=" + eretailorId + ", transCreationDate="
				+ transCreationDate + ", transIsTest=" + transIsTest + ", transControlNumber=" + transControlNumber
				+ ", messageCount=" + messageCount + ", transStructureVersion=" + transStructureVersion
				+ ", transReceivingPartyId=" + transReceivingPartyId + ", transSendingPartyId=" + transSendingPartyId
				+ ", messageType=" + messageType + ", messageIsTest=" + messageIsTest + ", messageSendingPartyId="
				+ messageSendingPartyId + ", messageReceivingPartyId=" + messageReceivingPartyId
				+ ", messageStructureVersion=" + messageStructureVersion + ", messageCreationDate="
				+ messageCreationDate + ", messageControlNumber=" + messageControlNumber + ", vendorPartyType="
				+ vendorPartyType + ", vendorPartyId=" + vendorPartyId + ", warehouseLocationId=" + warehouseLocationId
				+ ", purchaseOrderNumber=" + purchaseOrderNumber + ", customerOrderNumber=" + customerOrderNumber
				+ ", responseCondition=" + responseCondition + ", resultCode=" + resultCode + ", resultDescription="
				+ resultDescription + ", packageId=" + packageId + ", trackingId=" + trackingId + ", manifestId="
				+ manifestId + ", readyToShipDate=" + readyToShipDate + ", manifestDate=" + manifestDate
				+ ", packageWeightUnit=" + packageWeightUnit + ", packageWeightValue=" + packageWeightValue
				+ ", shipMethod=" + shipMethod + ", runStatus=" + runStatus + ", txnStatus=" + txnStatus
				+ ", processInstanceId=" + processInstanceId + ", asnFilePath=" + asnFilePath + ", txnErrorMsg="
				+ txnErrorMsg + ", createdDate=" + createdDate + ", outboundDate=" + outboundDate + "]";
	}
}
