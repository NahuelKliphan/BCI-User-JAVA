package com.example.userapi.service;

import java.util.List;

import com.example.userapi.dto.UserRequestDto;
import com.example.userapi.dto.UserResponseDto;
import com.example.userapi.dto.UserSimpleDto;

public interface UserService {

	UserResponseDto register(UserRequestDto userRequest);

	UserResponseDto login(String token);

	List<UserSimpleDto> getAllUsers();

}
