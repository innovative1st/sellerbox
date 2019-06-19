package com.seller.box.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EDI_SHIPMENT_OC_ITEMS", catalog = "SELLER")
public class EdiShipmentOcItems implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="OC_ITEM_ID")
	private Long ocItemId;
	@Column(name="OC_ID", insertable=false, updatable=false)
	private Long ocId;
	@Column(name="LINE_ITEM_SEQ")
	private int lineItemSeq;
	@Column(name="WAREHOUSE_LOCATION_ID")
	private String warehouseLocationId;
	@Column(name="ITEM_ID")
	private String itemId;
	@Column(name="ITEM_TYPE")
	private String itemType;
	@Column(name="FNSKU")
	private String fnSku;
	@Column(name="SKU_CODE")
	private String skuCode;
	@Column(name="EAN")
	private Long ean;
	@Column(name="PRODUCT_NAME")
	private String productName;
	@Column(name="THUMBNAIL_URL")
	private String thumbnailUrl;
	@Column(name="QUANTITY")
	private int quantity;
	@Column(name="BAY_NO")
	private String bayNo;
	@Column(name="RACK_NO")
	private String rackNo;
	@Column(name="EDI_ORDER_ID")
	private Long ediOrderId;
	@Column(name="EDI_ITEM_ID")
	private Long ediItemId;
	@Column(name="RESPONSE_CONDITION")
	private String responseCondition;
	@Column(name="RESULT_CODE")
	private String resultCode;
	@Column(name="RESULT_DESCRIPTION")
	private String resultDescription;
	@Column(name="IS_IAN")
	private int isIan;
	@Column(name="IAN_ID")
	private Long ianId;
	@Column(name="REF_INWARD_ID")
	private String refInwardId;
	public Long getOcItemId() {
		return ocItemId;
	}
	public void setOcItemId(Long ocItemId) {
		this.ocItemId = ocItemId;
	}
	public Long getOcId() {
		return ocId;
	}
	public void setOcId(Long ocId) {
		this.ocId = ocId;
	}
	public int getLineItemSeq() {
		return lineItemSeq;
	}
	public void setLineItemSeq(int lineItemSeq) {
		this.lineItemSeq = lineItemSeq;
	}
	public String getWarehouseLocationId() {
		return warehouseLocationId;
	}
	public void setWarehouseLocationId(String warehouseLocationId) {
		this.warehouseLocationId = warehouseLocationId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getFnSku() {
		return fnSku;
	}
	public void setFnSku(String fnSku) {
		this.fnSku = fnSku;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public Long getEan() {
		return ean;
	}
	public void setEan(Long ean) {
		this.ean = ean;
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
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getBayNo() {
		return bayNo;
	}
	public void setBayNo(String bayNo) {
		this.bayNo = bayNo;
	}
	public String getRackNo() {
		return rackNo;
	}
	public void setRackNo(String rackNo) {
		this.rackNo = rackNo;
	}
	public Long getEdiOrderId() {
		return ediOrderId;
	}
	public void setEdiOrderId(Long ediOrderId) {
		this.ediOrderId = ediOrderId;
	}
	public Long getEdiItemId() {
		return ediItemId;
	}
	public void setEdiItemId(Long ediItemId) {
		this.ediItemId = ediItemId;
	}
	public String getResponseCondition() {
		return responseCondition;
	}
	public void setResponseCondition(String responseCondition) {
		this.responseCondition = responseCondition;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDescription() {
		return resultDescription;
	}
	public void setResultDescription(String resultDescription) {
		this.resultDescription = resultDescription;
	}
	public int getIsIan() {
		return isIan;
	}
	public void setIsIan(int isIan) {
		this.isIan = isIan;
	}
	public Long getIanId() {
		return ianId;
	}
	public void setIanId(Long ianId) {
		this.ianId = ianId;
	}
	public String getRefInwardId() {
		return refInwardId;
	}
	public void setRefInwardId(String refInwardId) {
		this.refInwardId = refInwardId;
	}
	@Override
	public String toString() {
		return "EdiShipmentOcItems [ocItemId=" + ocItemId + ", ocId=" + ocId + ", lineItemSeq=" + lineItemSeq
				+ ", warehouseLocationId=" + warehouseLocationId + ", itemId=" + itemId + ", itemType=" + itemType
				+ ", fnSku=" + fnSku + ", skuCode=" + skuCode + ", ean=" + ean + ", productName=" + productName
				+ ", thumbnailUrl=" + thumbnailUrl + ", quantity=" + quantity + ", bayNo=" + bayNo + ", rackNo="
				+ rackNo + ", ediOrderId=" + ediOrderId + ", ediItemId=" + ediItemId + ", responseCondition="
				+ responseCondition + ", resultCode=" + resultCode + ", resultDescription=" + resultDescription
				+ ", isIan=" + isIan + ", ianTxnId=" + ianId + ", refInwardId=" + refInwardId + "]";
	}
}
