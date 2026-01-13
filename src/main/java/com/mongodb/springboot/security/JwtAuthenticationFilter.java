package com.mongodb.springboot.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * JWT authentication filter that validates JWT tokens in request headers.
 * Executes once per request to extract and validate JWT tokens.
 * 
 * @author metanoia
 * @since 1.0
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
									FilterChain filterChain) throws ServletException, IOException {
		try {
			String token = extractTokenFromRequest(request);
			
			if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
				String username = tokenProvider.getUsernameFromToken(token);
				String role = tokenProvider.getRoleFromToken(token);
				
				// Create authentication object
				UsernamePasswordAuthenticationToken authentication = 
					new UsernamePasswordAuthenticationToken(
						username, 
						null, 
						Collections.singletonList(new SimpleGrantedAuthority(role))
					);
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				// Set authentication in security context
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				LOGGER.debug("JWT token validated for user: {}", username);
			}
		} catch (Exception e) {
			LOGGER.error("JWT authentication filter error: {}", e.getMessage());
		}
		
		filterChain.doFilter(request, response);
	}

	/**
	 * Extract JWT token from Authorization header.
	 * @param request the HTTP request
	 * @return JWT token string or null if not found
	 */
	private String extractTokenFromRequest(HttpServletRequest request) {
		String authHeader = request.getHeader(JwtConstants.JWT_HEADER);
		
		if (StringUtils.hasText(authHeader) && authHeader.startsWith(JwtConstants.JWT_PREFIX)) {
			return authHeader.substring(JwtConstants.JWT_PREFIX.length());
		}
		
		return null;
	}
}
