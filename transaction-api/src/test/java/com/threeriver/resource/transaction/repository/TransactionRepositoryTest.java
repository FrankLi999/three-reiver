package com.threeriver.resource.transaction.repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import com.threeriver.resource.transaction.TransactionApiApplication;
import com.threeriver.resource.transaction.dto.Transaction;

@SpringBootTest(classes = TransactionApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @JdbcTest
@Sql({ "test/schema.sql", "test/data.sql" })
class TransactionRepositoryTest {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Test
	void testGetTransactionByDates() {
		List<Transaction> transactions = transactionRepository.getTransactionByDates("abc", Timestamp.valueOf("2019-12-31 00:00:00"), Timestamp.valueOf("2021-12-31 00:00:00"));
		Assertions.assertEquals(2, transactions.size());
	}

	@Test
	void testGetTransactionByTansactionTypeAndDates() {
		List<Transaction> transactions = transactionRepository.getTransactionByTansactionTypeAndDates("abc", "DEPOSIT", Timestamp.valueOf("2019-12-31 00:00:00"), Timestamp.valueOf("2021-12-31 00:00:00"));
		Assertions.assertEquals(1, transactions.size());

	}

	@Test
	void otherTest() {
		// TODO
	}

}
