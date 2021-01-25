package com.threeriver.resource.account.controller.v1;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.threeriver.resource.account.domain.Account;
import com.threeriver.resource.account.service.AccountService;

// @SpringBootTest(classes = AccountApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @SpringBootTest
@WebMvcTest(controllers = AccountResourceController.class)
// @ActiveProfiles("test")
class AccountResourceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;
    
	@Test
	void testGetLastesBalance() throws Exception {
		Account account = new Account();
		account.setAccountNumber("abc");
		account.setBalance(12.5);
		given(accountService.getAccountByAccountNumber(ArgumentMatchers.any(String.class))).willReturn(Optional.of(account));
		
		
		this.mockMvc.perform(get("/account/v1/abc"))
         	.andExpect(status().isOk());
//         	.andExpect(jsonPath("$.getBalance()", is(account.getBalance())))
//         	.andExpect(jsonPath("$.getAccountNumber()", is(account.getAccountNumber())));
	}


	//@Test
	void testNotFound() throws Exception {
		given(accountService.getAccountByAccountNumber(ArgumentMatchers.any(String.class))).willReturn(Optional.empty());
		this.mockMvc.perform(get("/account/v1/abc"))
	     	.andExpect(status().isNotFound());
	     	
	}

//	@Test
//	void testBadRequest() throws Exception {
//		this.mockMvc.perform(get("/account/v1/")).andExpect(status().isBadRequest());
//	}
}
