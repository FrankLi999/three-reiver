package com.threeriver.resource.transaction.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

public class Query {
	@Schema(description = "The account number.", example = "abc", required = true)
	@NotNull(message = "accountNumber must not be empty")
	private String accountNumber;
	
	@Schema(description = "Unique identifier of the transaction.", example = "DEPOSIT", required = false)
	private String transactionType;

	@Schema(description = "The start date to search the transaction", required = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp beginDate;

	@Schema(description = "The end date to search the transaction", required = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp endDate;

	@Schema(description = "...", required = false)
	private long offset;
	
	@Schema(description = "The number of the result wanted", required = false)
	private long limit;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Timestamp getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Timestamp beginDate) {
		this.beginDate = beginDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public long getLimit() {
		return limit;
	}

	public void setLimit(long limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "Query [accountNumber=" + accountNumber + ", transactionType=" + transactionType + ", beginDate="
				+ beginDate + ", endDate=" + endDate + ", offset=" + offset + ", limit=" + limit + "]";
	}
}
