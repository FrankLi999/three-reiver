package com.threeriver.resource.account.integration;

import org.hamcrest.CoreMatchers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AccountStep extends CucumberRoot {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private SimpleJdbcInsert simpleJdbcInsert;
	private ResponseEntity<Double> response; // output
	
	@BeforeEach
	public void setup() {
		simpleJdbcInsert = 
			new SimpleJdbcInsert(jdbcTemplate).withTableName("ACCOUNT").usingGeneratedKeyColumns("ID");
	};

	@Given("the client has the following account data")
    public void haveAccountData(DataTable accountData) {
		 
        List<List<String>> rows = accountData.asLists(String.class);

        for (List<String> columns: rows) {
        	Map<String, Object> parameters = new HashMap<String, Object>();
    	    parameters.put("accountNumber", columns.get(0));
    	    parameters.put("lastUpdateTimstamp", columns.get(1));
    	    parameters.put("balance", columns.get(2));
    	    
    	    simpleJdbcInsert.executeAndReturnKey(parameters);
        }
    }
	
	@When("the user calls account api with the {string}")
    public void getLatestBalance(String accountNumber) {
		response = restTemplate.getForEntity(String.format("/account/v1/%s", accountNumber), Double.class);
    }

	@Then("the client receives status code of {int}")
    public void statusCodeOk(int statusCode) throws Throwable {
        HttpStatus currentStatusCode = response.getStatusCode();
        MatcherAssert.assertThat("status code is incorrect : " +
                response.getBody(), currentStatusCode.value(), CoreMatchers.is(statusCode));
    }
	
	@Then("the client receives the latest {double}")
    public void latestBalance(double balance) throws Throwable {
        Assertions.assertEquals(response.getBody().doubleValue(), balance);
    }
	
	@When("the user calls account api with {string} that does not exist")
    public void getLatestBalanceWithAccountNumberThatDoesNotExisit(String accountNumber) {
		response = restTemplate.getForEntity(String.format("/account/v1/%s", accountNumber), Double.class);
	}
}
