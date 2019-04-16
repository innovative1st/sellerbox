package com.seller.box.core;

import java.io.Serializable;

//import com.seller.box.message.transform.core.CRN;
//import com.seller.box.message.transform.core.OC;
//import com.seller.box.message.transform.core.OF;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	public String isTest;
    public String receivingPartyID;
    public String sendingPartyID;
    public String messageType;
    public String messageStructureVersion;
    public String messageCreationDate;
    public String messageControlNumber;
    private OF of;
    private CRN crn;
    private OC oc;
    private String status;
    private String requestId;
	public String getIsTest() {
		return isTest;
	}
	public void setIsTest(String isTest) {
		this.isTest = isTest;
	}
	public String getReceivingPartyID() {
		return receivingPartyID;
	}
	public void setReceivingPartyID(String receivingPartyID) {
		this.receivingPartyID = receivingPartyID;
	}
	public String getSendingPartyID() {
		return sendingPartyID;
	}
	public void setSendingPartyID(String sendingPartyID) {
		this.sendingPartyID = sendingPartyID;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
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
	public OF getOf() {
		return of;
	}
	public void setOf(OF of) {
		this.of = of;
	}
	public CRN getCrn() {
		return crn;
	}
	public void setCrn(CRN crn) {
		this.crn = crn;
	}
	public OC getOc() {
		return oc;
	}
	public void setOc(OC oc) {
		this.oc = oc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}