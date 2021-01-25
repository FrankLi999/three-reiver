package com.threeriver.resource.transaction.controller.v1;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threeriver.resource.transaction.dto.Query;
import com.threeriver.resource.transaction.dto.Transaction;
import com.threeriver.resource.transaction.service.TransactionService;

@WebMvcTest(controllers = TransactionResourceController.class)
//@ActiveProfiles("test")
class TransactionResourceControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private TransactionService transactionService;
    
	@Test
	void testGetTransactionByDates() throws Exception {
		Transaction tx1 = new Transaction();
		Transaction tx2 = new Transaction();
		List<Transaction> expectedTransactions = Arrays.asList(tx1, tx2);
		
		given(transactionService.getTransactionByDates(ArgumentMatchers.any(String.class), 
				ArgumentMatchers.any(Timestamp.class), ArgumentMatchers.any(Timestamp.class))).willReturn(expectedTransactions);
		
		Query query = new Query();
		query.setAccountNumber("abc");
		query.setBeginDate(Timestamp.from(Instant.MIN));
		query.setEndDate(Timestamp.from(Instant.MAX));
		System.out.println(">>>>>>>>>>query" + objectMapper.writeValueAsString(query));
		this.mockMvc.perform(post("/transaction/v1/query")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(query)))
         	.andExpect(status().isOk())
         	.andExpect(jsonPath("$.size()", is(expectedTransactions.size())));
	}

	@Test
	void testGetTransactionByTansactionTypeAndDates() throws Exception {
		Transaction tx1 = new Transaction();
		Transaction tx2 = new Transaction();
		List<Transaction> expectedTransactions = Arrays.asList(tx1, tx2);
		
		given(transactionService.getTransactionByTansactionTypeAndDates(
				ArgumentMatchers.any(String.class), 
				ArgumentMatchers.any(String.class), 
				ArgumentMatchers.any(Timestamp.class), 
				ArgumentMatchers.any(Timestamp.class)))
				.willReturn(expectedTransactions);

		Query query = new Query();
		query.setAccountNumber("abc");
		query.setBeginDate(Timestamp.from(Instant.MIN));
		query.setEndDate(Timestamp.from(Instant.MAX));
		query.setTransactionType(Transaction.TransactionType.DEPOSIT.name());
		this.mockMvc.perform(post("/transaction/v1/query")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(query)))
         	.andExpect(status().isOk())
         	.andExpect(jsonPath("$.size()", is(expectedTransactions.size())));
	}

	@Test
	void othertests() {
		// TODO
	}
}
