package com.threeriver.resource.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class LatestBalance {
	@Schema(description = "The account number.", 
		    example = "abc", required = true)
	private String accountNumber;
	
	@Schema(description = "The account balance.")
	private double balance;

	private LatestBalance(String accountNumber, double balance) {
		this.accountNumber = accountNumber;
		this.balance = balance;
	}
	public String getAccountNumber() {
		return accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	@Override
	public String toString() {
		return "LatestBalance [accountNumber=" + accountNumber + ", balance=" + balance + "]";
	}
	
	public static class Builder {

		private String accountNumber;
		private double balance;

        public Builder setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this; 
        }

        public Builder setBalance(double balance) {
            this.balance = balance;
            return this;
        }

        public LatestBalance build() {
            return new LatestBalance(accountNumber, balance);
        }
    }
}
