package com.seller.box.config;

import java.util.UUID;

import com.seller.box.dao.EdiConfigDao;
import com.seller.box.utils.SBConstant;
import com.seller.box.utils.SBUtils;

public class EdiConfig {
	public EdiConfig(String requestId, EdiConfigDao configDao, String warehouseCode, int etailorId, String messageType) {
		this.warehouseCode 				= warehouseCode;
		this.etailorId    				= etailorId;
		this.messageType  				= messageType;
		this.txnId						= configDao.getId(messageType);
		if (requestId != null) {
			this.requestId				= requestId;
		} else {
			this.requestId = UUID.randomUUID().toString();
		}
		this.transCreationDate			= SBUtils.getTxnSysDateTime();
		this.transIsTest				= SBUtils.getPropertyValue("IS_TEST");
		this.messageCount 				= Integer.parseInt(SBUtils.getPropertyValue("MESSAGE_COUNT_IAN"));
		this.transStructureVersion		= SBUtils.getPropertyValue("TRANS_STRUCTURE_VERSION");
		this.messageIsTest				= SBUtils.getPropertyValue("IS_TEST");
		this.messageStructureVersion	= SBUtils.getPropertyValue("MESSAGE_STRUCTURE_VERSION");
		this.messageCreationDate		= this.transCreationDate;
		this.transControlNumber			= configDao.getTransControlNumber();
		this.transReceivingPartyId		= configDao.getReceivingPartyId(warehouseCode);
		this.transSendingPartyId 		= configDao.getSendingPartyId(warehouseCode);
		this.messageReceivingPartyId 	= this.transReceivingPartyId;
		this.messageSendingPartyId		= this.transSendingPartyId;
		this.messageControlNumber		= configDao.getMessageControlNumber();
		this.vendorPartyType			= SBUtils.getPropertyValue("VENDOR_PARTY_TYPE");
		this.vendorPartyId				= this.messageSendingPartyId;
		this.archiveFilepath 			= SBUtils.getPropertyValue("ARCHIVE_FILEPATH_"+messageType.toUpperCase());
		if(messageType.equalsIgnoreCase(SBConstant.MESSAGE_TYPE_IAN)) {
			this.adjustmentControlId	= configDao.getAdjustmentControlId();
			this.marketplaceId			= configDao.getMarketplaceId(warehouseCode);
		} else if(messageType.equalsIgnoreCase(SBConstant.MESSAGE_TYPE_ILN)) {
			this.inventoryControlNumber	= configDao.getInventoryControlNumber();
		} else if(messageType.equalsIgnoreCase(SBConstant.MESSAGE_TYPE_OFR) || messageType.equalsIgnoreCase(SBConstant.MESSAGE_TYPE_ASN)) {
			try {
				this.responseCondition 		= SBUtils.getPropertyValue("ofr.response.condition");
			} catch (Exception e) {
				this.responseCondition 		= SBConstant.OFR_RESPONSE_CONDITION;
			}
			try {
				this.resultCode				= SBUtils.getPropertyValue("ofr.result.code");
			} catch (Exception e) {
				this.resultCode				= SBConstant.OFR_RESULT_CODE;
			}
			if (messageType.equalsIgnoreCase(SBConstant.MESSAGE_TYPE_OFR)) {
				try {
					this.resultDescription = SBUtils.getPropertyValue("ofr.result.desc");
				} catch (Exception e) {
					this.resultDescription = SBConstant.OFR_RESULT_DESC;
				} 
			}
			if (messageType.equalsIgnoreCase(SBConstant.MESSAGE_TYPE_ASN)) {
				this.readyToShipDate   = this.transCreationDate;
			}
		}
	}
	private Long txnId;
	private String requestId;
	private String warehouseCode;
	private String messageType;
	private int etailorId;
	private String archiveFilepath;
	private String transCreationDate;
	private String transIsTest;
	private String transControlNumber;
	private int messageCount;
	private String transStructureVersion;
	private String transReceivingPartyId;
	private String transSendingPartyId;
	private String messageIsTest;
	private String messageReceivingPartyId;
	private String messageSendingPartyId;
	private String messageStructureVersion;
	private String messageCreationDate;
	private String messageControlNumber;
	private String vendorPartyType;
	private String vendorPartyId;
	private String adjustmentControlId;
	private String marketplaceId;
	private String inventoryControlNumber;
	private String responseCondition;
	private String resultCode;
	private String resultDescription;
	private String readyToShipDate;
	public Long getTxnId() {
		return txnId;
	}
	public void setTxnId(Long txnId) {
		this.txnId = txnId;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public int getEtailorId() {
		return etailorId;
	}
	public void setEtailorId(int etailorId) {
		this.etailorId = etailorId;
	}
	public String getArchiveFilepath() {
		return archiveFilepath;
	}
	public void setArchiveFilepath(String archiveFilepath) {
		this.archiveFilepath = archiveFilepath;
	}
	public String getTransCreationDate() {
		return transCreationDate;
	}
	public void setTransCreationDate(String transCreationDate) {
		this.transCreationDate = transCreationDate;
	}
	public String getTransIsTest() {
		return transIsTest;
	}
	public void setTransIsTest(String transIsTest) {
		this.transIsTest = transIsTest;
	}
	public String getTransControlNumber() {
		return transControlNumber;
	}
	public void setTransControlNumber(String transControlNumber) {
		this.transControlNumber = transControlNumber;
	}
	public int getMessageCount() {
		return messageCount;
	}
	public void setMessageCount(int messageCount) {
		this.messageCount = messageCount;
	}
	public String getTransStructureVersion() {
		return transStructureVersion;
	}
	public void setTransStructureVersion(String transStructureVersion) {
		this.transStructureVersion = transStructureVersion;
	}
	public String getTransReceivingPartyId() {
		return transReceivingPartyId;
	}
	public void setTransReceivingPartyId(String transReceivingPartyId) {
		this.transReceivingPartyId = transReceivingPartyId;
	}
	public String getTransSendingPartyId() {
		return transSendingPartyId;
	}
	public void setTransSendingPartyId(String transSendingPartyId) {
		this.transSendingPartyId = transSendingPartyId;
	}
	public String getMessageIsTest() {
		return messageIsTest;
	}
	public void setMessageIsTest(String messageIsTest) {
		this.messageIsTest = messageIsTest;
	}
	public String getMessageReceivingPartyId() {
		return messageReceivingPartyId;
	}
	public void setMessageReceivingPartyId(String messageReceivingPartyId) {
		this.messageReceivingPartyId = messageReceivingPartyId;
	}
	public String getMessageSendingPartyId() {
		return messageSendingPartyId;
	}
	public void setMessageSendingPartyId(String messageSendingPartyId) {
		this.messageSendingPartyId = messageSendingPartyId;
	}
	public String getMessageStructureVersion() {
		return messageStructureVersion;
	}
	public void setMessageStructureVersion(String messageStructureVersion) {
		this.messageStructureVersion = messageStructureVersion;
	}
	public String getMessageCreationDate() {
		return messageCreationDate;
	}
	public void setMessageCreationDate(String messageCreationDate) {
		this.messageCreationDate = messageCreationDate;
	}
	public String getMessageControlNumber() {
		return messageControlNumber;
	}
	public void setMessageControlNumber(String messageControlNumber) {
		this.messageControlNumber = messageControlNumber;
	}
	public String getVendorPartyType() {
		return vendorPartyType;
	}
	public void setVendorPartyType(String vendorPartyType) {
		this.vendorPartyType = vendorPartyType;
	}
	public String getVendorPartyId() {
		return vendorPartyId;
	}
	public void setVendorPartyId(String vendorPartyId) {
		this.vendorPartyId = vendorPartyId;
	}
	public String getAdjustmentControlId() {
		return adjustmentControlId;
	}
	public void setAdjustmentControlId(String adjustmentControlId) {
		this.adjustmentControlId = adjustmentControlId;
	}
	public String getMarketplaceId() {
		return marketplaceId;
	}
	public void setMarketplaceId(String marketplaceId) {
		this.marketplaceId = marketplaceId;
	}
	public String getInventoryControlNumber() {
		return inventoryControlNumber;
	}
	public void setInventoryControlNumber(String inventoryControlNumber) {
		this.inventoryControlNumber = inventoryControlNumber;
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
	public String getReadyToShipDate() {
		return readyToShipDate;
	}
	public void setReadyToShipDate(String readyToShipDate) {
		this.readyToShipDate = readyToShipDate;
	}
	
	
}
