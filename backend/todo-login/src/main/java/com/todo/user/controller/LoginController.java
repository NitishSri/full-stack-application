package com.todo.user.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todo.user.basicauth.AuthenticationException;
import com.todo.user.basicauth.JwtTokenUtil;
import com.todo.user.dto.LoginRequest;
import com.todo.user.dto.UserProfileResponse;
import com.todo.user.service.LoginService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

	@Autowired
	private LoginService service;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
	public ResponseEntity<UserProfileResponse> login(@RequestBody LoginRequest request) {
		UserProfileResponse userProfileResponse = service.login(request);
		if (userProfileResponse.isInvalidCredentials()) {
			return ResponseEntity.ok().body(userProfileResponse);
		}
		authenticate(request.getUsername(), request.getPassword());
		final UserDetails userDetails = service.loadUserByUsername(userProfileResponse.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		userProfileResponse.setToken(token);

		return ResponseEntity.ok().body(userProfileResponse);
	}

	@GetMapping(path = "/loaduser", produces = "application/json")
	public ResponseEntity<UserDetails> loadUser(@RequestParam String username) {
		return ResponseEntity.ok().body(service.loadUserByUsername(username));
	}

	@GetMapping(path = "/user/{username}", produces = "application/json")
	public ResponseEntity<UserProfileResponse> loadUserInfo(@PathVariable String username) {
		return ResponseEntity.ok().body(service.loadUserInfo(username));
	}

	private void authenticate(String username, String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new AuthenticationException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new AuthenticationException("INVALID_CREDENTIALS", e);
		}
	}
}
