package com.seller.box.core;

import java.io.Serializable;

public class InvoiceFile implements Serializable {
	private static final long serialVersionUID = 1L;
	public String requestId;
    public String shipmentId;
    public String filepath;
    public String messageType;
    
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getShipmentId() {
		return shipmentId;
	}
	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	@Override
	public String toString() {
		return "{ \"shipmentId\":\""+shipmentId+"\", \"filepath\":\""+filepath.replace('\\', '/')+"\"}";
	}

}
