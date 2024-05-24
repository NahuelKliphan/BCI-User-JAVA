package com.example.userapi.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.userapi.dto.UserRequestDto;
import com.example.userapi.exception.EmailAlreadyRegisteredException;
import com.example.userapi.exception.InvalidPasswordException;
import com.example.userapi.repository.UserRepository;

@Component
public class UserValidator {

	@Value("${regex.password}")
	private String passwordRegex;

	private final UserRepository userRepository;

	public UserValidator(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void validate(UserRequestDto userRequest) {
		validatePassword(userRequest.getPassword());
		validateEmail(userRequest.getEmail());
	}

	private void validatePassword(String password) {
		Pattern pattern = Pattern.compile(passwordRegex);
		Matcher matcher = pattern.matcher(password);

		if (!matcher.matches()) {
			throw new InvalidPasswordException("La contraseña no cumple la expresión regular");
		}
	}

	private void validateEmail(String email) {
		if (userRepository.findByEmail(email).isPresent()) {
			throw new EmailAlreadyRegisteredException("El correo ya está registrado");
		}
	}
}