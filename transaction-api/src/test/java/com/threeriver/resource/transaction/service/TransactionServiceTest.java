package com.threeriver.resource.transaction.service;

import static org.mockito.BDDMockito.given;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.threeriver.resource.transaction.dto.Transaction;
import com.threeriver.resource.transaction.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
	@InjectMocks
	TransactionService transactionService;
	
    @Mock
    private TransactionRepository mockRepository;
    
    
	@Test
	void testGetTransactionByDates() {
		Transaction tx1 = new Transaction();
		Transaction tx2 = new Transaction();
		List<Transaction> expectedTransactions = Arrays.asList(tx1, tx2);
		
		given(mockRepository.getTransactionByDates(ArgumentMatchers.any(String.class), 
				ArgumentMatchers.any(Timestamp.class), ArgumentMatchers.any(Timestamp.class))).willReturn(expectedTransactions);
		List<Transaction> transactions = transactionService.getTransactionByDates("abc", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
		Assertions.assertEquals(expectedTransactions.size(), transactions.size());
	}

	@Test
	void testGetTransactionByTansactionTypeAndDates() {
		Transaction tx1 = new Transaction();
		Transaction tx2 = new Transaction();
		List<Transaction> expectedTransactions = Arrays.asList(tx1, tx2);
		
		given(mockRepository.getTransactionByTansactionTypeAndDates(ArgumentMatchers.any(String.class), ArgumentMatchers.any(String.class), 
				ArgumentMatchers.any(Timestamp.class), ArgumentMatchers.any(Timestamp.class))).willReturn(expectedTransactions);
		List<Transaction> transactions = transactionService.getTransactionByTansactionTypeAndDates("abc", "DEPOSIT", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));
		Assertions.assertEquals(expectedTransactions.size(), transactions.size());
	}

}
