package com.todoapp.application.LoginProxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.todoapp.application.basicauth.JWTUserDetails;

@FeignClient(name = "todo-login-app")
public interface LoginProxyService {

	@GetMapping("/loaduser")
	public JWTUserDetails loadUserByUsername(@RequestParam("username") String username);

}
