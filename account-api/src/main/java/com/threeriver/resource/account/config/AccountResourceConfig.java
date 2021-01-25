package com.threeriver.resource.account.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class AccountResourceConfig {

	@Bean(name = "accountJdbcTemplate")
	@ConditionalOnMissingBean(name = "accountDdbcTemplate")
	public NamedParameterJdbcTemplate accountJdbcTemplate(DataSource datasource) {
	    return new NamedParameterJdbcTemplate(datasource);
	}
}
