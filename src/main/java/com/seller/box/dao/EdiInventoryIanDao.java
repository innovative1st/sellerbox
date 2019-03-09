package com.seller.box.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.seller.box.entities.EdiInventoryIan;

@Repository
public interface EdiInventoryIanDao extends CrudRepository<EdiInventoryIan, Long> {
}
