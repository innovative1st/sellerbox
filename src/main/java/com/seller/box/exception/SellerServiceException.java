package com.seller.box.exception;

public class SellerServiceException extends SellerClientException{
	private static final long serialVersionUID = 1L;
	private String requestId;
	private String errorCode;
	  
	public static enum ErrorType {
	    CLIENT_ERROR,  SERVICE_ERROR,  UNKNOWN, DATA_NOT_FOUND;
	    
	    private ErrorType() {}
	}
	private ErrorType errorType = ErrorType.UNKNOWN;
	private int statusCode;
	private String serviceName;

	public SellerServiceException(String message) {
		super(message);
	}

	public SellerServiceException(String message, Exception cause) {
		super(message, cause);
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRequestId() {
		return this.requestId;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceName() {
		return this.serviceName;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}

	public ErrorType getErrorType() {
		return this.errorType;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return this.statusCode;
	}

	public String toString() {
	    return "Status Code: " + getStatusCode() + ", " + "Service: " + getServiceName() + ", " + "Request ID: " + getRequestId() + ", " + "Error Code: " + getErrorCode() + ", " + "Error Message: " + getMessage();
	}
}
