package com.todo.user.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.user.basicauth.JWTUserDetails;
import com.todo.user.dto.LoginRequest;
import com.todo.user.dto.UserProfileResponse;
import com.todo.user.entity.Login;
import com.todo.user.entity.UserProfile;
import com.todo.user.repository.LoginRepository;
import com.todo.user.repository.RegisterRepository;
import com.todo.user.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	private RegisterRepository registerRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Override
	public UserProfileResponse login(LoginRequest request) {
		Optional<Login> loggedInUser = loginRepository.findByUsername(request.getUsername());
		if (loggedInUser.isPresent() && encoder.matches(request.getPassword(), loggedInUser.get().getPassword())) {
			ModelMapper modelMapper = new ModelMapper();
			Optional<UserProfile> userProfile = registerRepository.findByUsername(request.getUsername());
			if (userProfile.isPresent()) {
				return modelMapper.map(userProfile.get(), UserProfileResponse.class);
			} else {
				return setInvalidCredentials();
			}
		} else {
			return setInvalidCredentials();
		}

	}

	public UserProfileResponse setInvalidCredentials() {
		UserProfileResponse userProfileResponse = new UserProfileResponse();
		userProfileResponse.setInvalidCredentials(true);
		return userProfileResponse;
	}

	@Override
	public JWTUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Login> foundUser = loginRepository.findByUsername(username);
		if (foundUser.isPresent()) {
			JWTUserDetails user = new JWTUserDetails(foundUser.get().getUsername(), foundUser.get().getPassword());
			return user;
		}
		return null;
	}

}
