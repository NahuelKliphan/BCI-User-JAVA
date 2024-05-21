package com.example.userapi.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userapi.dto.UserRequestDTO;
import com.example.userapi.dto.UserResponseDTO;
import com.example.userapi.model.User;
import com.example.userapi.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@PostMapping
	public ResponseEntity<?> registerUser(@Validated @RequestBody UserRequestDTO userRequest) {
		try {
			UserResponseDTO response = userService.registerUser(userRequest);
			return ResponseEntity.status(201).body(response);
		} catch (RuntimeException e) {
			return ResponseEntity.status(400).body(Collections.singletonMap("mensaje", e.getMessage()));
		}
	}

}
