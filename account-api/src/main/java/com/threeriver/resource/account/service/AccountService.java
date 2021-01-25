package com.threeriver.resource.account.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.threeriver.resource.account.domain.Account;
import com.threeriver.resource.account.repository.AccountRepository;

@Service
@Transactional
public class AccountService {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
	@Autowired
	private AccountRepository accountRepository;
	
	public Optional<Account> getAccountByAccountNumber(String accountNumber) {
		if (logger.isDebugEnabled()) {
			logger.debug("entering");
		}
		
		Optional<Account> account = accountRepository.getAccountByAccountNumber(accountNumber);
		
		if (logger.isDebugEnabled()) {
			logger.debug("exiting");
		}
		return account;
	}
}
