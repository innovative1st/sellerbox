package com.seller.box.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.seller.box.entities.EdiShipmentOcr;

@Repository
public interface EdiShipmentOcrDao extends CrudRepository<EdiShipmentOcr, Long> {
	EdiShipmentOcr findByPurchaseOrderNumber(String purchaseOrderNumber);
	EdiShipmentOcr findByOcId(Long ocId);
}
