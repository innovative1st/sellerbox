package com.seller.box.service;

import com.seller.box.core.ServiceResponse;
import com.seller.box.form.EdiInventoryIanForm;

public interface InventoryService {
	public ServiceResponse makeIAN(EdiInventoryIanForm input);
	//ProductDetails getProductInfo(int etailorId, String value);
}
