package com.seller.box.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "INVENTORY_LOCATION", catalog = "INVENTORY")
public class EdiInventoryLocation {
	@Id
	@Column(name = "LOCATION_ID", unique = true, updatable = false)
	private int locationId;
	@Column(name = "LOCATION_CODE", unique = true, updatable = false)
	private String locationCode;
	@Column(name = "LOCATION_NAME")
	private String locationName;
	@Column(name = "ETAILOR_ID")
	private int etailorId;
	@Column(name = "IS_AUTO_INBOUND")
	private char isAutoInbound;
	@Column(name = "THRESHOLD_LIMIT")
	private int thresholdLimit;
	@Column(name = "MIN_THRESHOLD_LEVEL")
	private int minThresholdLimit;
	@Column(name = "IS_PICKLIST_AUTO")
	private char isPicklistAuto;
	@Column(name = "PICKLIST_ORDER_COUNT")
	private int picklistOrderCount;
	@Column(name = "PICKLIST_TIME_INTERVAL")
	private int picklistTimeInterval;
	@Column(name = "CREATED_BY")
	private String createdBy;
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	@Column(name = "UPDATE_REASON")
	private String reasonForUpdate;
	@Column(name = "MARKETPLACE_ID")
	private String marketplaceId;
	@Column(name = "SENDING_PARTY_ID")
	private String sendingPartyId;
	@Column(name = "RECEIVING_PARTY_ID")
	private String receivingPartyId;
	@Column(name = "AWS_ACCOUNT_ID")
	private String awsAccountId;
	@Column(name = "AWS_ACCESS_KEY")
	private String wasAccessKey;
	@Column(name = "AWS_SECRET_KEY")
	private String awsSecretKey;
	@Column(name = "IS_MANUAL_PICKLIST_ENABLE")
	private char isManualPicklistEnable;
	@Column(name = "MANUAL_PICKLIST_CREATED_BY")
	private String manualPicklistCreatedBy;
	@Column(name = "MANUAL_PICKLIST_CREATED_DT")
	private Date manualPicklistCreatedDate;
	@Column(name = "OFR_ACCEPT_WAIT_TIME")
	private String ofrAcceptWaitTime;
	@Column(name = "UDFT1")
	private String udft1;
	@Column(name = "UDFT2")
	private String udft2;
	@Column(name = "UDFT3")
	private String udft3;
	@Column(name = "UDFT4")
	private String udft4;
	@Column(name = "UDFT5")
	private String udft5;
	@Column(name = "UDFN1")
	private int udfn1;
	@Column(name = "UDFN2")
	private int udfn2;
	@Column(name = "UDFN3")
	private int udfn3;
	@Column(name = "UDFN4")
	private int udfn4;
	@Column(name = "UDFN5")
	private int udfn5;
	
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public int getEtailorId() {
		return etailorId;
	}
	public void setEtailorId(int etailorId) {
		this.etailorId = etailorId;
	}
	public char getIsAutoInbound() {
		return isAutoInbound;
	}
	public void setIsAutoInbound(char isAutoInbound) {
		this.isAutoInbound = isAutoInbound;
	}
	public int getThresholdLimit() {
		return thresholdLimit;
	}
	public void setThresholdLimit(int thresholdLimit) {
		this.thresholdLimit = thresholdLimit;
	}
	public int getMinThresholdLimit() {
		return minThresholdLimit;
	}
	public void setMinThresholdLimit(int minThresholdLimit) {
		this.minThresholdLimit = minThresholdLimit;
	}
	public char getIsPicklistAuto() {
		return isPicklistAuto;
	}
	public void setIsPicklistAuto(char isPicklistAuto) {
		this.isPicklistAuto = isPicklistAuto;
	}
	public int getPicklistOrderCount() {
		return picklistOrderCount;
	}
	public void setPicklistOrderCount(int picklistOrderCount) {
		this.picklistOrderCount = picklistOrderCount;
	}
	public int getPicklistTimeInterval() {
		return picklistTimeInterval;
	}
	public void setPicklistTimeInterval(int picklistTimeInterval) {
		this.picklistTimeInterval = picklistTimeInterval;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getReasonForUpdate() {
		return reasonForUpdate;
	}
	public void setReasonForUpdate(String reasonForUpdate) {
		this.reasonForUpdate = reasonForUpdate;
	}
	public String getMarketplaceId() {
		return marketplaceId;
	}
	public void setMarketplaceId(String marketplaceId) {
		this.marketplaceId = marketplaceId;
	}
	public String getSendingPartyId() {
		return sendingPartyId;
	}
	public void setSendingPartyId(String sendingPartyId) {
		this.sendingPartyId = sendingPartyId;
	}
	public String getReceivingPartyId() {
		return receivingPartyId;
	}
	public void setReceivingPartyId(String receivingPartyId) {
		this.receivingPartyId = receivingPartyId;
	}
	public String getAwsAccountId() {
		return awsAccountId;
	}
	public void setAwsAccountId(String awsAccountId) {
		this.awsAccountId = awsAccountId;
	}
	public String getWasAccessKey() {
		return wasAccessKey;
	}
	public void setWasAccessKey(String wasAccessKey) {
		this.wasAccessKey = wasAccessKey;
	}
	public String getAwsSecretKey() {
		return awsSecretKey;
	}
	public void setAwsSecretKey(String awsSecretKey) {
		this.awsSecretKey = awsSecretKey;
	}
	public char getIsManualPicklistEnable() {
		return isManualPicklistEnable;
	}
	public void setIsManualPicklistEnable(char isManualPicklistEnable) {
		this.isManualPicklistEnable = isManualPicklistEnable;
	}
	public String getManualPicklistCreatedBy() {
		return manualPicklistCreatedBy;
	}
	public void setManualPicklistCreatedBy(String manualPicklistCreatedBy) {
		this.manualPicklistCreatedBy = manualPicklistCreatedBy;
	}
	public Date getManualPicklistCreatedDate() {
		return manualPicklistCreatedDate;
	}
	public void setManualPicklistCreatedDate(Date manualPicklistCreatedDate) {
		this.manualPicklistCreatedDate = manualPicklistCreatedDate;
	}
	public String getOfrAcceptWaitTime() {
		return ofrAcceptWaitTime;
	}
	public void setOfrAcceptWaitTime(String ofrAcceptWaitTime) {
		this.ofrAcceptWaitTime = ofrAcceptWaitTime;
	}
	public String getUdft1() {
		return udft1;
	}
	public void setUdft1(String udft1) {
		this.udft1 = udft1;
	}
	public String getUdft2() {
		return udft2;
	}
	public void setUdft2(String udft2) {
		this.udft2 = udft2;
	}
	public String getUdft3() {
		return udft3;
	}
	public void setUdft3(String udft3) {
		this.udft3 = udft3;
	}
	public String getUdft4() {
		return udft4;
	}
	public void setUdft4(String udft4) {
		this.udft4 = udft4;
	}
	public String getUdft5() {
		return udft5;
	}
	public void setUdft5(String udft5) {
		this.udft5 = udft5;
	}
	public int getUdfn1() {
		return udfn1;
	}
	public void setUdfn1(int udfn1) {
		this.udfn1 = udfn1;
	}
	public int getUdfn2() {
		return udfn2;
	}
	public void setUdfn2(int udfn2) {
		this.udfn2 = udfn2;
	}
	public int getUdfn3() {
		return udfn3;
	}
	public void setUdfn3(int udfn3) {
		this.udfn3 = udfn3;
	}
	public int getUdfn4() {
		return udfn4;
	}
	public void setUdfn4(int udfn4) {
		this.udfn4 = udfn4;
	}
	public int getUdfn5() {
		return udfn5;
	}
	public void setUdfn5(int udfn5) {
		this.udfn5 = udfn5;
	}
}
