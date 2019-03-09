package com.seller.box.exception;

public class NoDataFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String requestId;
	private String errorCode;
	private String errorType;
	private String serviceName;
	
	public NoDataFoundException(String message) {
		super(message);
	}

	public NoDataFoundException(String message, Exception t) {
		super(message, t);
	}
	
    public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
    
    public String getMessage() {
        return super.getMessage();
    }
	public String toString() {
	    return "Service: " + getServiceName() + ", " + "Request ID: " + getRequestId() + ", " + "Error Code: " + getErrorCode() + ", " + "Error Type: " + getErrorType() + ", " + "Error Message: " + getMessage();
	}
}
