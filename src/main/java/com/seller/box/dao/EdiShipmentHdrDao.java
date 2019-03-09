package com.seller.box.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.seller.box.entities.EdiShipmentHdr;

@Repository
public interface EdiShipmentHdrDao extends CrudRepository<EdiShipmentHdr, Long>{
	//No use yet
}
