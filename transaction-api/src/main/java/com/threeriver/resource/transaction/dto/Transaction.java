package com.threeriver.resource.transaction.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;

import io.swagger.v3.oas.annotations.media.Schema;

public class Transaction {
	public enum TransactionType {
		DEPOSIT, WITHDRAW
	}

    @Schema(description = "Unique identifier of the transaction.", 
            example = "1", required = true)
	@Id
	private Long id;
	
    @Schema(description = "The account number", 
            example = "abc", required = true)
	@NotEmpty(message = "accountNumber must not be empty")
	private String accountNumber;
	
    @Schema(description = "The timestamp of the transaction")
	private Timestamp transactionTs;
    
    @Schema(description = "The transaction type. DEPOSIT or WITHDRAW")
	private TransactionType type;
    
    @Schema(description = "The amount of the transaction")
	private double amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Timestamp getTransactionTs() {
		return transactionTs;
	}

	public void setTransactionTs(Timestamp transactionTs) {
		this.transactionTs = transactionTs;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", accountNumber=" + accountNumber + ", transactionTs=" + transactionTs
				+ ", type=" + type + ", amount=" + amount + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((transactionTs == null) ? 0 : transactionTs.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Transaction other = (Transaction) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (transactionTs == null) {
			if (other.transactionTs != null)
				return false;
		} else if (!transactionTs.equals(other.transactionTs))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
