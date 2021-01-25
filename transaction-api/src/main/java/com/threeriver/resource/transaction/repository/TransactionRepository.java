package com.threeriver.resource.transaction.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import com.threeriver.resource.transaction.dto.Transaction;

@Component
public class TransactionRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionRepository.class);
	@Autowired
	@Qualifier("transactionJdbcTemplate")
	private NamedParameterJdbcTemplate transactionJdbcTemplate;
	
	private static final String findByDatesSql = 
			  "SELECT id, account_Number, transaction_Ts, transaction_Type, amount "
			+ "FROM transaction "
			+ "WHERE account_Number=:accountNumber AND transaction_Ts BETWEEN :beginDate AND :endDate";  
	
	private static final String findByTransactionTypeAndDatesSql = 
			  "SELECT id, account_Number, transaction_Ts, transaction_Type, amount "
			+ "FROM transaction "
			+ "WHERE account_Number=:accountNumber AND transaction_Type = :transactionType AND transaction_Ts BETWEEN :beginDate AND :endDate";  
	
	public List<Transaction> getTransactionByDates(String accountNumber, Timestamp beginDate, Timestamp endDate) {
		if (logger.isDebugEnabled()) {
			logger.debug("entering");
		}
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("accountNumber", accountNumber)
		    .addValue("beginDate", beginDate).addValue("endDate", endDate);
		
		List<Transaction> transactions = transactionJdbcTemplate.query(
				findByDatesSql, namedParameters, new TransactionRowMapper());
		
		if (logger.isDebugEnabled()) {
			logger.debug("exting");
		}
		return transactions;
		
	}
	
	public List<Transaction> getTransactionByTansactionTypeAndDates(String accountNumber, String transactionType, Timestamp beginDate, Timestamp endDate) {
		if (logger.isDebugEnabled()) {
			logger.debug("entering");
		}
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("accountNumber", accountNumber)
		    .addValue("transactionType", transactionType).addValue("beginDate", beginDate).addValue("endDate", endDate);
		
		List<Transaction> transactions = transactionJdbcTemplate.query(
			findByTransactionTypeAndDatesSql, namedParameters, new TransactionRowMapper());
		
		if (logger.isDebugEnabled()) {
			logger.debug("exting");
		}
		return transactions;
	}

	public class TransactionRowMapper implements RowMapper<Transaction> {
	    @Override
	    public  Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	Transaction transaction = new Transaction();
	    	transaction.setId(rs.getLong("id"));
	    	transaction.setAccountNumber(rs.getString("account_Number"));
	    	transaction.setTransactionTs(rs.getTimestamp("transaction_Ts"));
	    	transaction.setType(Transaction.TransactionType.valueOf(rs.getString("transaction_Type")));
	    	transaction.setAmount(rs.getDouble("amount"));
	    	return transaction;
	    }
	}
}
