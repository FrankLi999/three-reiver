package com.threeriver.datafeed.account;

import java.sql.Timestamp;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.threeriver.datafeed.account.domain.Account;
import com.threeriver.datafeed.account.port.message.Message;
import com.threeriver.datafeed.account.port.message.MessageConstants;
import com.threeriver.datafeed.account.port.message.MessageSender;

@SpringBootApplication
public class AccountFeedApplication {
	private static final Logger logger = LoggerFactory.getLogger(AccountFeedApplication.class);
	
	@Autowired
	private MessageSender sender;
	public static void main(String[] args) {
		SpringApplication.run(AccountFeedApplication.class, args);
	}

	@PostConstruct
    private void initDb() {
		logger.debug("Preload test account data");
		Account account = new Account();
		account.setAccountNumber("abc");
		account.setLastUpdateTimstamp(Timestamp.valueOf("2020-01-01 10:10:10"));
		account.setBalance(89.1);
		
		Message<Account> message = new Message<Account>(MessageConstants.ACCOUNT_EVEVT_TYPE, account);
		sender.send(message);

		account = new Account();
		account.setAccountNumber("abc");
		account.setLastUpdateTimstamp(Timestamp.valueOf("2021-01-01 10:10:10"));
		account.setBalance(12.5);
		message = new Message<Account>(MessageConstants.ACCOUNT_EVEVT_TYPE, account);
		sender.send(message);
		
		account = new Account();
		account.setAccountNumber("efg");
		account.setLastUpdateTimstamp(Timestamp.valueOf("2020-01-01 10:10:10"));
		account.setBalance(60.5);
		message = new Message<Account>(MessageConstants.ACCOUNT_EVEVT_TYPE, account);
		sender.send(message);

		account = new Account();
		account.setAccountNumber("efg");
		account.setLastUpdateTimstamp(Timestamp.valueOf("2021-01-01 10:10:10"));
		account.setBalance(20.6);
		message = new Message<Account>(MessageConstants.ACCOUNT_EVEVT_TYPE, account);
		sender.send(message);
	}
}
