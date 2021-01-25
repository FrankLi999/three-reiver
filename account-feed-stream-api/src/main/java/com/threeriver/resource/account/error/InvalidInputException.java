package com.threeriver.resource.account.error;

public class InvalidInputException extends RuntimeException {
	private static final long serialVersionUID = 2622830919674005368L;

	public InvalidInputException(String message) {
		super(message);
	}
}
