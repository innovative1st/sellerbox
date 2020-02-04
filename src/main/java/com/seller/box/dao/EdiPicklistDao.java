package com.seller.box.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.seller.box.entities.EdiPicklist;

@Repository
public interface EdiPicklistDao extends CrudRepository<EdiPicklist, Long>{
	EdiPicklist findByPicklistNumber(String picklistNumber);
	EdiPicklist findByPicklistId(Long picklistId);
}
