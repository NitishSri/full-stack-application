package com.todo.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todo.user.dto.LoginRequest;
import com.todo.user.dto.UserProfileResponse;
import com.todo.user.service.LoginService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

	@Autowired
	private LoginService service;

	@PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
	public ResponseEntity<UserProfileResponse> login(@RequestBody LoginRequest request) {
		return ResponseEntity.ok().body(service.login(request));
	}

	@GetMapping(path = "/loaduser", produces = "application/json")
	public ResponseEntity<UserDetails> loadUser(@RequestParam String username) {
		return ResponseEntity.ok().body(service.loadUserByUsername(username));
	}
}
