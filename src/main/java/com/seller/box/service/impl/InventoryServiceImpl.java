package com.seller.box.service.impl;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.seller.box.config.EdiConfig;
import com.seller.box.core.OutboundMessage;
import com.seller.box.core.ServiceResponse;
import com.seller.box.dao.EdiConfigDao;
import com.seller.box.dao.EdiInventoryIanDao;
import com.seller.box.dao.ProductSearchDao;
import com.seller.box.entities.EdiInventoryIan;
import com.seller.box.form.EdiInventoryIanForm;
import com.seller.box.service.InventoryService;
import com.seller.box.utils.KafkaUtils;
import com.seller.box.utils.SBConstant;
import com.seller.box.utils.SBUtils;

@Service
public class InventoryServiceImpl implements InventoryService {
	private static final Logger logger = LogManager.getLogger(InventoryServiceImpl.class);
	@Autowired
	KafkaUtils kafkaUtils;
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	EdiConfigDao ediConfigDao;
	@Autowired
	EdiInventoryIanDao inventoryIanDao;
	@Autowired
	ProductSearchDao productSearchDao;
	
//	@Override
//	public ProductDetails getProductInfo(int etailorId, String value) {
//		logger.info("getProductInfo(int etailorId, String value)----START");
//		ProductDetails product = null;
//		try {
//			product = productSearchDao.findProduct(etailorId, value);
//		} catch (Exception e) {
//			logger.info("Exception :: getProductInfo(int etailorId, String value)", e);
//		}
//		logger.info("getProductInfo(int etailorId, String value)----START");
//		return product;
//	}
	
	@Override
	public ServiceResponse makeIAN(EdiInventoryIanForm input) {
		logger.info(input.getRequestId() + SBConstant.LOG_SEPRATOR + "makeIAN(EdiInventoryIanForm input)---START");
		ServiceResponse response = new ServiceResponse();
		response.setRequestId(input.getRequestId());
		EdiInventoryIan ian = null;
		try {
			EdiConfig config = new EdiConfig(input.getRequestId(), ediConfigDao, input.getWarehouseCode(), input.getEtailorId(), SBConstant.MESSAGE_TYPE_IAN);
			ian = new EdiInventoryIan();
			ian.setIanId(config.getTxnId());
			ian.setLocationCode(config.getWarehouseCode());
			ian.setEtailorId(config.getEtailorId());
			ian.setMessageCount(config.getMessageCount());
			ian.setTransmissionDate(new Date());
			ian.setTransCreationDate(config.getTransCreationDate());
			ian.setTransIsTest(config.getTransIsTest());
			ian.setTransControlNumber(config.getTransControlNumber());
			ian.setMessageCount(config.getMessageCount());
			ian.setTransStructureVersion(config.getTransStructureVersion());
			ian.setTransReceivingPartyId(config.getTransReceivingPartyId());
			ian.setTransSendingPartyId(config.getTransSendingPartyId());
			ian.setMessageType(config.getMessageType());
			ian.setMessageIsTest(config.getMessageIsTest());
			ian.setMessageReceivingPartyId(config.getMessageReceivingPartyId());
			ian.setMessageSendingPartyId(config.getMessageSendingPartyId());
			ian.setMessageStructureVersion(config.getMessageStructureVersion());
			ian.setMessageCreationDate(config.getMessageCreationDate());
			ian.setMessageControlNumber(config.getMessageControlNumber());
			ian.setAdjustmentType(input.getAdjustmentType());
			ian.setItemType(SBUtils.getPropertyValue("ITEM_TYPE_IAN"));
			ian.setItemId(input.getFnsku());
			ian.setSkuCode(input.getSkuCode());
			ian.setQuantity(input.getQuantity());
			ian.setUnitOfMeasure(SBUtils.getPropertyValue("UNIT_OF_MEASURE_IAN"));
			ian.setVendorPartyType(config.getVendorPartyType());
			ian.setVendorPartyId(config.getVendorPartyId());
			ian.setAdjustmentControlId(config.getAdjustmentControlId());
			ian.setInventoryEffectiveDateTime(config.getMessageCreationDate());
			ian.setPurchaseOrderNumber(input.getPurchaseOrderNumber());
			ian.setInventorySourceLocation(input.getInventorySourceLocation());
			ian.setInventoryDestinationLocation(input.getInventoryDestinationLocation());
			ian.setIanFilepath(config.getArchiveFilepath());
			ian.setMessageError(null);
			ian.setRunStatus(0);
			ian.setTxnStatus(SBConstant.TXN_STATUS_INIT);
			ian.setIsIanUpdated(0);
			ian.setInvUpdateErrorMsg(null);
			ian.setCreatedDate(new Date());
			ian.setCreatedBy(input.getCreatedBy());
			ian.setRequestId(config.getRequestId());
			
			inventoryIanDao.save(ian);
			response.setStatus(SBConstant.TXN_STATUS_SUCCESS);
			response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_SUCCESS);
			response.setUniqueKey(ian.getIanId().toString());
			response.setResponseMessage("Ian created successfully.");
		} catch (Exception e) {
			logger.error(input.getRequestId() + SBConstant.LOG_SEPRATOR + "Exception :: makeIAN(EdiInventoryIanForm input)", e);
			response.setStatus(SBConstant.TXN_STATUS_EXCEPTION);
			response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_EXCEPTION);
			response.setErrorDesc("IAN creation failed, General exception occured. \n"+e.getMessage());
		} finally {
			if (ian != null && response.getStatus().equalsIgnoreCase(SBConstant.TXN_STATUS_SUCCESS)) {
				String topic = SBConstant.MESSAGE_TYPE_IAN;
				OutboundMessage message = new OutboundMessage();
				message.setRequestId(input.getRequestId());
				message.setMessageType(topic);
				message.setIan(ian);
				message.setStatus(SBConstant.TXN_STATUS_INIT);
				logger.info("IAN Kafka Request >>>> " + message.toString());
				boolean status = kafkaUtils.send(input.getRequestId(), topic, message);
				logger.info(input.getRequestId() + SBConstant.LOG_SEPRATOR + "IAN #"+ian.getIanId()+SBConstant.LOG_SEPRATOR+"Kafka produce "+SBConstant.LOG_SEPRATOR+(status == true ? "SUCCESS" : "FAILED"));
				try {
					ian.setRunStatus(1);
					ian.setTxnStatus(SBConstant.TXN_STATUS_PRODUCE);
					inventoryIanDao.save(ian);
				} catch (Exception e) {
					logger.error(input.getRequestId() + SBConstant.LOG_SEPRATOR + " updation RunStatus = 1 failed for ianId = " + ian.getIanId());
				}
			} else {
				logger.error(input.getRequestId() + SBConstant.LOG_SEPRATOR + "IAN #"+ian.getIanId()+SBConstant.LOG_SEPRATOR+"not Kafka produce due to IAN create response = "+response.getStatus());
			}
		}
		logger.info(input.getRequestId() + SBConstant.LOG_SEPRATOR + "makeIAN(EdiInventoryIanForm input)---END");
		return response;
	}

}
