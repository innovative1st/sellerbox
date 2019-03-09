package com.seller.box.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EdiScanPicklist implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "EDI_ORDER_ID")
	private Long ediOrderId;
	@Column(name = "ERETAILOR_ID")
	private Long etailorId;
	@Column(name = "PICKLIST_ID")
	private Long ediPicklistId;
	@Column(name = "WAREHOUSE_CODE")
	private String warehouseCode;
	@Column(name = "LINE_ITEM_SEQ")
	private int lineItemSeq;
	@Column(name = "SKU_CODE")
	private String skuCode;
	@Column(name = "EAN")
	private String ean;
	@Column(name = "ITEM_ID")
	private String itemId;
	@Column(name = "FNSKU")
	private String fnsku;
	@Column(name = "QUANTITY")
	private int quantity;
	@Column(name = "ORDER_STATUS")
	private String orderStatus;
	
	public Long getEdiOrderId() {
		return ediOrderId;
	}
	public void setEdiOrderId(Long ediOrderId) {
		this.ediOrderId = ediOrderId;
	}
	public Long getEtailorId() {
		return etailorId;
	}
	public void setEtailorId(Long etailorId) {
		this.etailorId = etailorId;
	}
	public Long getEdiPicklistId() {
		return ediPicklistId;
	}
	public void setEdiPicklistId(Long ediPicklistId) {
		this.ediPicklistId = ediPicklistId;
	}
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public int getLineItemSeq() {
		return lineItemSeq;
	}
	public void setLineItemSeq(int lineItemSeq) {
		this.lineItemSeq = lineItemSeq;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getEan() {
		return ean;
	}
	public void setEan(String ean) {
		this.ean = ean;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getFnsku() {
		return fnsku;
	}
	public void setFnsku(String fnsku) {
		this.fnsku = fnsku;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
}