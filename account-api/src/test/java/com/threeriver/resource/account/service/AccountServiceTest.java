package com.threeriver.resource.account.service;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.threeriver.resource.account.domain.Account;
import com.threeriver.resource.account.repository.AccountRepository;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
	@InjectMocks
	AccountService accountService;
	
    @Mock
    private AccountRepository mockRepository;

    @Test
	void testGetAccountByAccountNumber() {
		Account expected = new Account();
		expected.setAccountNumber("abc");
		expected.setBalance(12.5);
		given(mockRepository.getAccountByAccountNumber(ArgumentMatchers.any(String.class))).willReturn(Optional.of(expected));
		Account account = accountService.getAccountByAccountNumber("abc").get();
		Assertions.assertEquals(expected.getAccountNumber(), account.getAccountNumber());
		Assertions.assertEquals(expected.getBalance(), account.getBalance());
	}

	@Test
	void testGetEmptyAccountByAccountNumber() {
		given(mockRepository.getAccountByAccountNumber(ArgumentMatchers.any(String.class))).willReturn(Optional.empty());
		Assertions.assertTrue(mockRepository.getAccountByAccountNumber("abc").isEmpty());
	}
}
