package com.seller.box.dao;

public interface EdiConfigDao {
	Long getId(String pSeqName);

	String getTransControlNumber();
	
	String getMessageControlNumber();
	
	String getInventoryControlNumber();

	String getAdjustmentControlId();
	
	String getMarketplaceId(String warehouseCode);
	
	String getReceivingPartyId(String warehouseCode);
	
	String getSendingPartyId(String warehouseCode);
	
	//String getVendorPartyId(String warehouseCode); use getSendingPartyId() for vendorPartyId
}
