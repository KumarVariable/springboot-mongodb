package com.mongodb.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.springboot.dto.AuthResponse;
import com.mongodb.springboot.dto.LoginRequest;
import com.mongodb.springboot.dto.RegisterRequest;
import com.mongodb.springboot.model.Role;
import com.mongodb.springboot.model.User;
import com.mongodb.springboot.service.AuthService;

/**
 * REST controller for authentication endpoints.
 * Handles user registration and login operations.
 * 
 * @author metanoia
 * @since 1.0
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private AuthService authService;

	/**
	 * Register a new user.
	 * @param request the registration request containing user details
	 * @return ResponseEntity with AuthResponse containing user info
	 */
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
		try {
			LOGGER.info("Registration attempt for username: {}", request.getUsername());

			// Validate request
			if (request.getUsername() == null || request.getPassword() == null) {
				return ResponseEntity.badRequest()
					.body(new AuthResponse("Username and password are required", false));
			}

			// Check if username already exists
			if (authService.userExists(request.getUsername())) {
				return ResponseEntity.badRequest()
					.body(new AuthResponse("Username already exists", false));
			}

			// Check if email already exists
			if (authService.emailExists(request.getEmail())) {
				return ResponseEntity.badRequest()
					.body(new AuthResponse("Email already exists", false));
			}

			// Create user object
			User user = new User();
			user.setUsername(request.getUsername());
			user.setEmail(request.getEmail());
			user.setPassword(request.getPassword());
			user.setFullName(request.getFullName());
			
			// Set role (default to USER if not provided)
			String role = request.getRole();
			if (role == null || role.isEmpty()) {
				user.setRole(Role.USER.getValue());
			} else {
				user.setRole(role);
			}

			// Register user
			User registeredUser = authService.register(user);

			AuthResponse response = new AuthResponse();
			response.setUsername(registeredUser.getUsername());
			response.setRole(registeredUser.getRole());
			response.setSuccess(true);
			response.setMessage("User registered successfully");

			return ResponseEntity.status(HttpStatus.CREATED).body(response);

		} catch (Exception e) {
			LOGGER.error("Registration failed: {}", e.getMessage());
			return ResponseEntity.badRequest()
				.body(new AuthResponse(e.getMessage(), false));
		}
	}

	/**
	 * Authenticate user and return JWT token.
	 * @param request the login request containing username and password
	 * @return ResponseEntity with AuthResponse containing JWT token
	 */
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
		try {
			LOGGER.info("Login attempt for username: {}", request.getUsername());

			// Validate request
			if (request.getUsername() == null || request.getPassword() == null) {
				return ResponseEntity.badRequest()
					.body(new AuthResponse("Username and password are required", false));
			}

			// Authenticate and get token
			String token = authService.login(request.getUsername(), request.getPassword());

			// Get user info
			User user = authService.findByUsername(request.getUsername());

			AuthResponse response = new AuthResponse(token, user.getUsername(), user.getRole());
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			LOGGER.error("Login failed: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new AuthResponse(e.getMessage(), false));
		}
	}
}
