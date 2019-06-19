package com.seller.box.core;

import java.io.Serializable;

public class ServiceResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	private int responseCode;
	private String responseMessage;
	private String uniqueKey;
	private String requestId;
	private String status;
	private String errorDesc;
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public String getUniqueKey() {
		return uniqueKey;
	}
	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	@Override
	public String toString() {
		return "ServiceResponse [responseCode=" + responseCode + ", responseMessage=" + responseMessage + ", uniqueKey="
				+ uniqueKey + ", requestId=" + requestId + ", status=" + status + ", errorDesc=" + errorDesc + "]";
	}
}
