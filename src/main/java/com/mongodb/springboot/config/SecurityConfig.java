package com.mongodb.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mongodb.springboot.security.JwtAuthenticationFilter;
import com.mongodb.springboot.security.JwtTokenProvider;

/**
 * Spring Security configuration.
 * Configures JWT-based stateless authentication and authorization.
 * 
 * @author metanoia
 * @since 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	/**
	 * Configure password encoder bean.
	 * @return BCryptPasswordEncoder for password hashing
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Create JWT authentication filter bean.
	 * @return JwtAuthenticationFilter instance
	 */
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	/**
	 * Configure HTTP security for the application.
	 * @param http HttpSecurity object
	 * @return SecurityFilterChain
	 * @throws Exception if configuration fails
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			// Disable CSRF for stateless API
			.csrf()
				.disable()
			// Set session management to stateless
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			// Configure authorization
			.authorizeRequests()
				// Allow public access to auth endpoints
				.antMatchers("/api/auth/**").permitAll()
				// Allow public access to health check
				.antMatchers("/health").permitAll()
				// Allow access to static resources
				.antMatchers("/static/**").permitAll()
				// Allow access to JSP views
				.antMatchers("/").permitAll()
				.antMatchers("/addCourse").permitAll()
				// All other requests require authentication
				.anyRequest().authenticated()
			.and()
			// Add JWT filter before username password filter
			.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
