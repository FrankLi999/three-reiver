package com.threeriver.resource.account.domain;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

public class Account {

	private long id;
	
	@NotNull
	@Schema(description = "Unique identifier of the account.", 
    example = "abc", required = true)
	private String accountNumber;
	
	@Schema(description = "Last update timestamp.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp lastUpdateTimstamp;
	
	@Schema(description = "The account balance.")
	private double balance;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Timestamp getLastUpdateTimstamp() {
		return lastUpdateTimstamp;
	}

	public void setLastUpdateTimstamp(Timestamp lastUpdateTimstamp) {
		this.lastUpdateTimstamp = lastUpdateTimstamp;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", accountNumber=" + accountNumber + ", lastUpdateTimstamp=" + lastUpdateTimstamp
				+ ", balance=" + balance + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((lastUpdateTimstamp == null) ? 0 : lastUpdateTimstamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		if (id != other.id)
			return false;
		if (lastUpdateTimstamp == null) {
			if (other.lastUpdateTimstamp != null)
				return false;
		} else if (!lastUpdateTimstamp.equals(other.lastUpdateTimstamp))
			return false;
		return true;
	}
}
