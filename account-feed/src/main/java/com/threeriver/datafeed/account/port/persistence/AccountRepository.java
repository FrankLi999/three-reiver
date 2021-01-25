package com.threeriver.datafeed.account.port.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.threeriver.datafeed.account.domain.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
}
