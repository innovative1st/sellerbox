package com.seller.box.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.seller.box.entities.EdiPicklist;
import com.seller.box.entities.ReadyToShip;
import com.seller.box.entities.ShipmentHdrWithItem;
import com.seller.box.form.PicklistSearchForm;

public interface OrderDao {
	List<EdiPicklist> findReadyToPick(@Param("etailorId") Integer etailorId, @Param("locationCode") String locationCode, @Param("picklistFor") String picklistFor, @Param("picklistStatus") String picklistStatus);

	List<ReadyToShip> findReadyToShip(int etailorId, String locationCode, String pickupDate, String pickupOtherDate, String carrierName);

	List<ShipmentHdrWithItem> findNewShipments(PicklistSearchForm criteria);

	Map<String, String> createPicklist(PicklistSearchForm criteria);

	Map<String, Object> findPicklistStatus(int etailorId, String locationCode, String scanedValue);

	Long getEdiOrderIdForPacking(int etailorId, String locationCode, String picklistNumber, String ean);

	String getInvoiceFilepath(String shipmentId);
}
