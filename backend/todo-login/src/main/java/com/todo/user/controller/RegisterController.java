package com.todo.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.todo.user.dto.RegistrationRequest;
import com.todo.user.dto.UserProfileResponse;
import com.todo.user.service.RegisterService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class RegisterController {

	@Autowired
	private RegisterService service;

	@PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
	public ResponseEntity<UserProfileResponse> register(@RequestBody RegistrationRequest request) {
		return ResponseEntity.ok().body(service.register(request));
	}

}
