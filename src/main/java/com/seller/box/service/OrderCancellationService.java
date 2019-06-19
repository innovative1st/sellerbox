package com.seller.box.service;

import com.seller.box.core.OC;
import com.seller.box.core.ServiceResponse;
import com.seller.box.entities.EdiShipmentOc;

public interface OrderCancellationService {
	public void createOrderCancellationAsync(String requestId, OC oc, String vendorPartyId, int etailorId, String user);
	public ServiceResponse createOrderCancellation(String requestId, OC oc, String vendorPartyId, int etailorId, String user);
	public Long isOrderCancellationExist(String requestId, int etailorId, String warehouseCode, String shipmentId);
	//public Long isOcExist(String requestId, Long shipmentId);
	public boolean isOcrExist(String requestId, int etailorId, String warehouseCode, String shipmentId);
	ServiceResponse createOcrAgainstOc(String requestId, EdiShipmentOc xoc);
}
