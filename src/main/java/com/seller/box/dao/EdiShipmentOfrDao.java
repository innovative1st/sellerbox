package com.seller.box.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.seller.box.entities.EdiShipmentOfr;

@Repository
public interface EdiShipmentOfrDao extends CrudRepository<EdiShipmentOfr, Long>{

}