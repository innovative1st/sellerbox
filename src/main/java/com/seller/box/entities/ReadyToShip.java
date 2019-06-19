package com.seller.box.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EDI_SHIPMENT_HDR", catalog = "SELLER")
public class ReadyToShip implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "EDI_ORDER_ID")
	private Long ediOrderId;
	@Column(name = "ERETAILOR_ID")
	private int etailorId;
	@Column(name = "PURCHASE_ORDER_NUMBER")
	private String shipmentId;
	@Column(name = "CUSTOMER_ORDER_NUMBER")
	private String orderId;
	@Column(name = "WAREHOUSE_CODE")
	private String warehouseCode;
	@Column(name = "PICKLIST_NUMBER")
	private String picklistNumber;
	@Column(name = "TRACKING_ID")
	private String trackingId;
	@Column(name = "CARRIER_NAME")
	private String carrierName;
	@Column(name = "PICKUP_DATE")
	private Date pickupDate;
	@Column(name = "EXPECTED_SHIP_DATE")
	private Date exSdDate;
	@Column(name = "ORDER_STATUS")
	private int orderStatus;
	public Long getEdiOrderId() {
		return ediOrderId;
	}
	public void setEdiOrderId(Long ediOrderId) {
		this.ediOrderId = ediOrderId;
	}
	public int getEtailorId() {
		return etailorId;
	}
	public void setEtailorId(int etailorId) {
		this.etailorId = etailorId;
	}
	public String getShipmentId() {
		return shipmentId;
	}
	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public String getPicklistNumber() {
		return picklistNumber;
	}
	public void setPicklistNumber(String picklistNumber) {
		this.picklistNumber = picklistNumber;
	}
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public Date getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}
	public Date getExSdDate() {
		return exSdDate;
	}
	public void setExSdDate(Date exSdDate) {
		this.exSdDate = exSdDate;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
}
