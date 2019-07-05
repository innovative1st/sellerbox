package com.seller.box.service;

import java.util.Map;

import com.seller.box.core.OF;
import com.seller.box.core.ServiceResponse;
import com.seller.box.dao.OrderDao;
import com.seller.box.entities.EdiShipmentHdr;
import com.seller.box.form.Shipment;

public interface OrderService {
	public Shipment getShipmentForPacking(Long ediOrderId, EdiShipmentHdr sh);
	public void createOrderAsync(String requestId, OF of, String orderType, int etailorId, Long ediOrderId, String user);
	public ServiceResponse createOrder(String requestId, OF of, String orderType, int etailorId, Long ediOrderId, String user);
	public Long isOrderExist(String requestId, int etailorId, String warehouseCode, String shipmentId);
	public boolean isOrderExistByEdiOrderId(String requestId, Long ediOrderId);
	public void measurmentUpdateAsync(String requestId, Long ediOrderId);
	public void measurmentUpdateSync(String requestId, Long ediOrderId);
	
	public ServiceResponse createOfr(String requestId, Long ediOrderId, int orderStatus);
	public Long isOfrExistByEdiOrderId(String requestId, Long ediOrderId, int orderStatus);
	public ServiceResponse createAsn(String requestId, Long ediOrderId);
	public String getReadyToShipDate(String requestId, Long ediOrderId);
	public String makeSidelineShipment(OrderDao orderDao, Long ediOrderId, String sidelineReason, String guid);
	public String skipShipment(Long ediOrderId, String reasonForSkip);
	Map<String, Object> completionOfShipment(String requestId, Long ediOrderId, String warehouseCode, int etailorId, String trackingId, String guid);
	String getInvoiceFilepath(String shipmentId);
}
