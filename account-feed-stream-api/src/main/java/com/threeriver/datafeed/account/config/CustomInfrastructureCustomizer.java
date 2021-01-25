package com.threeriver.datafeed.account.config;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.KafkaStreamsInfrastructureCustomizer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import com.threeriver.datafeed.account.dto.Account;
import com.threeriver.datafeed.account.process.AccountProcessor;

public class CustomInfrastructureCustomizer implements KafkaStreamsInfrastructureCustomizer {

  private static final Serde<String> KEY_SERDE = Serdes.String();

  private static final Serde<Account> VALUE_SERDE = new JsonSerde<>(Account.class).ignoreTypeHeaders();

  private static final Deserializer<String> KEY_JSON_DE = new JsonDeserializer<>(String.class);

  private static final Deserializer<Account> VALUE_JSON_DE =
      new JsonDeserializer<>(Account.class).ignoreTypeHeaders();

  private final String accountTopic;
  private final String ktableName;
  CustomInfrastructureCustomizer(String accountTopic, String ktableName) {
    this.accountTopic = accountTopic;
    this.ktableName = ktableName;
  }

  @Override
  public void configureBuilder(StreamsBuilder builder) {
    StoreBuilder<KeyValueStore<String, Account>> stateStoreBuilder =
        Stores.keyValueStoreBuilder(Stores.persistentKeyValueStore(ktableName), KEY_SERDE, VALUE_SERDE);

    Topology topology = builder.build();
    topology.addSource("Source", KEY_JSON_DE, VALUE_JSON_DE, accountTopic)
            .addProcessor("Process", () -> new AccountProcessor(ktableName), "Source")
            .addStateStore(stateStoreBuilder, "Process");
//            .addSink("Sink", outputTopic, "Process");
  }
}
