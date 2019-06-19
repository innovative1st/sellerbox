package com.seller.box.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seller.box.core.ServiceResponse;
import com.seller.box.dao.EdiInventoryIanDao;
import com.seller.box.dao.ProductSearchDao;
import com.seller.box.dao.VirtualInventorySearchDao;
import com.seller.box.entities.ProductDetails;
import com.seller.box.entities.VirtualInventorySearch;
import com.seller.box.exception.NoDataFoundException;
import com.seller.box.exception.SellerClientException;
import com.seller.box.form.EdiInventoryIanForm;
import com.seller.box.form.VirtualInventoryForm;
import com.seller.box.service.InventoryService;
import com.seller.box.utils.SBConstant;
import com.seller.box.utils.SBUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags= {"Inventory"}, position = 2, value="Inventory", consumes= "application/x-www-form-urlencoded", description="API to retrieve or manipulate inventory related information.")
@RequestMapping("/inventory")
public class InventoryController {
	private static final Logger logger = LogManager.getLogger(InventoryController.class);
	@Autowired
	VirtualInventorySearchDao vInventoryDao;
	@Autowired
	EdiInventoryIanDao inventoryIanDao;
	//@Autowired
	//EdiConfigDao configDao;
	@Autowired
	InventoryService inventoryService;
	@Autowired
	ProductSearchDao productSearchDao;
	
	@ApiOperation(value = "View status of virtual inventory")
	@PostMapping(value = "/virtualInventoryStatus", 
				 produces = {MediaType.APPLICATION_JSON_VALUE},
				 consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<List<VirtualInventorySearch>> getAllInventoryPagable(@RequestParam(name="guid") String guid, 
			  										  						   @RequestParam(name="token") String token, 
			  										  						   @RequestParam(name = "RequestBody") String body){
		VirtualInventoryForm criteria = new VirtualInventoryForm();
		try {
			criteria = new ObjectMapper().readValue(body, VirtualInventoryForm.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int page = criteria.getPage();
		int size = criteria.getSize();
		if(size == 0) {
			size = SBConstant.DEFAULT_PAGE_SIZE;
		}
		if(criteria.getSkuCode() != null || criteria.getSkuCode() != "") {
			criteria.setSkuCode(null);
		}
		if(SBUtils.isNull(criteria.getWarehouseCode()) || criteria.getEtailorId() == 0) {
			StringBuffer args = new StringBuffer();
			if(SBUtils.isNull(criteria.getWarehouseCode())) {
				args.append(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(criteria.getEtailorId() == 0) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_ETAILOR_ID);
			}
			
			throw new SellerClientException("Following arguments "+args+" is mandatory to getAllInventoryPagable(...)");
		} 
		//Pageable pagable = new PageRequest(page, size);
		List<VirtualInventorySearch> result = null;
		if(criteria.getWarehouseCode() != null && criteria.getEtailorId() != 0 && criteria.getSkuCode() == null) {
			result = (List<VirtualInventorySearch>) vInventoryDao.findAllPagable(criteria.getEtailorId(), criteria.getWarehouseCode());
		} else if(criteria.getWarehouseCode() != null && criteria.getEtailorId() != 0 && criteria.getSkuCode() != null) {
			result = (List<VirtualInventorySearch>) vInventoryDao.findAllWithSkuPagable(criteria.getEtailorId(), criteria.getWarehouseCode(), criteria.getSkuCode());
		} 
		if(result == null) {
			NoDataFoundException sse = new NoDataFoundException("There are no product/inventory status based on provided criteria.");
			sse.setErrorType(SBConstant.ErrorType.DATA_NOT_FOUND.name());
			sse.setErrorCode(SBConstant.ERROR_CODE_DATA_NOT_FOUND);
			sse.setRequestId(UUID.randomUUID().toString());
			sse.setServiceName("/inventory/viAdvanceSearch");
			throw sse;
		} else {
			return new ResponseEntity<List<VirtualInventorySearch>>(result, HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "View virtual inventory", hidden = true)
	@PostMapping(value = "/viBasicSearch", 
			 produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<VirtualInventorySearch> virtuenInventoryBasicSearch(@RequestParam(name = "etailorId") Optional<Integer> etailorId, @RequestParam(name = "value") String value){
		List<VirtualInventorySearch> result = null;
		int page = 0;
		int size = SBConstant.DEFAULT_PAGE_SIZE;
		if((value != null && !value.isEmpty() && value != "") && (etailorId.isPresent())) {
			//Pageable pagable = new PageRequest(page, size);
			result = (List<VirtualInventorySearch>) vInventoryDao.findAllInventoryOnBasicSearchWithEtailor(etailorId.get(), value);
		} else if((value != null && !value.isEmpty() && value != "") && (!etailorId.isPresent())) {
			//Pageable pagable = new PageRequest(page, size);
			result = (List<VirtualInventorySearch>) vInventoryDao.findAllInventoryOnBasicSearch(value);
		} else {
			throw new SellerClientException("Invalid argument passed to viBasicSearch(...), value is null");
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "View inventory status for lost(Outbound)")
	@PostMapping(value = "/inventoryStatusForLost",
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public int getQueueQuantityForLost(@RequestParam(name="guid") String guid, 
				   					   @RequestParam(name="token") String token, 
				   					   @RequestParam(name = "RequestBody") String body) {
		Map<String, Object> bodyMap = null;
		int etailorId		= 0; 
		String warehouseCode	= null;
		String skuCode		= null;
		try {
			bodyMap = new ObjectMapper().readValue(body, HashMap.class);
			if(bodyMap.containsKey(SBConstant.VAR_ETAILOR_ID)) {
				etailorId = (int) bodyMap.get(SBConstant.VAR_ETAILOR_ID);
			}
			if(bodyMap.containsKey(SBConstant.VAR_WAREHOUSE_CODE)) {
				warehouseCode = (String) bodyMap.get(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(bodyMap.containsKey(SBConstant.VAR_SKU_CODE)) {
				skuCode = (String) bodyMap.get(SBConstant.VAR_SKU_CODE);
			}
		} catch (IOException e) {
			bodyMap = new HashMap<String, Object>();
		}
		if(etailorId == 0 || SBUtils.isNull(warehouseCode) || SBUtils.isNull(skuCode)) {
			StringBuffer args = new StringBuffer();
			if(SBUtils.isNull(warehouseCode)) {
				args.append(SBConstant.VAR_WAREHOUSE_CODE);
			}
			if(etailorId == 0) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_ETAILOR_ID);
			}
			if(SBUtils.isNull(skuCode)) {
				args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_SKU_CODE);
			}
			
			throw new SellerClientException("Following arguments "+args.toString()+" is mandarory to getQueueQuantityForLost(...)");
		} else {
			return vInventoryDao.getQueueQuantityForLost(etailorId, warehouseCode, skuCode);
		}
	}
	
	@ApiOperation(value = "View live inventory status")
	@PostMapping(value = "/inventoryStatus",
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public int getAvailableStock(@RequestParam(name = "etailorId") int etailorId, 
											  @RequestParam(name = "warehouseCode") String warehouseCode, 
											  @RequestParam(name = "skuCode") String skuCode) {
		return vInventoryDao.getAvailableStock(etailorId, warehouseCode, skuCode);
	}
	
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Create inventory adjustment notification (IAN) for FOUND/LOST")
	@ApiImplicitParams(
			{ 
				@ApiImplicitParam(name = "RequestBody", dataType = "String", required = true, paramType = "query", value = "<table border=1 ><tr><td width=\"100\"><strong>Field Name </strong></td><td width=\"100\"><strong>Data Type </strong></td><td width=\"100\"><strong>Mandatory </strong></td><td width=\"300\"><strong>Description </strong></td></tr><tr><td>warehouseCode</td><td>Varchar(10)</td><td align=\"center\">Mandatory</td><td>eRetail Warehouse Code. API will pick Loc Code from WMS API configuration.</td></tr><tr><td>etailorId</td><td>int(3)</td><td>Mandatory</td><td>eRetail Id</td></tr><tr align=\"center\"><td>skuCode</td><td>Varchar(20)</td><td>Mandatory</td><td>Seller sku code of the product.</td></tr><tr align=\"center\"><td>fnsku</td><td>Varchar(20)</td><td>Mandatory</td><td>Channel fulfillment network sku of the product.</td></tr><tr align=\"center\"><td>adjustmentType</td><td>Varchar(20)</td><td>Mandatory</td><td>Adjustment type, For inbound 'FOUND' or for outbound 'LOST'</td></tr><tr align=\"center\"><td>inventoryDestinationLocation</td><td>Varchar(20)</td><td>Optional</td><td>In case of inbound it should be 'PRIME'</td></tr><tr align=\"center\"><td>inventorySourceLocation</td><td>Varchar(20)</td><td>Optional</td><td>In case of inbound it should be 'UNAVAILABLE'</td></tr><tr><td colspan=\"4\"><p><strong>Input JSON Format- <br /> {<br />&nbsp; &nbsp;\"warehouseCode\":\"XXXX\", <br />&nbsp; &nbsp;\"etailorId\":101, <br />&nbsp; &nbsp;\"skuCode\":\"xxxxxxxxxxxxx\", <br />&nbsp; &nbsp;\"fnsku\":\"xxxxxxxxxx\", <br />&nbsp; &nbsp;\"quantity\":100,<br />&nbsp; &nbsp;\"adjustmentType\":\"FOUND\", <br />&nbsp; &nbsp;\"inventorySourceLocation\":\"UNAVAILABLE\", <br />&nbsp; &nbsp;\"inventoryDestinationLocation\":\"PRIME\"<br />}<br /></strong></p></td></tr></table>"),
				@ApiImplicitParam(name = "guid", dataType = "String", required = true, paramType = "query", value = "API Owner"),
				@ApiImplicitParam(name = "token", dataType = "String", required = true, paramType = "query", value = "API Key")
			})
	@RequestMapping(method= {RequestMethod.POST, RequestMethod.GET}, value = "/makeIAN", consumes = MediaType.ALL_VALUE)
	public ResponseEntity<ServiceResponse> makeIAN(@RequestParam(name = "guid") String guid,
												   @RequestParam(name = "token") String token, 
												   @RequestParam(name = "RequestBody") String body) {
		logger.info("makeIAN(String guid, String token, String body)---START");
		ServiceResponse response = new ServiceResponse();
		EdiInventoryIanForm input = new EdiInventoryIanForm();
		if (body != null) {
			try {
				Map<String, Object> bodyMap = new ObjectMapper().readValue(body, HashMap.class);
				String requestId = null;
				if (bodyMap.containsKey(SBConstant.VAR_REQUEST_ID)) {
					requestId = (String) bodyMap.get(SBConstant.VAR_REQUEST_ID);
				} else {
					requestId = UUID.randomUUID().toString();
				}
				input.setRequestId(requestId);
				response.setRequestId(requestId);
				if (bodyMap.containsKey(SBConstant.VAR_WAREHOUSE_CODE)) {
					String warehouseCode = (String) bodyMap.get(SBConstant.VAR_WAREHOUSE_CODE);
					input.setWarehouseCode(warehouseCode);;
				}
				if (bodyMap.containsKey(SBConstant.VAR_ETAILOR_ID)) {
					int etailorId = (int) bodyMap.get(SBConstant.VAR_ETAILOR_ID);
					input.setEtailorId(etailorId);
				}
				if (bodyMap.containsKey(SBConstant.VAR_SKU_CODE)) {
					String skuCode = (String) bodyMap.get(SBConstant.VAR_SKU_CODE);
					input.setSkuCode(skuCode);
				}
				if (bodyMap.containsKey(SBConstant.VAR_FNSKU)) {
					String fnsku = (String) bodyMap.get(SBConstant.VAR_FNSKU);
					input.setFnsku(fnsku);
				}
				if (bodyMap.containsKey(SBConstant.VAR_QUANTITY)) {
					int quantity = (int) bodyMap.get(SBConstant.VAR_QUANTITY);
					input.setQuantity(quantity);
				}
				if (bodyMap.containsKey(SBConstant.VAR_ADJUSTMENT_TYPE)) {
					String adjustmentType = (String) bodyMap.get(SBConstant.VAR_ADJUSTMENT_TYPE);
					input.setAdjustmentType(adjustmentType);
				}
				if (bodyMap.containsKey(SBConstant.VAR_ADJUSTMENT_TYPE)) {
					String adjustmentType = (String) bodyMap.get(SBConstant.VAR_ADJUSTMENT_TYPE);
					input.setAdjustmentType(adjustmentType);
				}
				if (bodyMap.containsKey(SBConstant.VAR_PURCHASE_ORDER_NUMBER)) {
					String purchaseOrderNumber = (String) bodyMap.get(SBConstant.VAR_PURCHASE_ORDER_NUMBER);
					input.setPurchaseOrderNumber(purchaseOrderNumber);
				}
				if (bodyMap.containsKey(SBConstant.VAR_INVENTORY_SOURCE_LOCATION)) {
					String inventorySourceLocation = (String) bodyMap.get(SBConstant.VAR_INVENTORY_SOURCE_LOCATION);
					input.setInventorySourceLocation(inventorySourceLocation);
				}
				if (bodyMap.containsKey(SBConstant.VAR_INVENTORY_DESTINATION_LOCATION)) {
					String inventoryDestinationLocation = (String) bodyMap.get(SBConstant.VAR_INVENTORY_DESTINATION_LOCATION);
					input.setInventoryDestinationLocation(inventoryDestinationLocation);
				}
				input.setCreatedBy(guid);
				if(SBUtils.isNull(input.getPurchaseOrderNumber())){
					if(input.getAdjustmentType().equalsIgnoreCase(SBConstant.IAN_ADUSTMENT_TYPE_FOUND)) {
						input.setInventorySourceLocation(SBConstant.IAN_LOCATION_TYPE_UNAVAILABLE);
						input.setInventoryDestinationLocation(SBConstant.IAN_LOCATION_TYPE_PRIME);
					} else if(input.getAdjustmentType().equalsIgnoreCase(SBConstant.IAN_ADUSTMENT_TYPE_LOST)) {
						input.setInventorySourceLocation(SBConstant.IAN_LOCATION_TYPE_PRIME);
						input.setInventoryDestinationLocation(SBConstant.IAN_LOCATION_TYPE_UNAVAILABLE);
					} 
				}
				if (SBUtils.isNull(input.getEtailorId()) || SBUtils.isNull(input.getWarehouseCode()) || SBUtils.isNull(input.getSkuCode()) || SBUtils.isNull(input.getQuantity()) || SBUtils.isNull(input.getAdjustmentType()) || SBUtils.isNull(input.getInventorySourceLocation()) || SBUtils.isNull(input.getInventoryDestinationLocation())) {
					StringBuffer args = new StringBuffer();
					if(SBUtils.isNull(input.getWarehouseCode())) {
						args.append(SBConstant.VAR_WAREHOUSE_CODE);
					}
					if(SBUtils.isNull(input.getEtailorId())) {
						args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_ETAILOR_ID);
					}
					if(SBUtils.isNull(input.getSkuCode())) {
						args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_SKU_CODE);
					}
					if(SBUtils.isNull(input.getQuantity())) {
						args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_QUANTITY);
					}
					if(SBUtils.isNull(input.getAdjustmentType())) {
						args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_ADJUSTMENT_TYPE);
					}
					if(SBUtils.isNull(input.getInventorySourceLocation())) {
						args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_INVENTORY_SOURCE_LOCATION);
					}
					if(SBUtils.isNull(input.getInventoryDestinationLocation())) {
						args.append(args.length() > 0 ? ", " : "").append(SBConstant.VAR_INVENTORY_DESTINATION_LOCATION);
					}
					throw new SellerClientException("Following arguments "+args.toString()+" is mandarory to ian.");
				} else {
					if(!SBUtils.isNull(input.getEtailorId()) && !SBUtils.isNull(input.getSkuCode())) {
						ProductDetails product = productSearchDao.findProduct(input.getEtailorId(), input.getSkuCode());
						if(product != null) {
							input.setFnsku(product.getFnsku());
							logger.info("IAN Request >>>> "+input.toString());
							ServiceResponse resp = inventoryService.makeIAN(input);
							response.setResponseCode(resp.getResponseCode());
							response.setStatus(resp.getStatus());
							response.setResponseMessage(resp.getResponseMessage());
							response.setUniqueKey(resp.getUniqueKey());
							response.setErrorDesc(resp.getErrorDesc());
							logger.info("IAN Response >>>> "+response.toString());
						} else {
							String errorMsg = input.getSkuCode() +" may not be tagged with selected etailor = " + input.getEtailorId();
							response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_FAILURE);
							response.setStatus(SBConstant.TXN_STATUS_FAILURE);
							response.setResponseMessage(errorMsg);
							response.setUniqueKey(null);
							response.setErrorDesc(errorMsg);
							logger.error(input.getSkuCode() +" may not be tagged with selected etailor = " + input.getEtailorId());
						}
					}
				}
			} catch (IOException e) {
				response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_EXCEPTION);
				response.setStatus(SBConstant.TXN_STATUS_EXCEPTION);
				response.setResponseMessage("General exception occured, Unable to send IAN.");
				response.setUniqueKey(null);
				response.setErrorDesc(e.getMessage());
				logger.error("IOException :: makeIAN(String guid, String token, String body)", e);
			}
		} else {
			logger.error("makeIAN(String guid, String token, String body) -- RequestBody for IAN is null.");
			response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_FAILURE);
			response.setStatus(SBConstant.TXN_STATUS_FAILURE);
			response.setResponseMessage("RequestBody for IAN is null.");
			response.setUniqueKey(null);
			response.setErrorDesc("RequestBody for IAN is null.");
		}
		logger.info("makeIAN(String guid, String token, String body)---END");
		return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
	}
}
