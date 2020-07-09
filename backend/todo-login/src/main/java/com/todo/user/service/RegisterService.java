package com.todo.user.service;

import com.todo.user.dto.RegistrationRequest;
import com.todo.user.dto.UserProfileResponse;

public interface RegisterService {

	public UserProfileResponse register(RegistrationRequest request);

}
