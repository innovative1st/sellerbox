package com.seller.box.entities;

import java.io.Serializable;
//import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT_CATALOGUE", catalog = "CATALOGUE") 
public class ProductCatalogue implements Serializable{
	private static final long serialVersionUID = 2458714696394131523L;
	//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "PRODUCT_ID")
//	private Long productId;
	@Id
	@Column(name = "SKU_CODE", unique = true, insertable = false, updatable = false)
	private String skuCode;
	@Column(name = "EAN")
	private String ean;
	@Column(name = "ASIN")
	private String asin;
	@Column(name = "FNSKU")
	private String fnsku;
	@Column(name = "PRODUCT_NAME")
	private String productName;
	@Column(name = "PUBLISHER")
	private String publisher;
	@Column(name = "AUTHOR")
	private String author;
//	@Column(name = "BINDING_TYPE")
//	private String bindingType;
//	@Column(name = "EDITION_NUMBER")
//	private int editionNumber;
//	@Column(name = "NUMBER_OF_PAGES")
//	private int numberOfPages;
//	@Column(name = "PUBLICATION_DATE")
//	private Date publicationDate;
//	@Column(name = "ITEM_CONDITION")
//	private String itemCondition;
	@Column(name = "THUMBNAIL_URL")
	private String thumbnailUrl;
	@Column(name = "SALES_STATUS")
	private String salesStatus;
//	@Column(name = "CREATED_BY")
//	private String createdBy;
//	@Column(name = "CREATED_DATE")
//	private Date createdDate;
//	@Column(name = "UPDATED_BY")
//	private String updatedBy;
//	@Column(name = "UPDATED_DATE")
//	private Date updatedDate;
//	@Column(name = "REASON_FOR_UPDATE")
//	private String reasonForUpdate;
	@Column(name = "INVENTORY_TYPE")
	private String inventoryType;
	
//	public Long getProductId() {
//		return productId;
//	}
//	public void setProductId(Long productId) {
//		this.productId = productId;
//	}
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
	public String getAsin() {
		return asin;
	}
	public void setAsin(String asin) {
		this.asin = asin;
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
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
//	public String getBindingType() {
//		return bindingType;
//	}
//	public void setBindingType(String bindingType) {
//		this.bindingType = bindingType;
//	}
//	public int getEditionNumber() {
//		return editionNumber;
//	}
//	public void setEditionNumber(int editionNumber) {
//		this.editionNumber = editionNumber;
//	}
//	public int getNumberOfPages() {
//		return numberOfPages;
//	}
//	public void setNumberOfPages(int numberOfPages) {
//		this.numberOfPages = numberOfPages;
//	}
//	public Date getPublicationDate() {
//		return publicationDate;
//	}
//	public void setPublicationDate(Date publicationDate) {
//		this.publicationDate = publicationDate;
//	}
//	public String getItemCondition() {
//		return itemCondition;
//	}
//	public void setItemCondition(String itemCondition) {
//		this.itemCondition = itemCondition;
//	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public String getSalesStatus() {
		return salesStatus;
	}
	public void setSalesStatus(String salesStatus) {
		this.salesStatus = salesStatus;
	}
//	public String getCreatedBy() {
//		return createdBy;
//	}
//	public void setCreatedBy(String createdBy) {
//		this.createdBy = createdBy;
//	}
//	public Date getCreatedDate() {
//		return createdDate;
//	}
//	public void setCreatedDate(Date createdDate) {
//		this.createdDate = createdDate;
//	}
//	public String getUpdatedBy() {
//		return updatedBy;
//	}
//	public void setUpdatedBy(String updatedBy) {
//		this.updatedBy = updatedBy;
//	}
//	public Date getUpdatedDate() {
//		return updatedDate;
//	}
//	public void setUpdatedDate(Date updatedDate) {
//		this.updatedDate = updatedDate;
//	}
//	public String getReasonForUpdate() {
//		return reasonForUpdate;
//	}
//	public void setReasonForUpdate(String reasonForUpdate) {
//		this.reasonForUpdate = reasonForUpdate;
//	}
	public String getInventoryType() {
		return inventoryType;
	}
	public void setInventoryType(String inventoryType) {
		this.inventoryType = inventoryType;
	}
}
