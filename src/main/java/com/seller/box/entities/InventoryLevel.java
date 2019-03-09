package com.seller.box.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "INVENTORY_LEVEL", catalog = "INVENTORY") 
public class InventoryLevel implements Serializable{
	private static final long serialVersionUID = -5365826074495481145L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IL_ID")
	private Long ilId;
	@Column(name = "SKU_CODE", insertable = false, updatable = false)
	private String skuCode;
	@Column(name = "LOCATION_CODE")
	private String locationCode;
	@Column(name = "ETAILOR_ID")
	private int etailorId;
	@Column(name = "QUANTITY")
	private int quantity;
	@Column(name = "INVENTORY_STATUS")
	private String inventoryStatus;
	@Column(name = "SALES_PRICE")
	private float salesPrice;
	@Column(name = "SHIPPING_CHARGE")
	private float shippingChange;
	@Column(name = "INVENTORY_SOURCE_LOCATION")
	private String inventorySourceLocation;
	@Column(name = "ITEM_LOCATION")
	private String itemLocation;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "SKU_CODE", nullable = false)
    private ProductCatalogue productCatalogue;
	
	public Long getIlId() {
		return ilId;
	}
	public void setIlId(Long ilId) {
		this.ilId = ilId;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public int getEtailorId() {
		return etailorId;
	}
	public void setEtailorId(int etailorId) {
		this.etailorId = etailorId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getInventoryStatus() {
		return inventoryStatus;
	}
	public void setInventoryStatus(String inventoryStatus) {
		this.inventoryStatus = inventoryStatus;
	}
	public float getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(float salesPrice) {
		this.salesPrice = salesPrice;
	}
	public float getShippingChange() {
		return shippingChange;
	}
	public void setShippingChange(float shippingChange) {
		this.shippingChange = shippingChange;
	}
	public String getInventorySourceLocation() {
		return inventorySourceLocation;
	}
	public void setInventorySourceLocation(String inventorySourceLocation) {
		this.inventorySourceLocation = inventorySourceLocation;
	}
	public String getItemLocation() {
		return itemLocation;
	}
	public void setItemLocation(String itemLocation) {
		this.itemLocation = itemLocation;
	}
	public ProductCatalogue getProductCatalogue() {
		return productCatalogue;
	}
	public void setProductCatalogue(ProductCatalogue productCatalogue) {
		this.productCatalogue = productCatalogue;
	}
}
