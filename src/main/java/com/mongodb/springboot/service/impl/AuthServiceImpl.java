package com.mongodb.springboot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mongodb.springboot.model.Role;
import com.mongodb.springboot.model.User;
import com.mongodb.springboot.repository.UserRepository;
import com.mongodb.springboot.security.JwtTokenProvider;
import com.mongodb.springboot.service.AuthService;

/**
 * Authentication service implementation.
 * Handles user registration, login, and JWT token generation.
 * 
 * @author metanoia
 * @since 1.0
 */
@Service
public class AuthServiceImpl implements AuthService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtTokenProvider tokenProvider;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public User register(User user) throws Exception {
		// Validate input
		if (user == null || user.getUsername() == null || user.getPassword() == null) {
			throw new Exception("Invalid user data");
		}

		// Check if username already exists
		if (userRepository.existsByUsername(user.getUsername())) {
			throw new Exception("Username already exists");
		}

		// Check if email already exists
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new Exception("Email already exists");
		}

		// Encode password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		// Set default role if not provided
		if (user.getRole() == null) {
			user.setRole(Role.USER.getValue());
		}
		
		// Set timestamps
		user.setCreatedAt(System.currentTimeMillis());
		user.setUpdatedAt(System.currentTimeMillis());
		user.setEnabled(true);

		User savedUser = userRepository.save(user);
		LOGGER.info("User registered successfully: {}", user.getUsername());

		return savedUser;
	}

	@Override
	public String login(String username, String password) throws Exception {
		// Validate input
		if (username == null || password == null) {
			throw new Exception("Username and password required");
		}

		// Find user by username
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new Exception("User not found");
		}

		// Check if user is enabled
		if (!user.getEnabled()) {
			throw new Exception("User account is disabled");
		}

		// Validate password
		if (!passwordEncoder.matches(password, user.getPassword())) {
			LOGGER.warn("Invalid password attempt for user: {}", username);
			throw new Exception("Invalid credentials");
		}

		// Generate JWT token
		String token = tokenProvider.generateToken(username, user.getRole());
		LOGGER.info("User logged in successfully: {}", username);

		return token;
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public boolean userExists(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public boolean emailExists(String email) {
		return userRepository.existsByEmail(email);
	}
}
