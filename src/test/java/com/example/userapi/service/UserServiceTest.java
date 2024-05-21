package com.example.userapi.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.userapi.dto.UserRequestDTO;
import com.example.userapi.model.User;
import com.example.userapi.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	@Test
	public void whenEmailAlreadyExists_thenThrowException() {
		UserRequestDTO userRequest = new UserRequestDTO();
		userRequest.setEmail("test@test.com");

		User existingUser = new User();
		existingUser.setEmail("test@test.com");

		Mockito.when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(existingUser));

		assertThrows(RuntimeException.class, () -> userService.registerUser(userRequest));
	}

}
