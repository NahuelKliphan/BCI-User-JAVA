package com.example.userapi.dto;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class UserRequestDto {

	@NotEmpty(message = "El campo 'name' es obligatorio")
	private String name;

	@Email(regexp = ".+@.+\\..+", message = "El correo electrónico debe tener un formato válido")
	@NotEmpty(message = "El campo 'email' es obligatorio")
	private String email;

	@NotEmpty(message = "El campo 'password' es obligatorio")
	private String password;

	@NotEmpty(message = "El campo 'phones' es obligatorio")
	private List<PhoneDto> phones;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<PhoneDto> getPhones() {
		return phones;
	}

	public void setPhones(List<PhoneDto> phones) {
		this.phones = phones;
	}

}
