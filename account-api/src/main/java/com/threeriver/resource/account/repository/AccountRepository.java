package com.threeriver.resource.account.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import com.threeriver.resource.account.domain.Account;

@Component
public class AccountRepository {
	private static final Logger logger = LoggerFactory.getLogger(AccountRepository.class);
	
	@Autowired
	@Qualifier("accountJdbcTemplate")
	private NamedParameterJdbcTemplate accountJdbcTemplate;
	
	private static final String findByAccountNumberSql = 
			  "SELECT ID, ACCOUNT_NUMBER, BALANCE, LAST_UPDATE_TIMSTAMP "
			+ "FROM account "
			+ "WHERE ACCOUNT_NUMBER=:accountNumber ORDER BY LAST_UPDATE_TIMSTAMP DESC LIMIT 1";  
	public Optional<Account> getAccountByAccountNumber(String accountNumber) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("entering");
		}
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("accountNumber", accountNumber);
		
		Account account = null; 
		try {
			account = accountJdbcTemplate.queryForObject(
					findByAccountNumberSql, namedParameters, new AccountRowMapper());
		} catch (EmptyResultDataAccessException e) {
			//logger.info("Not able to find account " + accountNumber, e);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("exiting");
		}
		return (account == null) ? Optional.empty() : Optional.of(account);
		
	}
	

	public class AccountRowMapper implements RowMapper<Account> {
	    @Override
	    public  Account mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	Account account = new Account();
	    	account.setId(rs.getLong("ID"));
	    	account.setAccountNumber(rs.getString("ACCOUNT_NUMBER"));
	    	account.setLastUpdateTimstamp(rs.getTimestamp("LAST_UPDATE_TIMSTAMP"));
	    	account.setBalance(rs.getDouble("BALANCE"));
	    	return account;
	    }
	}
}
