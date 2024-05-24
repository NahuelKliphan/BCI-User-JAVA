package com.example.userapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userapi.dto.UserRequestDto;
import com.example.userapi.dto.UserResponseDto;
import com.example.userapi.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public List<?> getAllUsers() {
		return userService.getAllUsers();
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Validated @RequestBody UserRequestDto userRequest) {
		UserResponseDto response = userService.register(userRequest);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/login")
	public ResponseEntity<?> login(@RequestHeader("Authorization") String bearerToken) {
		String token = bearerToken.substring(7);
		UserResponseDto userInfo = userService.login(token);
		return ResponseEntity.ok(userInfo);
	}

}
