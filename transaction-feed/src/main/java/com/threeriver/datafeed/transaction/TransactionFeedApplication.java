package com.threeriver.datafeed.transaction;

import java.sql.Timestamp;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.threeriver.datafeed.transaction.domain.Transaction;
import com.threeriver.datafeed.transaction.port.message.Message;
import com.threeriver.datafeed.transaction.port.message.MessageConstants;
import com.threeriver.datafeed.transaction.port.message.MessageSender;

@SpringBootApplication
public class TransactionFeedApplication {
	private static final Logger logger = LoggerFactory.getLogger(TransactionFeedApplication.class);
	@Autowired
	private MessageSender sender;
	
	public static void main(String[] args) {
		SpringApplication.run(TransactionFeedApplication.class, args);
	}


	@PostConstruct
    private void initDb() {
		logger.debug("Preload test transaction data");
		Transaction transaction = new Transaction();
		transaction.setAccountNumber("abc");
		transaction.setTransactionTs(Timestamp.valueOf("2020-01-01 10:10:10"));
		transaction.setAmount(89.1);
		transaction.setTransactionType(Transaction.TransactionType.DEPOSIT);
		Message<Transaction> message = new Message<Transaction>(MessageConstants.TRANSACTION_EVEVT_TYPE, transaction);
		sender.send(message);

		transaction = new Transaction();
		transaction.setAccountNumber("abc");
		transaction.setTransactionTs(Timestamp.valueOf("2021-01-01 10:10:10"));
		transaction.setAmount(12.5);
		transaction.setTransactionType(Transaction.TransactionType.WITHDRAW);
		message = new Message<Transaction>(MessageConstants.TRANSACTION_EVEVT_TYPE, transaction);
		sender.send(message);
		
		transaction = new Transaction();
		transaction.setAccountNumber("efg");
		transaction.setTransactionTs(Timestamp.valueOf("2020-01-01 10:10:10"));
		transaction.setAmount(65.6);
		transaction.setTransactionType(Transaction.TransactionType.DEPOSIT);
		message = new Message<Transaction>(MessageConstants.TRANSACTION_EVEVT_TYPE, transaction);
		sender.send(message);

		transaction = new Transaction();
		transaction.setAccountNumber("efg");
		transaction.setTransactionTs(Timestamp.valueOf("2021-01-01 10:10:10"));
		transaction.setAmount(20.1);
		transaction.setTransactionType(Transaction.TransactionType.WITHDRAW);
		message = new Message<Transaction>(MessageConstants.TRANSACTION_EVEVT_TYPE, transaction);
		sender.send(message);
	}
}
