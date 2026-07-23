package com.mongodb.springboot.dto;

/**
 * DTO for authentication response.
 * Contains JWT token and user information.
 * 
 * @author metanoia
 * @since 1.0
 */
public class AuthResponse {
	
	private String token;
	private String username;
	private String role;
	private String message;
	private boolean success;

	public AuthResponse() {
	}

	public AuthResponse(String token, String username, String role) {
		this.token = token;
		this.username = username;
		this.role = role;
		this.success = true;
		this.message = "Authentication successful";
	}

	public AuthResponse(String message, boolean success) {
		this.message = message;
		this.success = success;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
