package com.example.userapi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.userapi.dto.PhoneDTO;
import com.example.userapi.dto.UserRequestDTO;
import com.example.userapi.dto.UserResponseDTO;
import com.example.userapi.model.Phone;
import com.example.userapi.model.User;
import com.example.userapi.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
    @Value("${regex.password}")
    private String passwordRegex;

	@Transactional
	public UserResponseDTO registerUser(UserRequestDTO userRequest) {

		validateUser(userRequest);

		User user = new User();
		user.setName(userRequest.getName());
		user.setEmail(userRequest.getEmail());
		user.setPassword(userRequest.getPassword());
		user.setCreated(LocalDateTime.now());
		user.setModified(LocalDateTime.now());
		user.setLastLogin(LocalDateTime.now());
		user.setToken(UUID.randomUUID().toString());
		user.setActive(true);
		user.setPhones(mapPhoneDTOsToPhones(userRequest.getPhones()));

		userRepository.save(user);

		return mapUserToUserResponseDTO(user);
	}
	
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

	private void validateUser(UserRequestDTO user) {
				
		Pattern pattern = Pattern.compile(passwordRegex);
		
		Matcher matcher = pattern.matcher(user.getPassword());
		
		if (!matcher.matches()) {
			throw new RuntimeException("La contraseña no cumple la expresin regular");
		}
		
		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
			throw new RuntimeException("El correo ya está registrado");
		}
		
	}

    
	private List<Phone> mapPhoneDTOsToPhones(List<PhoneDTO> phoneDTOs) {
		return phoneDTOs.stream().map(dto -> {
			Phone phone = new Phone();
			phone.setNumber(dto.getNumber());
			phone.setCitycode(dto.getCitycode());
			phone.setContrycode(dto.getContrycode());
			return phone;
		}).collect(Collectors.toList());
	}

	private UserResponseDTO mapUserToUserResponseDTO(User user) {
		UserResponseDTO response = new UserResponseDTO();
		response.setId(user.getId());
		response.setName(user.getName());
		response.setEmail(user.getEmail());
		response.setCreated(user.getCreated());
		response.setModified(user.getModified());
		response.setLastLogin(user.getLastLogin());
		response.setToken(user.getToken());
		response.setActive(user.isActive());
		response.setPhones(user.getPhones().stream().map(phone -> {
			PhoneDTO dto = new PhoneDTO();
			dto.setNumber(phone.getNumber());
			dto.setCitycode(phone.getCitycode());
			dto.setContrycode(phone.getContrycode());
			return dto;
		}).collect(Collectors.toList()));
		return response;
	}

}
