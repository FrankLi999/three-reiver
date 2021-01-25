package com.threeriver.datafeed.account.port.message;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.threeriver.datafeed.account.domain.Account;
import com.threeriver.datafeed.account.port.persistence.AccountRepository;

@Component
public class MessageListener {
	private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);
	
	@Autowired
	private AccountRepository repository;

	@KafkaListener(topics = "${message.topic.name}", containerFactory = "kafkaListenerContainerFactory")
	@Transactional
	public void messageReceived(
			@Payload  String jsonMessage,
			@Header("type") String messageType) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering");
			logger.debug("Event type " + messageType);
		}

		if (messageType.equals(MessageConstants.ACCOUNT_EVEVT_TYPE)) {
			this.accountReceived(jsonMessage);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Exiting");
		}
	}
	
	/**  
	 * Handles incoming OrderPlacedEvents.
	 * 
	 * Using the conditional {@link StreamListener} from
	 * https://github.com/spring-cloud/spring-cloud-stream/blob/master/spring-cloud-stream-core-docs/src/main/asciidoc/spring-cloud-stream-overview.adoc
	 * in a way close to what Axion would do (see e.g.
	 * https://dturanski.wordpress.com/2017/03/26/spring-cloud-stream-for-event-driven-architectures/)
	 */
	// @KafkaListener(topics = "${message.topic.name}", containerFactory = "orderPlacedEventKafkaListenerContainerFactory")
	// @Transactional
	protected void accountReceived(@Payload String jsonMessage) throws JsonParseException, JsonMappingException, IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("Entering");
		}
		
		Message<Account> message = new ObjectMapper().readValue(jsonMessage, new TypeReference<Message<Account>>() {});
		Account account = message.getPayload();
		try {
			repository.save(account);
		} catch (Exception e) {
			//TDOD:
			System.out.println("failed to save account:" + account);
			e.printStackTrace();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Exiting");
		}
	}

}
