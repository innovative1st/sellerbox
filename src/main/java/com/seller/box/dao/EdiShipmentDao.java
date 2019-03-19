package com.seller.box.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.seller.box.entities.EdiShipmentHdr;

@Repository
public interface EdiShipmentDao extends CrudRepository<EdiShipmentHdr, Long>{
	EdiShipmentHdr findByEdiOrderId(Long ediOrderId);
}
