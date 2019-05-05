package com.seller.box.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.seller.box.entities.EdiPackStationPrinter;

@Repository
public interface EdiPackStationPrinterDao extends CrudRepository<EdiPackStationPrinter, Long> {
	List<EdiPackStationPrinter> findByPackStationId(Long packStationId);
	EdiPackStationPrinter findByPackStationIdAndPrinterType(Long packStationId, String printerType);
}
