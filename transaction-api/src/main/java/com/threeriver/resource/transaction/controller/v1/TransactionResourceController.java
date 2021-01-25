package com.threeriver.resource.transaction.controller.v1;


import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.threeriver.resource.transaction.dto.Query;
import com.threeriver.resource.transaction.dto.Transaction;
import com.threeriver.resource.transaction.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "transaction", description = "the Transaction API")
@RestController
@RequestMapping("/transaction/v1")
public class TransactionResourceController {
	private static final Logger logger = LoggerFactory.getLogger(TransactionResourceController.class);
	@Autowired
	private TransactionService transactionService;
	
    @Operation(summary = "Rereieves the transaction based on the given query.", 
 		   description = "...", 
 		   tags = { "transaction" })
    @ApiResponses(value = {
         @ApiResponse(responseCode = "200", description = "successful operation",
             content = @Content(array = @ArraySchema(schema = @Schema(implementation = Transaction.class)))),
         @ApiResponse(responseCode = "400", description = "Invalid account number"),
         @ApiResponse(responseCode = "404", description = "Account not found"),
         @ApiResponse(responseCode = "500", description = "Unexpected?") })
	@PostMapping(path="/query", consumes = "application/json", produces = "application/json")
	public List<Transaction> getTransactionByDates(
            @Parameter(description="Transaction query", 
            	required=true, schema=@Schema(implementation = Query.class))
			@RequestBody @Valid Query query) {
		if (logger.isDebugEnabled()) {
			logger.debug("entering");
		}
		
		List<Transaction> transactions = (StringUtils.hasText(query.getTransactionType())) ?
				this.transactionService.getTransactionByTansactionTypeAndDates(
						query.getAccountNumber(), query.getTransactionType(), query.getBeginDate(), query.getEndDate()) :
				this.transactionService.getTransactionByDates(query.getAccountNumber(), 
						query.getBeginDate(), query.getEndDate());
		if (logger.isDebugEnabled()) {
			logger.debug("exiting");
		}
		return transactions;
	}
}
