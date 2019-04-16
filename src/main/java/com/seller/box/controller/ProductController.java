package com.seller.box.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seller.box.dao.ProductMeasurementDao;
import com.seller.box.dao.ProductSearchDao;
import com.seller.box.entities.ProductDetails;
import com.seller.box.entities.ProductMeasurements;
import com.seller.box.exception.NoDataFoundException;
import com.seller.box.exception.SellerClientException;
import com.seller.box.utils.SBConstant;
import com.seller.box.utils.SBUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Product", position = 0, value = "Product", description = "API to retrieve or manipulate product related information.")
@RequestMapping("/product")
public class ProductController {
	@Autowired
	ProductSearchDao productSearchDao;
	@Autowired
	ProductMeasurementDao productMeasurementDao;
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiOperation(value = "Get product details")
	@PostMapping(value = "/getProductInfo", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> getProductDetailsInfo(@RequestParam(name = "guid") String guid,
			@RequestParam(name = "token") String token, @RequestParam(name = "RequestBody") String body) {
		ProductDetails product = null;
		Map<String, Object> bodyMap = null;
		int etailorId = 0;
		String locationCode = null;
		String value = null;
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if (bodyMap.containsKey(SBConstant.VAR_ETAILOR_ID)) {
				etailorId = (int) bodyMap.get(SBConstant.VAR_ETAILOR_ID);
			}
			if (bodyMap.containsKey(SBConstant.VAR_LOCATION_CODE)) {
				locationCode = (String) bodyMap.get(SBConstant.VAR_LOCATION_CODE);
			}
			if (bodyMap.containsKey("value")) {
				value = (String) bodyMap.get("value");
			}
		} catch (IOException e) {
			bodyMap = new HashMap<String, Object>();
		}
		if ((value != null && !value.isEmpty() && value != "") && etailorId != 0) {
			product = productSearchDao.findProduct(etailorId, value);
			if (product == null) {
				NoDataFoundException sse = new NoDataFoundException(
						"Product " + value + " not tagged with etailorId = " + etailorId);
				sse.setErrorType(HttpStatus.NO_CONTENT.name());
				sse.setErrorCode("0");
				sse.setRequestId(UUID.randomUUID().toString());
				sse.setServiceName("/product/getProductInfo");
				throw sse;
			} else {
				System.out.println("Response>>>>" + product.toString());
				return new ResponseEntity<>(product, HttpStatus.OK);
			}
		} else {
			if (value == null || value.isEmpty() || value == "") {
				throw new IllegalArgumentException("Parameter value cannot be null.");
			} else {
				throw new SellerClientException("Invalid argument passed to getProductInfo(...)");
			}
		}
	}

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Get product measurment details")
	@PostMapping(value = "/getProductMeasurement", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Object> getProductMeasurment(@RequestParam(name = "guid") String guid,
											  		   @RequestParam(name = "token") String token, 
											  		   @RequestParam(name = "RequestBody") String body) {
		//ProductMeasurements measurment = null;
		Map<String, Object> bodyMap = null;
		int eretailId = 0;
		String value = null;
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if (bodyMap.containsKey(SBConstant.VAR_ETAILOR_ID)) {
				eretailId = (int) bodyMap.get(SBConstant.VAR_ETAILOR_ID);
			}
			if (bodyMap.containsKey("value")) {
				value = (String) bodyMap.get("value");
			}
		} catch (IOException e) {
			bodyMap = new HashMap<String, Object>();
		}
		if (SBUtils.isNotNull(value) && !SBUtils.isNull(eretailId)) {
			ProductMeasurements measurment = productMeasurementDao.findProductByItemId(eretailId, value);

			
			if (measurment == null) {
				NoDataFoundException sse = new NoDataFoundException("Product " + value + " not tagged with etailorId = " + eretailId);
				sse.setErrorType(HttpStatus.NO_CONTENT.name());
				sse.setErrorCode("0");
				sse.setRequestId(UUID.randomUUID().toString());
				sse.setServiceName("/product/getProductMeasurment");
				throw sse;
			} else {
				System.out.println("Response>>>>" + measurment.toString());
				return new ResponseEntity<>(measurment, HttpStatus.OK);
			}
		} else {
			if (value == null || value.isEmpty() || value == "") {
				throw new IllegalArgumentException("Parameter value cannot be null.");
			} else {
				throw new SellerClientException("Invalid argument passed to getProductMeasurment(...)");
			}
		}
	}
}
