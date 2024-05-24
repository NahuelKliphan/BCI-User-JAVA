package com.example.userapi.exception;

public class EmailAlreadyRegisteredException extends RuntimeException {

	private static final long serialVersionUID = 71123800874165433L;

	public EmailAlreadyRegisteredException(String message) {
		super(message);
	}
}
