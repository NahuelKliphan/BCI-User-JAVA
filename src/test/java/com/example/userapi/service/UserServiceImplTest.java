package com.example.userapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.userapi.dto.UserRequestDto;
import com.example.userapi.dto.UserResponseDto;
import com.example.userapi.dto.UserSimpleDto;
import com.example.userapi.exception.EmailAlreadyRegisteredException;
import com.example.userapi.model.User;
import com.example.userapi.repository.UserRepository;
import com.example.userapi.validator.UserValidator;
import org.mockito.InjectMocks;

@SpringBootTest
public class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private UserValidator userValidator;

	@Mock
	private JwtService jwtService;

	@InjectMocks
	private UserServiceImpl userService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testRegister() {
		UserRequestDto userRequest = new UserRequestDto();
		userRequest.setEmail("test@example.com");
		userRequest.setPassword("password");
		User user = new User();
		User savedUser = new User();
		savedUser.setEmail("test@example.com");
		savedUser.setToken("token");

		UserResponseDto userResponse = new UserResponseDto();
		userResponse.setEmail("test@example.com");
		userResponse.setToken("token");

		when(modelMapper.map(userRequest, User.class)).thenReturn(user);
		when(jwtService.generateToken(user)).thenReturn("token");
		when(userRepository.save(user)).thenReturn(savedUser);
		when(modelMapper.map(savedUser, UserResponseDto.class)).thenReturn(userResponse);

		UserResponseDto response = userService.register(userRequest);

		assertNotNull(response);
		assertEquals("test@example.com", response.getEmail());
		assertEquals("token", response.getToken());
	}

	@Test
	public void testLogin() {
		String token = "validtoken";
		String email = "test@example.com";
		User user = new User();
		user.setEmail(email);

		UserResponseDto userResponseDto = new UserResponseDto();
		userResponseDto.setEmail(email);
		userResponseDto.setToken(token);

		when(jwtService.extractUsername(token)).thenReturn(email);
		when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
		when(userRepository.save(user)).thenReturn(user);
		when(modelMapper.map(user, UserResponseDto.class)).thenReturn(userResponseDto);

		UserResponseDto response = userService.login(token);

		assertNotNull(response);
		assertEquals(email, response.getEmail());
		assertEquals(token, response.getToken());
	}

	@Test
	public void testGetAllUsers() {
		String email = "test@example.com";
		User user = new User();
		user.setName("Test User");
		user.setEmail(email);

		UserSimpleDto userSimple = new UserSimpleDto();
		userSimple.setEmail(email);

		when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
		when(modelMapper.map(user, UserSimpleDto.class)).thenReturn(userSimple);

		List<UserSimpleDto> users = userService.getAllUsers();

		assertNotNull(users);
		assertEquals(1, users.size());
		assertEquals(email, users.get(0).getEmail());
	}

}
