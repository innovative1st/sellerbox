package com.seller.box.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.seller.box.entities.EdiPackStationInfo;

@Repository
public interface EdiPackStationInfoDao extends CrudRepository<EdiPackStationInfo, Long> {
	EdiPackStationInfo findByPackStationId(Long packStationId);
	EdiPackStationInfo findByPackStationNameAndPackStationLocation(String packStationName, String packStationLocation);
}
