package com.seller.box.dao;

import java.util.Map;

public interface MasterDao {
	Map<Integer, String> getOrderStatus();
	Map<Integer, String> getEtailorList();
	Map<String, String> getWarehouseCodeList();
}
