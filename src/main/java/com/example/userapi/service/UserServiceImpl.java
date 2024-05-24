package com.example.userapi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.userapi.dto.UserRequestDto;
import com.example.userapi.dto.UserResponseDto;
import com.example.userapi.dto.UserSimpleDto;
import com.example.userapi.model.User;
import com.example.userapi.repository.UserRepository;
import com.example.userapi.validator.UserValidator;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private JwtService jwtService;

	@Transactional
	@Override
	public UserResponseDto register(UserRequestDto userRequest) {
		userValidator.validate(userRequest);
		User user = modelMapper.map(userRequest, User.class);
		String jwtToken = jwtService.generateToken(user);
		user.setToken(jwtToken);
		User savedUser = userRepository.save(user);
		return modelMapper.map(savedUser, UserResponseDto.class);
	}

	@Transactional
	@Override
	public UserResponseDto login(String token) {
		String email = jwtService.extractUsername(token);
		User user = userRepository.findByEmail(email).orElseThrow();
		user.setLastLogin(LocalDateTime.now());
		User userUpdated = userRepository.save(user);
		return modelMapper.map(userUpdated, UserResponseDto.class);
	}

	@Transactional
	@Override
	public List<UserSimpleDto> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map(user -> modelMapper.map(user, UserSimpleDto.class)).collect(Collectors.toList());
	}

}
