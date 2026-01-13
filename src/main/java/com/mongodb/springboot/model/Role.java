package com.mongodb.springboot.model;

/**
 * User roles in the system.
 * 
 * @author metanoia
 * @since 1.0
 */
public enum Role {
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER"),
	TRAINER("ROLE_TRAINER");

	private final String value;

	Role(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
