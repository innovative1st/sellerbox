package com.seller.box.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.seller.box.entities.InventoryLevel;

public interface InventoryDao extends PagingAndSortingRepository<InventoryLevel, Long> {
	List<InventoryLevel> findAll();
	List<InventoryLevel> findBySkuCode(String skuCode);
	//List<InventoryLevel> findBySkuCode(String skuCode);
	List<InventoryLevel> findAllByLocationCode(String locationCode);
	List<InventoryLevel> findByEtailorId(int etailorId);
}
