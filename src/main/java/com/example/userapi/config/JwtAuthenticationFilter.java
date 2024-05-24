package com.example.userapi.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.userapi.model.User;
import com.example.userapi.repository.UserRepository;
import com.example.userapi.service.JwtService;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserRepository userRepository;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		try {
			final String jwt = authHeader.substring(7);
			final String userEmail = jwtService.extractUsername(jwt);
			if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				Optional<User> userOptional = userRepository.findByEmail(userEmail);
				if (!userOptional.isPresent()) {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
							"Usuario no encontrado en la base de datos");
					return;
				}
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
				if (jwtService.isTokenValid(jwt, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				} else {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token JWT inválido");
					return;
				}
			} else {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
						"Token JWT no contiene información de usuario válida");
				return;
			}
			filterChain.doFilter(request, response);
		} catch (Exception exception) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"Error durante la autenticación: " + exception.getMessage());
		}
	}

}