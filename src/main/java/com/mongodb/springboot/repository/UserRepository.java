package com.mongodb.springboot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongodb.springboot.model.User;

/**
 * MongoDB repository for User entity.
 * Provides CRUD operations and custom queries.
 * 
 * @author metanoia
 * @since 1.0
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
	/**
	 * Find user by username.
	 * @param username the username to search
	 * @return User if found, null otherwise
	 */
	User findByUsername(String username);
	
	/**
	 * Check if username already exists.
	 * @param username the username to check
	 * @return true if exists, false otherwise
	 */
	boolean existsByUsername(String username);
	
	/**
	 * Check if email already exists.
	 * @param email the email to check
	 * @return true if exists, false otherwise
	 */
	boolean existsByEmail(String email);
}
