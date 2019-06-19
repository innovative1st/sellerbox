package com.seller.box.service;

import com.seller.box.core.CRN;
import com.seller.box.core.ServiceResponse;
import com.seller.box.entities.EdiShipmentCrn;

public interface OrderReturnsService {
	public void createOrderReturnsAsync(String requestId, CRN crn, String vendorPartyId, int etailorId, String user);
	public ServiceResponse createOrderReturns(String requestId, CRN crn, String vendorPartyId, int etailorId, String user);
	public Long isOrderReturnsExist(String requestId, int etailorId, CRN crn);
	public boolean isOrderReturnsExistByCrnId(String requestId, Long crnId);
	public boolean isOrderReturnsProcessedByCrnId(String requestId, Long crnId);
	public ServiceResponse createIanAgainstOrderReturns(String requestId, EdiShipmentCrn crn);
}
