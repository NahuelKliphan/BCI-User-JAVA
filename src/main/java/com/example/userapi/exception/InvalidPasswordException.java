package com.example.userapi.exception;

public class InvalidPasswordException extends RuntimeException {

	private static final long serialVersionUID = 5489425096991700635L;

	public InvalidPasswordException(String message) {
		super(message);
	}
}
