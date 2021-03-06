package com.todo.user.dto;

public class UserProfileResponse {
	private Long id;
	private String username;
	private String firstname;
	private String lastname;
	private String password;
	private boolean invalidCredentials;
	private boolean userExists;
	private String token;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public boolean isInvalidCredentials() {
		return invalidCredentials;
	}

	public void setInvalidCredentials(boolean invalidCredentials) {
		this.invalidCredentials = invalidCredentials;
	}

	public boolean isUserExists() {
		return userExists;
	}

	public void setUserExists(boolean userExists) {
		this.userExists = userExists;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
