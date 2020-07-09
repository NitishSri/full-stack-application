package com.todo.user.basicauth;

import java.io.Serializable;

public class JwtTokenResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5487395368547562222L;
	private final String token;

	public JwtTokenResponse(String token) {
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}
}
