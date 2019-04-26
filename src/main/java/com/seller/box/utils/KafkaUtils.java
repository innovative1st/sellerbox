package com.seller.box.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.seller.box.core.OutboundMessage;

@Component
public class KafkaUtils {
	private static final Logger logger = LogManager.getLogger(KafkaUtils.class);
	@Autowired
    private KafkaTemplate<String, OutboundMessage> kafkaTemplate;
	
	public boolean send(String requestId, String topic, OutboundMessage message) {
		logger.info(requestId+SBConstant.LOG_SEPRATOR+"KafkaUtils.send(String topic, Object message)----START");
		boolean status = true;
        try {
        	// the KafkaTemplate provides asynchronous send methods returning a Future
            ListenableFuture<SendResult<String, OutboundMessage>> future = kafkaTemplate.send(topic, message);

            // register a callback with the listener to receive the result of the send asynchronously
            future.addCallback(new ListenableFutureCallback<SendResult<String, OutboundMessage>>() {

                @Override
                public void onSuccess(SendResult<String, OutboundMessage> result) {
                    logger.info("sent message='{}' with offset={}", message, result.getRecordMetadata().offset());
                }

                @Override
                public void onFailure(Throwable ex) {
                    logger.error("unable to send message="+message.toString(), ex);
                }
            });
		} catch (Exception e) {
			status = false;
			logger.error(requestId+SBConstant.LOG_SEPRATOR+"KafkaUtils.send(String topic, Object message)", e);
		}
        logger.info(requestId+SBConstant.LOG_SEPRATOR+"KafkaUtils.send(String topic, Object message)----END");
        return status;
    }
}
