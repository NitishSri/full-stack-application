package com.todo.user.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.user.dto.RegistrationRequest;
import com.todo.user.dto.UserProfileResponse;
import com.todo.user.entity.Login;
import com.todo.user.entity.UserProfile;
import com.todo.user.repository.LoginRepository;
import com.todo.user.repository.RegisterRepository;
import com.todo.user.service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private RegisterRepository registerRepository;

	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Override
	public UserProfileResponse register(RegistrationRequest request) {
		Optional<UserProfile> userFound = registerRepository.findByUsername(request.getUsername());
		if (userFound.isPresent()) {
			UserProfileResponse userProfileResponse = new UserProfileResponse();
			userProfileResponse.setUserExists(true);
			return userProfileResponse;
		} else {
			UserProfile user = new UserProfile();
			user.setFirstname(request.getFirstname());
			user.setLastname(request.getLastname());
			user.setUsername(request.getUsername());

			Login credentials = new Login();
			credentials.setUsername(request.getUsername());
			credentials.setPassword(encoder.encode(request.getPassword()));
			loginRepository.save(credentials);
			registerRepository.save(user);

			ModelMapper modelMapper = new ModelMapper();
			return modelMapper.map(user, UserProfileResponse.class);
		}

	}

}
