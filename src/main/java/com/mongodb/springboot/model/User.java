package com.mongodb.springboot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a user in the system.
 * Stores user authentication and profile information.
 * 
 * @author metanoia
 * @since 1.0
 */
@Document(collection = "users")
public class User {

	@Id
	private String id;
	
	private String username;
	private String email;
	private String password;
	private String fullName;
	private String role;
	private Boolean enabled;
	private Long createdAt;
	private Long updatedAt;

	public User() {
	}

	public User(String username, String email, String password, String fullName, String role) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.role = role;
		this.enabled = true;
		this.createdAt = System.currentTimeMillis();
		this.updatedAt = System.currentTimeMillis();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public Long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}
}
