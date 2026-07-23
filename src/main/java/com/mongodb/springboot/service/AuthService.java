package com.mongodb.springboot.service;

import com.mongodb.springboot.model.User;

/**
 * Authentication service interface.
 * Defines authentication and user management operations.
 * 
 * @author metanoia
 * @since 1.0
 */
public interface AuthService {
	
	/**
	 * Register a new user.
	 * @param user the user to register
	 * @return registered User
	 * @throws Exception if registration fails
	 */
	User register(User user) throws Exception;
	
	/**
	 * Authenticate user and generate JWT token.
	 * @param username the username
	 * @param password the password
	 * @return JWT token if authentication successful
	 * @throws Exception if authentication fails
	 */
	String login(String username, String password) throws Exception;
	
	/**
	 * Find user by username.
	 * @param username the username
	 * @return User if found, null otherwise
	 */
	User findByUsername(String username);
	
	/**
	 * Check if username already exists.
	 * @param username the username to check
	 * @return true if exists, false otherwise
	 */
	boolean userExists(String username);
	
	/**
	 * Check if email already exists.
	 * @param email the email to check
	 * @return true if exists, false otherwise
	 */
	boolean emailExists(String email);
}
