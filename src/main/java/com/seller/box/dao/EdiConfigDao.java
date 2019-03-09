package com.seller.box.dao;

public interface EdiConfigDao {
	Long getId(String pSeqName);

	String getTransControlNumber();
	
	String getMessageControlNumber();
	
	String getInventoryControlNumber();

	String getAdjustmentControlId();
	
	String getMarketplaceId(String locationCode);
	
	String getReceivingPartyId(String locationCode);
	
	String getSendingPartyId(String locationCode);
	
	//String getVendorPartyId(String locationCode); use getSendingPartyId() for vendorPartyId
}
