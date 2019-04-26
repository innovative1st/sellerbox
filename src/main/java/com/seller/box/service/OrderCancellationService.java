package com.seller.box.service;

import com.seller.box.core.OC;
import com.seller.box.core.ServiceResponse;

public interface OrderCancellationService {
	public void createOrderCancellationAsync(String requestId, OC oc, String orderType, int etailorId, String user);
	public ServiceResponse createOrderCancellation(String requestId, OC oc, String orderType, int etailorId, String user);
	public Long isOrderCancellationExist(String requestId, int etailorId, String warehouseCode, String shipmentId);
}
