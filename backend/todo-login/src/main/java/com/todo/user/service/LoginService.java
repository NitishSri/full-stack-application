package com.todo.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.todo.user.dto.LoginRequest;
import com.todo.user.dto.UserProfileResponse;

public interface LoginService extends UserDetailsService{

	public UserProfileResponse login(LoginRequest request);

}
