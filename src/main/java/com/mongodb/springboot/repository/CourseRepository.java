package com.mongodb.springboot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mongodb.springboot.model.Course;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {

}
