package com.threeriver.datafeed.account.process;

import java.util.Comparator;
import java.util.Objects;
import java.util.TreeSet;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

import com.threeriver.datafeed.account.dto.Account;

public class AccountProcessor implements Processor<String, Account> {
	private static long count = 1;
	private KeyValueStore<String, TreeSet<Account>> stateStore;

	private final String stateStoreName;

	public AccountProcessor(String stateStoreName) {
		this.stateStoreName = stateStoreName;
	}

	@Override
	public void init(ProcessorContext processorContext) {
		stateStore = (KeyValueStore<String, TreeSet<Account>>) processorContext.getStateStore(stateStoreName);
		Objects.requireNonNull(stateStore, "State store can't be null");
	}

	@Override
	public void process(String key, Account account) {
		
		TreeSet<Account> accounts = stateStore.get(account.getAccountNumber());
		account.setId(count++);
		System.out.println("Key: " + key + " Value: " + account.toString());
		if (accounts == null) {
			accounts = new TreeSet<Account>(new Comparator<Account>() {
				public int compare(Account e1, Account e2) {
			        if (e1.getLastUpdateTimstamp().after(e2.getLastUpdateTimstamp())){
			            return 1;
			        } else {
			            return -1;
			        }
			    }	
			});
			stateStore.put(account.getAccountNumber(), accounts);
		}
		accounts.add(account);
	}

	@Override
	public void close() {
	}
}
