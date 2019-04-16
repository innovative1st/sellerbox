package com.seller.box.service;

import com.seller.box.core.OF;
import com.seller.box.core.ServiceResponse;

public interface OrderService {
	public void createOrderAsync(String requestId, OF of, int etailorId, Long ediOrderId, String user);
	public ServiceResponse createOrder(String requestId, OF of, int etailorId, Long ediOrderId, String user);
	public Long isOrderExist(String requestId, int etailorId, String warehouseCode, String shipmentId);
	public boolean isOrderExistByEdiOrderId(String requestId, Long ediOrderId);
	public void measurmentUpdateAsync(String requestId, Long ediOrderId);
}
