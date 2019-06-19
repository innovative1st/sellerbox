package com.seller.box.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.seller.box.entities.EdiShipmentInvoice;

@Repository
public interface ShipmentInvoiceDao  extends CrudRepository<EdiShipmentInvoice, String>{
	EdiShipmentInvoice findByShipmentId(String shipmentId);
}
