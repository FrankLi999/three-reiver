package com.threeriver.resource.account.acceptance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;



public class ApplicationE2E{
    private int port = 8097;
    private RestTemplate restTemplate;
    private URL baseURL;

    @BeforeEach
    public void setUp() throws Exception {
        this.baseURL = new URL("http://localhost:" + port + "/");
        restTemplate = new RestTemplate();


    }

    @Test
    public void test_is_server_up() {
        assertTrue(restTemplate.getForEntity(baseURL + "/actuator/health", String.class).getStatusCode().is2xxSuccessful());

    }


}

