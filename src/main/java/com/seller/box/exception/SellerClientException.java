package com.seller.box.exception;

public class SellerClientException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public SellerClientException(String message, Throwable t) {
	    super(message, t);
	}
	  
	public SellerClientException(String message) {
	    super(message);
	}
}
