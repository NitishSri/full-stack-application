package com.todoapp.application.LoginProxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todoapp.application.basicauth.JWTUserDetails;

@FeignClient(url = "http://localhost:9091", name = "login-service")
public interface LoginProxyService {

	@GetMapping("/loaduser")
	public JWTUserDetails loadUserByUsername(@RequestParam("username") String username);

}
