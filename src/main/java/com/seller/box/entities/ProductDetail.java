package com.seller.box.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductDetail implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "PRODUCT_ID")
	private Long productId;
	@Column(name = "SKU_CODE", insertable = false, updatable = false)
	private String skuCode;
	@Column(name = "EAN")
	private String ean;
	@Column(name = "ITEM_ID")
	private String itemId;
	@Column(name = "FNSKU")
	private String fnsku;
	@Column(name = "PRODUCT_NAME")
	private String productName;
	@Column(name = "THUMBNAIL_URL")
	private String thumbnailUrl;
	@Column(name = "IS_SALEBLE")
	private int isSaleble;
	@Column(name = "IS_ACTIVE")
	private int isActive;
	@Column(name = "AUTO_SYNC_INVENTORY")
	private int autoSyncInventory;
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
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
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		if (getProductId() != null) {
		  sb.append("ProductId: " + getProductId() + ", ");
		}
		if (getSkuCode() != null) {
		  sb.append("SkuCode: " + getSkuCode() + ", ");
		}
		if (getEan() != null) {
		  sb.append("Ean: " + getEan() + ", ");
		}
		if (getItemId() != null) {
		  sb.append("ItemId: " + getItemId() + ", ");
		}
		if (getFnsku() != null) {
		  sb.append("Fnsku: " + getFnsku() + ", ");
		}
		if (getProductName() != null) {
		  sb.append("ProductName: " + getProductName() + ", ");
		}
		if (getThumbnailUrl() != null) {
		  sb.append("ThumbnailUrl: " + getThumbnailUrl() + ", ");
		}
		sb.append("IsSaleble: " + getIsSaleble() + ", ");
		sb.append("IsActive: " + getIsActive() + ", ");
		sb.append("AutoSyncInventory: " + getAutoSyncInventory() + ", ");
		sb.append("}");
		return sb.toString();
	}
	
	
}
