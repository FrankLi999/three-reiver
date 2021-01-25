package com.threeriver.resource.transaction.integration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;

import com.threeriver.resource.transaction.TransactionApiApplication;

import io.cucumber.spring.CucumberContextConfiguration;

@SpringBootTest(classes = TransactionApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @ActiveProfiles("INTEGRATION_TEST")
@ContextConfiguration
@CucumberContextConfiguration
public class CucumberRoot {
    @Autowired
    protected TestRestTemplate restTemplate;
}
