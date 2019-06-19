package com.seller.box.core;

import java.io.Serializable;

import com.seller.box.entities.EdiInventoryIan;
import com.seller.box.entities.EdiShipmentAsn;
import com.seller.box.entities.EdiShipmentOcr;
import com.seller.box.entities.EdiShipmentOfr;

public class OutboundMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	private String requestId;
	private String messageType;
    private EdiShipmentAsn asn;
    private EdiShipmentOfr ofr;
    private EdiShipmentOcr ocr;
    private EdiInventoryIan ian;
    private String status;
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public EdiShipmentAsn getAsn() {
		return asn;
	}
	public void setAsn(EdiShipmentAsn asn) {
		this.asn = asn;
	}
	public EdiShipmentOfr getOfr() {
		return ofr;
	}
	public void setOfr(EdiShipmentOfr ofr) {
		this.ofr = ofr;
	}
	public EdiShipmentOcr getOcr() {
		return ocr;
	}
	public void setOcr(EdiShipmentOcr ocr) {
		this.ocr = ocr;
	}
	public EdiInventoryIan getIan() {
		return ian;
	}
	public void setIan(EdiInventoryIan ian) {
		this.ian = ian;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "OutboundMessage [requestId=" + requestId + ", messageType=" + messageType + ", asn=" + asn + ", ofr="
				+ ofr + ", ocr=" + ocr + ", ian=" + ian + ", status=" + status + "]";
	}
}
