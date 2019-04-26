package com.seller.box.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.seller.box.entities.EdiShipmentAsn;

@Repository
public interface EdiShipmentAsnDao extends CrudRepository<EdiShipmentAsn, Long>{
	EdiShipmentAsn findByEdiOrderId(Long ediOrderId);
}
