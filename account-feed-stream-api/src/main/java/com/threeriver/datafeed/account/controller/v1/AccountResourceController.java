package com.threeriver.datafeed.account.controller.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.threeriver.datafeed.account.dto.Account;
import com.threeriver.datafeed.account.dto.LatestBalance;
import com.threeriver.datafeed.account.service.StateStoreQueryService;
import com.threeriver.resource.account.error.AccountNotFoundException;
import com.threeriver.resource.account.error.InvalidInputException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "account", description = "the Account API")
@RestController
@RequestMapping("/account/v1")
public class AccountResourceController {
	private static final Logger logger = LoggerFactory.getLogger(AccountResourceController.class);
	
	@Autowired
	private StateStoreQueryService stateStoreQueryService;
	
    @Operation(summary = "Rereieves the latest balance of the given account.", 
    		   description = "Given an accountNumber, returns the latest balance.", 
    		   tags = { "account" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = LatestBalance.class))),
            @ApiResponse(responseCode = "400", description = "Invalid account number"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Unexpected?") })
	@GetMapping(path="/{accountNumber}", produces = "application/json")
	public LatestBalance getAccount(
            @Parameter(description="Account number. Cannot be empty.", required=true)
			@PathVariable String accountNumber) {
		if (logger.isDebugEnabled()) {
			logger.debug("entering");
		}
		
		if (ObjectUtils.isEmpty(accountNumber)) {
			throw new InvalidInputException("Account number is blank");
		}
		Account account = stateStoreQueryService.getLatestAccountByAccountNumber(accountNumber)
				.orElseThrow(() -> new AccountNotFoundException(accountNumber));
		LatestBalance latestBalance = new LatestBalance.Builder().setAccountNumber(accountNumber).setBalance(account.getBalance()).build();
		if (logger.isDebugEnabled()) {
			logger.debug("exiting");
		}
		
		return latestBalance;
	}
}