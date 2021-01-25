package com.threeriver.datafeed.transaction.port.persistence;

import org.springframework.data.repository.CrudRepository;

import com.threeriver.datafeed.transaction.domain.Transaction;


public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
