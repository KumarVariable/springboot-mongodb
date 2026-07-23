package com.mongodb.springboot.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.springboot.config.properties.ConfigProperties;
import com.mongodb.springboot.model.Course;
import com.mongodb.springboot.service.FileStorageService;

/**
 * JUnit Test Class for BaseController
 * 
 * Tests the main controller endpoints for Course management:
 * - GET / (showCourses)
 * - GET /addCourse
 * - POST /addCourse
 * - GET /editCourse
 * 
 * @author Test Suite
 * @version 1.0
 */
@WebMvcTest(BaseController.class)
@DisplayName("BaseController Unit Tests")
class BaseControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ConfigProperties configProperties;

	@MockBean
	private FileStorageService fileStorageService;

	private List<Course> dummyCourseList;
	private Course testCourse;

	/**
	 * Setup method - runs before each test
	 * Initializes test data and mock configurations
	 */
	@BeforeEach
	void setUp() {
		// Initialize dummy course list
		dummyCourseList = new ArrayList<>();
		
		// Create test course 1
		Course course1 = new Course();
		course1.setCourseId("C001");
		course1.setCourseName("Java Programming");
		course1.setTrainerName("John Doe");
		course1.setDuration(3);
		course1.setTotalSeats(30);
		course1.setCourseFee(500.0);
		course1.setStartDate("2026-02-01");
		course1.setFileName("java-course.jpg");
		dummyCourseList.add(course1);

		// Create test course 2
		Course course2 = new Course();
		course2.setCourseId("C002");
		course2.setCourseName("Spring Boot");
		course2.setTrainerName("Jane Smith");
		course2.setDuration(2);
		course2.setTotalSeats(25);
		course2.setCourseFee(600.0);
		course2.setStartDate("2026-03-01");
		course2.setFileName("spring-course.jpg");
		dummyCourseList.add(course2);

		// Initialize test course
		testCourse = new Course();
		testCourse.setCourseId("C001");
		testCourse.setCourseName("Java Programming");
		testCourse.setTrainerName("John Doe");
		testCourse.setDuration(3);
		testCourse.setTotalSeats(30);
		testCourse.setCourseFee(500.0);
		testCourse.setStartDate("2026-02-01");
		testCourse.setFileName("java-course.jpg");

		// Mock ConfigProperties
		when(configProperties.getMaxSizeFileUpload()).thenReturn("10MB");
		
		// Mock FileStorageService.load() to return null by default
		when(fileStorageService.load(anyString())).thenReturn(null);
	}

	/**
	 * Test Case 1: Test GET / endpoint - showCourses
	 * 
	 * Expected Behavior:
	 * - HTTP Status 200 OK
	 * - View name should be "viewCourses"
	 * - Model should contain "hasCourses" attribute set to true
	 * - Model should contain "courseList" attribute
	 * - Model should contain "basePath" attribute
	 */
	@Test
	@DisplayName("Test GET / - Should return course list view with status 200")
	void testShowCourses_Success() throws Exception {
		// Perform GET request to root path
		MvcResult result = mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("viewCourses"))
				.andExpect(model().attributeExists("hasCourses"))
				.andExpect(model().attributeExists("courseList"))
				.andExpect(model().attributeExists("basePath"))
				.andReturn();

		// Additional assertions on ModelAndView
		ModelAndView modelAndView = result.getModelAndView();
		assertNotNull(modelAndView, "ModelAndView should not be null");
		assertEquals("viewCourses", modelAndView.getViewName(), 
			"View name should be 'viewCourses'");
		
		Object hasCourses = modelAndView.getModel().get("hasCourses");
		assertNotNull(hasCourses, "hasCourses attribute should exist");
		assertTrue((Boolean) hasCourses, "hasCourses should be true when courses exist");
	}

	/**
	 * Test Case 2: Test GET / with slash path
	 * 
	 * Expected Behavior:
	 * - Should behave same as GET /
	 * - HTTP Status 200 OK
	 * - View name should be "viewCourses"
	 */
	@Test
	@DisplayName("Test GET / slash path - Should return course list view")
	void testShowCoursesEmptyPath_Success() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("viewCourses"))
				.andExpect(model().attributeExists("hasCourses"))
				.andExpect(model().attributeExists("courseList"));
	}

	/**
	 * Test Case 3: Test GET /addCourse - Show add course form
	 * 
	 * Expected Behavior:
	 * - HTTP Status 200 OK
	 * - View name should be "addCourse"
	 * - Model should contain empty "course" object
	 * - Model should contain "maxUploadSize" from ConfigProperties
	 */
	@Test
	@DisplayName("Test GET /addCourse - Should return add course form view")
	void testAddCourseGetForm_Success() throws Exception {
		MvcResult result = mockMvc.perform(get("/addCourse"))
				.andExpect(status().isOk())
				.andExpect(view().name("addCourse"))
				.andExpect(model().attributeExists("course"))
				.andExpect(model().attributeExists("maxUploadSize"))
				.andReturn();

		// Verify the maxUploadSize is set correctly
		ModelAndView modelAndView = result.getModelAndView();
		assertNotNull(modelAndView, "ModelAndView should not be null");
		
		Object maxUploadSize = modelAndView.getModel().get("maxUploadSize");
		assertNotNull(maxUploadSize, "maxUploadSize attribute should exist");
		assertEquals("10MB", maxUploadSize, "maxUploadSize should match configured value");
	}



	/**
	 * Test Case 6: Test that ConfigProperties is properly injected
	 * 
	 * Expected Behavior:
	 * - ConfigProperties bean should be available
	 * - Should return configured max upload size
	 */
	@Test
	@DisplayName("Test ConfigProperties injection - Should be available in controller")
	void testConfigPropertiesInjection() {
		assertNotNull(configProperties, "ConfigProperties should be injected");
		assertEquals("10MB", configProperties.getMaxSizeFileUpload(), 
			"Max upload size should be 10MB");
	}

	/**
	 * Test Case 7: Test that FileStorageService is properly mocked
	 * 
	 * Expected Behavior:
	 * - FileStorageService bean should be mocked
	 * - Should be available for injection
	 */
	@Test
	@DisplayName("Test FileStorageService injection - Should be mocked")
	void testFileStorageServiceMocking() {
		assertNotNull(fileStorageService, "FileStorageService should be mocked");
		// Verify it's a mock
		verify(fileStorageService, never()).save(any());
	}



	/**
	 * Test Case 9: Test response status is OK
	 * 
	 * Expected Behavior:
	 * - Response should return HTTP 200 OK status
	 */
	@Test
	@DisplayName("Test response status - Should return 200 OK")
	void testResponseHeaders() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("viewCourses"));
	}

	/**
	 * Test Case 10: Test that basePath is correctly set in model
	 * 
	 * Expected Behavior:
	 * - basePath should be present in model
	 * - Should contain request context information
	 */
	@Test
	@DisplayName("Test basePath in model - Should be set from request")
	void testBasePathInModel() throws Exception {
		MvcResult result = mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andReturn();

		ModelAndView modelAndView = result.getModelAndView();
		assertNotNull(modelAndView, "ModelAndView should not be null");
		
		Object basePath = modelAndView.getModel().get("basePath");
		assertNotNull(basePath, "basePath should be set in model");
	}
}
