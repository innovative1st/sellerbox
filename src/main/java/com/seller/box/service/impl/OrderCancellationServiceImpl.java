package com.seller.box.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.seller.box.core.OC;
import com.seller.box.core.ServiceResponse;
import com.seller.box.dao.EdiConfigDao;
import com.seller.box.dao.ProductSearchDao;
import com.seller.box.service.OrderCancellationService;
import com.seller.box.utils.KafkaUtils;
import com.seller.box.utils.SBConstant;

@Service
public class OrderCancellationServiceImpl implements OrderCancellationService {
	private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);
	@Autowired
	KafkaUtils kafkaUtils;
	@Autowired
	EdiConfigDao ediConfigDao;
	@Autowired
	ProductSearchDao productSearchDao;
	@Override
	@Async
	public void createOrderCancellationAsync(String requestId, OC oc, String orderType, int etailorId, String user) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrderCancellationAsync(String requestId, OC oc, String orderType, int etailorId, String user)---START");
		createOrderCancellation(requestId, oc, orderType, etailorId, user);
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrderCancellationAsync(String requestId, OC oc, String orderType, int etailorId, String user)---END");
	}
	@Override
	public ServiceResponse createOrderCancellation(String requestId, OC oc, String orderType, int etailorId, String user) {
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrderCancellation(String requestId, OC oc, String orderType, int etailorId, String user)---START");
		ServiceResponse response = new ServiceResponse();
		if(oc != null) {
			
			
		} else {
			response.setStatus(SBConstant.TXN_STATUS_FAILURE);
			response.setResponseCode(SBConstant.TXN_RESPONSE_CODE_FAILURE);
			response.setErrorDesc("Order cancellation creation failed, Order cancellation metadata is null.");
		}
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrderCancellation(...) --> Response = " + response.toString());
		logger.info(requestId + SBConstant.LOG_SEPRATOR + "createOrderCancellation(String requestId, OC oc, String orderType, int etailorId, String user)---END");
		return response;
	}
	@Override
	public Long isOrderCancellationExist(String requestId, int etailorId, String warehouseCode, String shipmentId) {
		// TODO Auto-generated method stub
		return null;
	}
}
