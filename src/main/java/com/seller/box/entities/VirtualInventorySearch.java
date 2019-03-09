package com.seller.box.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class VirtualInventorySearch implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "IL_ID")
	private Long ilId;
	@Column(name = "SKU_CODE", insertable = false, updatable = false)
	private String skuCode;
	@Column(name = "EAN")
	private String ean;
	@Column(name = "LOCATION_CODE")
	private String locationCode;
	@Column(name = "ETAILOR_ID")
	private int etailorId;
	@Column(name = "ITEM_ID")
	private String itemId;
	@Column(name = "FNSKU")
	private String fnsku;
	@Column(name = "PRODUCT_NAME")
	private String productName;
	@Column(name = "BRAND_NAME")
	private String brandName;
	@Column(name = "QUANTITY")
	private int quantity;
	@Column(name = "INVENTORY_STATUS")
	private String inventoryStatus;
	@Column(name = "SALE_PRICE")
	private float salePrice;
	@Column(name = "SHIPPING_CHARGE")
	private float shippingChange;
	@Column(name = "INVENTORY_SOURCE_LOCATION")
	private String inventorySourceLocation;
	@Column(name = "ITEM_LOCATION")
	private String itemLocation;
	@Column(name = "THUMBNAIL_URL")
	private String thumbnailUrl;
	@Column(name = "IS_SALEBLE")
	private int isSaleble;
	@Column(name = "IS_ACTIVE")
	private int isActive;
	@Column(name = "AUTO_SYNC_INVENTORY")
	private int autoSyncInventory;
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
	public String getEan() {
		return ean;
	}
	public void setEan(String ean) {
		this.ean = ean;
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
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
	public float getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(float salePrice) {
		this.salePrice = salePrice;
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
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public int getIsSaleble() {
		return isSaleble;
	}
	public void setIsSaleble(int isSaleble) {
		this.isSaleble = isSaleble;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public int getAutoSyncInventory() {
		return autoSyncInventory;
	}
	public void setAutoSyncInventory(int autoSyncInventory) {
		this.autoSyncInventory = autoSyncInventory;
	}
}
