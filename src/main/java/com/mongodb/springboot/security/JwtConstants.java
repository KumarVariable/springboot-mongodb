package com.mongodb.springboot.security;

/**
 * JWT token constants and configuration values.
 * 
 * @author metanoia
 * @since 1.0
 */
public class JwtConstants {
	
	public static final String JWT_HEADER = "Authorization";
	public static final String JWT_PREFIX = "Bearer ";
	public static final long JWT_EXPIRATION = 604800000; // 7 days in milliseconds
	
	private JwtConstants() {
		// Private constructor to prevent instantiation
	}
}
