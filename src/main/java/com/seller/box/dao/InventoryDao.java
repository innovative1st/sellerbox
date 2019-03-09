package com.seller.box.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.seller.box.entities.InventoryLevel;

public interface InventoryDao extends PagingAndSortingRepository<InventoryLevel, Long> {
	Page<InventoryLevel> findAll(Pageable pagable);
	Page<InventoryLevel> findBySkuCode(String skuCode, Pageable pagable);
	//List<InventoryLevel> findBySkuCode(String skuCode);
	Page<InventoryLevel> findAllByLocationCode(String locationCode, Pageable pagable);
	Page<InventoryLevel> findByEtailorId(int etailorId, Pageable pagable);
}
