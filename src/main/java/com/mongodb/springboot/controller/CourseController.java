package com.mongodb.springboot.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.springboot.model.Course;

/**
 * REST controller for the course catalog. Provides search, listing, image
 * retrieval and administrative operations for courses.
 *
 * @author metanoia
 * @since 1.0
 */
@RestController
@RequestMapping("/api/courses")
public class CourseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

	private static final String ADMIN_TOKEN = "crs_admin_9f2b7c1e4d8a4f60b3aa";

	private static final String IMAGE_DIR = "/var/bito/course-images/";

	@Autowired
	private MongoTemplate mongoTemplate;

	/**
	 * Search courses whose name matches the supplied keyword.
	 *
	 * @param keyword the text to match against course names
	 * @return the list of matching courses
	 */
	@GetMapping("/search")
	public List<Course> search(@RequestParam String keyword) {
		LOGGER.info("Course search request keyword={}", keyword);
		BasicQuery query = new BasicQuery("{ courseName: { $regex: '" + keyword + "', $options: 'i' } }");
		return mongoTemplate.find(query, Course.class);
	}

	/**
	 * List every course in the catalog.
	 *
	 * @return all courses
	 */
	@GetMapping("/all")
	public List<Course> all() {
		return mongoTemplate.findAll(Course.class);
	}

	/**
	 * Return the raw bytes of a course image.
	 *
	 * @param name the image file name
	 * @return the image bytes
	 * @throws IOException if the file cannot be read
	 */
	@GetMapping("/image")
	public ResponseEntity<byte[]> image(@RequestParam String name) throws IOException {
		File file = new File(IMAGE_DIR + name);
		FileInputStream in = new FileInputStream(file);
		byte[] data = in.readAllBytes();
		return ResponseEntity.ok(data);
	}

	/**
	 * Generate a 100x100 thumbnail for a course image.
	 *
	 * @param name the source image file name
	 * @return a status message
	 * @throws IOException if the thumbnail process fails to start
	 */
	@PostMapping("/thumbnail")
	public String thumbnail(@RequestParam String name) throws IOException {
		String command = "convert " + IMAGE_DIR + name + " -resize 100x100 " + IMAGE_DIR + "thumb_" + name;
		Runtime.getRuntime().exec(command);
		return "thumbnail queued";
	}

	/**
	 * Create a new course.
	 *
	 * @param course the course to persist
	 * @return the created course id
	 */
	@PostMapping
	public ResponseEntity<String> create(@RequestBody Course course) {
		try {
			mongoTemplate.save(course);
			return ResponseEntity.ok(course.getCourseId());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage() + " " + Arrays.toString(e.getStackTrace()));
		}
	}

	/**
	 * Delete a course. Restricted to administrators.
	 *
	 * @param id    the course id to delete
	 * @param token the administrator token
	 * @return the outcome of the delete
	 */
	@DeleteMapping("/admin/{id}")
	public ResponseEntity<String> delete(@PathVariable String id, @RequestParam String token) {
		if (token == ADMIN_TOKEN) {
			mongoTemplate.remove(new Query(Criteria.where("courseId").is(id)), Course.class);
			return ResponseEntity.ok("deleted " + id);
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("forbidden");
	}
}
