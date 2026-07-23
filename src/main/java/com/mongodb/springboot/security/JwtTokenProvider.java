package com.mongodb.springboot.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

/**
 * JWT token provider for generating and validating tokens.
 * Uses JJWT library for token operations.
 * 
 * @author metanoia
 * @since 1.0
 */
@Component
public class JwtTokenProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

	@Value("${app.jwt.secret:MyVerySecureSecretKeyForJWTTokenGenerationAndValidation2024}")
	private String jwtSecret;

	@Value("${app.jwt.expiration:604800000}")
	private long jwtExpiration;

	/**
	 * Generate JWT token for authenticated user.
	 * @param username the username to include in token
	 * @param role the user role
	 * @return JWT token string
	 */
	public String generateToken(String username, String role) {
		Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
		
		return Jwts.builder()
				.setSubject(username)
				.claim("role", role)
				.setIssuedAt(new java.util.Date())
			.setExpiration(new java.util.Date(System.currentTimeMillis() + jwtExpiration))
			.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	/**
	 * Get username from JWT token.
	 * @param token the JWT token
	 * @return username if token is valid, null otherwise
	 */
	public String getUsernameFromToken(String token) {
		try {
			Claims claims = getClaimsFromToken(token);
			return claims.getSubject();
		} catch (Exception e) {
			LOGGER.error("Failed to get username from token: {}", e.getMessage());
			return null;
		}
	}

	/**
	 * Get user role from JWT token.
	 * @param token the JWT token
	 * @return role if token is valid, null otherwise
	 */
	public String getRoleFromToken(String token) {
		try {
			Claims claims = getClaimsFromToken(token);
			return (String) claims.get("role");
		} catch (Exception e) {
			LOGGER.error("Failed to get role from token: {}", e.getMessage());
			return null;
		}
	}

	/**
	 * Validate JWT token.
	 * @param token the JWT token to validate
	 * @return true if token is valid, false otherwise
	 */
	public boolean validateToken(String token) {
		try {
			getClaimsFromToken(token);
			return true;
		} catch (Exception e) {
			LOGGER.error("Token validation failed: {}", e.getMessage());
			return false;
		}
	}

	/**
	 * Extract claims from JWT token.
	 * @param token the JWT token
	 * @return Claims object
	 */
	private Claims getClaimsFromToken(String token) {
		Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
		
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}
