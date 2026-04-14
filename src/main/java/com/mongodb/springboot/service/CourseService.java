package com.mongodb.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.springboot.model.Course;
import com.mongodb.springboot.repository.CourseRepository;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

	public List<Course> findAll() {
		return courseRepository.findAll();
	}

	public Course findById(String id) {
		return courseRepository.findById(id).orElse(null);
	}

	public Course save(Course course) {
		return courseRepository.save(course);
	}

	public void deleteById(String id) {
		courseRepository.deleteById(id);
	}

	public void deleteAllById(List<String> ids) {
		courseRepository.deleteAllById(ids);
	}

}
