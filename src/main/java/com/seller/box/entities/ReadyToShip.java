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
	
	
}
