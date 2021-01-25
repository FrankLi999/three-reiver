package com.threeriver.datafeed.transaction.port.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MessageSender {
	private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Value(value = "${message.topic.name}")
	private String accountTopic;

	/**
	 * Account account = new Account();
		account.setAccountNumber("act1");
		account.setTimeStamp(12345);
		account.setBalance(12);

		Message<account> message = new Message<>("AccountFeedEvent", account);
		messageSender.send(message);
	 * @param m
	 */
	public void send(Message<?> m) {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering");
		}
		try {
			// avoid too much magic and transform ourselves
			ObjectMapper mapper = new ObjectMapper();
			String jsonMessage = mapper.writeValueAsString(m);
			// wrap into a proper message for the transport (Kafka/Rabbit) and send it
			//output.send(MessageBuilder.withPayload(jsonMessage).setHeader("type", m.getMessageType()).build());
			org.springframework.messaging.Message<String> kafkaMessage = MessageBuilder
					.withPayload(jsonMessage)
					.setHeader(KafkaHeaders.TOPIC, accountTopic)
					.setHeader(KafkaHeaders.MESSAGE_KEY, MessageConstants.TRANSACTION_MESSAGE_KEY)
					.setHeader(MessageConstants.MESSAGE_TYPE, m.getMessageType())
					.build();
			
			ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(kafkaMessage);
			future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
		        @Override
		        public void onSuccess(SendResult<String, String> result) {
		        	logger.info("Sent message=[" + jsonMessage + 
		              "] with offset=[" + result.getRecordMetadata().offset() + "]");
		        }
		        @Override
		        public void onFailure(Throwable ex) {
		        	logger.error("Unable to send message=["
		              + jsonMessage + "] due to : " + ex.getMessage());
		        }
		    });
		} catch (Exception e) {
			logger.error("Could not tranform and send message", e);
			throw new RuntimeException("Could not tranform and send message due to: " + e.getMessage(), e);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Exiting");
		}
	}
}
