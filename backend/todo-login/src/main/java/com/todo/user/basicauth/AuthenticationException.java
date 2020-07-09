package com.todo.user.basicauth;

public class AuthenticationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1837546394051805948L;

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
}