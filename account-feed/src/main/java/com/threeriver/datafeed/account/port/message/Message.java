package com.threeriver.datafeed.account.port.message;


import java.util.Date;

public class Message<T> {

	private String messageType;
	private long id; // unique id of this message
	private String traceId;
	private String sender = "account";
	private Date timestamp = new Date();

	private T payload;

	public Message() {
	}

	public Message(String type, T payload) {
		this.messageType = type;
		this.payload = payload;
	}

	public Message(String type, String traceId, T payload) {
		this.messageType = type;
		this.payload = payload;
		this.traceId = traceId;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	@Override
	public String toString() {
		return "Message [messageType=" + messageType + ", id=" + id + ", timestamp=" + timestamp + ", payload="
				+ payload + "]";
	}

}
