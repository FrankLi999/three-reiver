package com.threeriver.datafeed.account.service;

import java.util.Optional;
import java.util.TreeSet;

import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Service;

import com.threeriver.datafeed.account.dto.Account;

@Service
public class StateStoreQueryService {

	@Value(value = "${message.ktable:account-k-table}")
	private String ktableName;

	private final StreamsBuilderFactoryBean streamsBuilderFactoryBean;

	public StateStoreQueryService(StreamsBuilderFactoryBean streamsBuilderFactoryBean) {
		this.streamsBuilderFactoryBean = streamsBuilderFactoryBean;
	}

	public Optional<Account> getLatestAccountByAccountNumber(String accountNumber) {
		TreeSet<Account> accounts = getReadOnlyStore().get(accountNumber);
		return (accounts == null) ? Optional.empty() : Optional.of(accounts.first());
	}

	private ReadOnlyKeyValueStore<String, TreeSet<Account>> getReadOnlyStore() {
		return streamsBuilderFactoryBean.getKafkaStreams().store(ktableName, QueryableStoreTypes.keyValueStore());
	}
}
