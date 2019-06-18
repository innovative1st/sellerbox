package com.seller.box.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.seller.box.entities.EdiPicklist;
import com.seller.box.entities.ReadyToShip;
import com.seller.box.entities.ShipmentHdrWithItem;
import com.seller.box.form.PicklistSearchForm;

public interface OrderDao {
	List<EdiPicklist> findReadyToPick(@Param("etailorId") String etailorId, @Param("warehouseCode") String warehouseCode, @Param("picklistFor") String picklistFor, @Param("picklistStatus") String picklistStatus);

	List<ReadyToShip> findReadyToShip(String etailorId, String warehouseCode, String pickupDate, String pickupOtherDate, String carrierName);

	List<ShipmentHdrWithItem> findNewShipments(PicklistSearchForm criteria);

	Map<String, String> createPicklist(PicklistSearchForm criteria);

	Map<String, Object> findPicklistStatus(String etailorId, String warehouseCode, String scanedValue);

	Long getEdiOrderIdForPacking(int etailorId, String warehouseCode, String picklistNumber, String ean);

	String getInvoiceFilepath(String shipmentId);
}
