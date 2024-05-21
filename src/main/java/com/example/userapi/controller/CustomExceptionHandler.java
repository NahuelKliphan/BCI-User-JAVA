package com.example.userapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getDefaultMessage()).collect(Collectors.toList());

		return ResponseEntity.badRequest().body(new ErrorMessage(errorMessages));
	}

	static class ErrorMessage {
		private List<String> mensajes;

		public ErrorMessage(List<String> mensajes) {
			this.mensajes = mensajes;
		}

		public List<String> getMensajes() {
			return mensajes;
		}

		public void setMensajes(List<String> mensajes) {
			this.mensajes = mensajes;
		}
	}
}