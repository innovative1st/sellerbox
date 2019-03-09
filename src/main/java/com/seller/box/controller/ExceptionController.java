package com.seller.box.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.seller.box.exception.NoDataFoundException;
import com.seller.box.exception.SellerClientException;
import com.seller.box.exception.SellerServiceException;

@ControllerAdvice
public class ExceptionController {
	@ExceptionHandler(value = SellerClientException.class)
	public ResponseEntity<Object> exception(SellerClientException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(value = SellerServiceException.class)
	public ResponseEntity<Object> exception(SellerServiceException exception) {
		return new ResponseEntity<>(exception, HttpStatus.OK);
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<Object> exception(MissingServletRequestParameterException ex) {
		return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<Object> exception(NoDataFoundException ex) {
		return new ResponseEntity<>(ex, HttpStatus.NOT_FOUND);
	}
	
}
