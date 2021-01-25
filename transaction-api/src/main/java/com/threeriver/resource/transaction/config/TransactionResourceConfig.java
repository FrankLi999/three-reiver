package com.threeriver.resource.transaction.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class TransactionResourceConfig {

	@Bean(name = "transactionJdbcTemplate")
	@ConditionalOnMissingBean(name = "transactionDdbcTemplate")
	public NamedParameterJdbcTemplate transactionJdbcTemplate(DataSource datasource) {
	    return new NamedParameterJdbcTemplate(datasource);
	}
}
