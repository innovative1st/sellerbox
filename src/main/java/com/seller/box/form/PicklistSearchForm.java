package com.seller.box.form;
public class PicklistSearchForm {
	private int etailorId;
	private String warehouseCode;
	private String exSDAfter; 	//format dd-mm-yyyy HH:mi:ss
	private String exSDBefore;	//format dd-mm-yyyy HH:mi:ss
	private String pickupDate;	//format dd-mm-yyyy HH:mi:ss
	private String fastTrackType;
	private String singleMultiType;
	private String fulfilmentType;
	private String fnsku;
	private String giftLabelType;
	private String giftWrapType;
	private String hazmatType;
	private String liquidContainsType;
	private int batchSize;
	private String username;
	private String orderIds;
	public int getEtailorId() {
		return etailorId;
	}
	public void setEtailorId(int etailorId) {
		this.etailorId = etailorId;
	}
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public String getExSDAfter() {
		return exSDAfter;
	}
	public void setExSDAfter(String exSDAfter) {
		this.exSDAfter = exSDAfter;
	}
	public String getExSDBefore() {
		return exSDBefore;
	}
	public void setExSDBefore(String exSDBefore) {
		this.exSDBefore = exSDBefore;
	}
	public String getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}
	public String getFastTrackType() {
		return fastTrackType;
	}
	public void setFastTrackType(String fastTrackType) {
		this.fastTrackType = fastTrackType;
	}
	public String getSingleMultiType() {
		return singleMultiType;
	}
	public void setSingleMultiType(String singleMultiType) {
		this.singleMultiType = singleMultiType;
	}
	public String getFulfilmentType() {
		return fulfilmentType;
	}
	public void setFulfilmentType(String fulfilmentType) {
		this.fulfilmentType = fulfilmentType;
	}
	public String getFnsku() {
		return fnsku;
	}
	public void setFnsku(String fnsku) {
		this.fnsku = fnsku;
	}
	public String getGiftLabelType() {
		return giftLabelType;
	}
	public void setGiftLabelType(String giftLabelType) {
		this.giftLabelType = giftLabelType;
	}
	public String getGiftWrapType() {
		return giftWrapType;
	}
	public void setGiftWrapType(String giftWrapType) {
		this.giftWrapType = giftWrapType;
	}
	public String getHazmatType() {
		return hazmatType;
	}
	public void setHazmatType(String hazmatType) {
		this.hazmatType = hazmatType;
	}
	public String getLiquidContainsType() {
		return liquidContainsType;
	}
	public void setLiquidContainsType(String liquidContainsType) {
		this.liquidContainsType = liquidContainsType;
	}
	public int getBatchSize() {
		return batchSize;
	}
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOrderIds() {
		return orderIds;
	}
	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}
}