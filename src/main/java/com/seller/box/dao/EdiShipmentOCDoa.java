package com.seller.box.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.seller.box.entities.EdiShipmentOc;

@Repository
public interface EdiShipmentOCDoa extends CrudRepository<EdiShipmentOc, Long>{
	EdiShipmentOc findByOcId(Long ocId);
}
