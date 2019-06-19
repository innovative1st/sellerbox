package com.seller.box.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductMeasurements implements Serializable{
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
	//HEIGHT, LENGTH, WIDTH, AS WEIGHT
	@Column(name = "HEIGHT")
	private double height;
	@Column(name = "LENGTH")
	private double length;
	@Column(name = "WIDTH")
	private double width;
	@Column(name = "WEIGHT")
	private double weight;
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
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	@Override
	public String toString() {
		return "ProductMeasurements [productId=" + productId + ", skuCode=" + skuCode + ", ean=" + ean + ", itemId="
				+ itemId + ", fnsku=" + fnsku + ", productName=" + productName + ", thumbnailUrl=" + thumbnailUrl
				+ ", height=" + height + ", length=" + length + ", width=" + width + ", weight=" + weight
				+ ", getProductId()=" + getProductId() + ", getSkuCode()=" + getSkuCode() + ", getEan()=" + getEan()
				+ ", getItemId()=" + getItemId() + ", getFnsku()=" + getFnsku() + ", getProductName()="
				+ getProductName() + ", getThumbnailUrl()=" + getThumbnailUrl() + ", getHeight()=" + getHeight()
				+ ", getLength()=" + getLength() + ", getWidth()=" + getWidth() + ", getWeight()=" + getWeight()
				+ "]";
	}
}
