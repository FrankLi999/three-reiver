package com.threeriver.resource.account.error;

public class AccountNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6983535120868236220L;
	private String accountNumber;

    public AccountNotFoundException(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}

