package com.threeriver.resource.account.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import com.threeriver.resource.account.AccountApiApplication;
import com.threeriver.resource.account.domain.Account;

@SpringBootTest(classes = AccountApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @JdbcTest
@Sql({ "test/schema.sql", "test/data.sql" })
class AccountRepositoryTest {

	@Autowired
	private AccountRepository acountRepository;

	@Test
	public void testGetAccountByAccountNumber() {

		Optional<Account> account = acountRepository.getAccountByAccountNumber("abc");
		Assertions.assertTrue(account.isPresent());
		Assertions.assertEquals(12.6, account.get().getBalance());
	}
	
	@Test
	public void othertest() {
		// TODO
	}

}
