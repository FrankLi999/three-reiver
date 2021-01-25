package com.threeriver.resource.transaction.service;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.threeriver.resource.transaction.dto.Transaction;
import com.threeriver.resource.transaction.repository.TransactionRepository;

@Service
@Transactional
public class TransactionService {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionRepository.class);
	
	@Autowired
	private TransactionRepository transactionRepository;
	

	public List<Transaction> getTransactionByDates(String accountNumber, Timestamp begineDate, Timestamp endDate) {
		if (logger.isDebugEnabled()) {
			logger.debug("entering");
		}
		List<Transaction> transactions = transactionRepository.getTransactionByDates(accountNumber, begineDate, endDate);
		
		if (logger.isDebugEnabled()) {
			logger.debug("exting");
		}
		return transactions;
	}
	
	@Transactional
	public List<Transaction> getTransactionByTansactionTypeAndDates(String accountNumber, String transactionType, 
			Timestamp begineDate, Timestamp endDate) {
		if (logger.isDebugEnabled()) {
			logger.debug("entering");
		}
		List<Transaction> transactions = transactionRepository.getTransactionByTansactionTypeAndDates(accountNumber, 
				transactionType, begineDate, endDate);
		if (logger.isDebugEnabled()) {
			logger.debug("exting");
		}
		return transactions;
	}
}
