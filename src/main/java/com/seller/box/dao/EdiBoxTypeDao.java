package com.seller.box.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.seller.box.entities.EdiBoxType;

@Repository
public interface EdiBoxTypeDao extends CrudRepository<EdiBoxType, Long> {
	EdiBoxType findByBoxId(Long boxId);
	EdiBoxType findByBoxName(String boxName);
}
