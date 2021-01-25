package com.threeriver.resource.transaction.integration;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.threeriver.resource.transaction.dto.Query;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TransactionStep extends CucumberRoot {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private SimpleJdbcInsert simpleJdbcInsert;
	@SuppressWarnings("rawtypes")
	private ResponseEntity<List> response; // output
	
	@BeforeEach
	public void setup() {
		simpleJdbcInsert = 
			new SimpleJdbcInsert(jdbcTemplate).withTableName("TRANSACTION").usingGeneratedKeyColumns("ID");
	};

	@Given("^the client has the following transaction data$")
    public void haveTransactionData(DataTable accountData) {
		 
        List<List<String>> rows = accountData.asLists(String.class);

        for (List<String> columns: rows) {
        	Map<String, Object> parameters = new HashMap<String, Object>();
    	    parameters.put("accountNumber", columns.get(0));
    	    parameters.put("transactionTs", columns.get(1));
    	    parameters.put("transactionType", columns.get(2));
    	    parameters.put("amount", columns.get(3));
    	    
    	    simpleJdbcInsert.executeAndReturnKey(parameters);
        }
    }
	
	@When("^the user call transaction api with the {string}, {string} and {string}$")
    public void getTransactionsBetweenDates(String accountNumber, String begineDate, String endDate) {
		Query query = new Query();
		query.setAccountNumber(accountNumber);
		query.setBeginDate(Timestamp.valueOf(begineDate));
		query.setEndDate(Timestamp.valueOf(endDate));
		response = restTemplate.postForEntity("/transaction/v1", query, List.class);
    }

	@Then("^the client receives status code of {int}$")
    public void statusCodeOk(int statusCode) throws Throwable {
        HttpStatus currentStatusCode = response.getStatusCode();
        MatcherAssert.assertThat("status code is incorrect : " +
                response.getBody(), currentStatusCode.value(), CoreMatchers.is(statusCode));
    }
	
	@Then("^the client receives {int} transactions$")
    public void latestBalance(int numberOfTransactions) throws Throwable {
		Assertions.assertEquals(response.getBody().size(), numberOfTransactions);
    }
	
	@When("^the user call transaction api with {string} that does not exist$")
    public void getTransactionWithAccountNumberThatDoesNotExisit(String accountNumber) {
		Query query = new Query();
		query.setAccountNumber(accountNumber);
		query.setBeginDate(Timestamp.from(Instant.MIN));
		query.setEndDate(Timestamp.from(Instant.now()));
		response = restTemplate.postForEntity("/transaction/v1", query, List.class);
	}
	
	@When("^the user call transaction api with the {string}, {string}, {string} and {string}$")
    public void getTransactionsBetweenDatesByTransactiontype(String accountNumber, String trsnactionType, String begineDate, String endDate) {
		Query query = new Query();
		query.setAccountNumber(accountNumber);
		query.setBeginDate(Timestamp.valueOf(begineDate));
		query.setEndDate(Timestamp.valueOf(endDate));
		query.setTransactionType(trsnactionType);
		response = restTemplate.postForEntity("/transaction/v1", query, List.class);
    }
}
