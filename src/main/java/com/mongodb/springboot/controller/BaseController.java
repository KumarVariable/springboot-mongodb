package com.mongodb.springboot.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mongodb.springboot.config.properties.ConfigProperties;
import com.mongodb.springboot.model.Course;
import com.mongodb.springboot.service.CourseService;
import com.mongodb.springboot.service.FileStorageService;

@Controller
public class BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BaseController.class);

	@Autowired
	ConfigProperties configProp;

	@Autowired
	FileStorageService storageService;

	@Autowired
	CourseService courseService;

	@GetMapping({"", "/"})
	public String showCourses(Model model) {
		LOGGER.info("View available Courses");

		List<Course> courseList = courseService.findAll();

		if (courseList != null && !courseList.isEmpty()) {
			model.addAttribute("hasCourses", true);
			model.addAttribute("courseList", courseList);
		} else {
			model.addAttribute("hasCourses", false);
		}

		return "viewCourses";
	}

	@GetMapping("/addCourse")
	public String addCourse(Model model) {
		LOGGER.info("Get Add Course");

		model.addAttribute("course", new Course());
		model.addAttribute("maxUploadSize", configProp.getMaxSizeFileUpload());
		return "addCourse";
	}

	@PostMapping("/addCourse")
	public String addCourse(@ModelAttribute("course") Course course, Model model) {
		LOGGER.info("Add new course to database");

		if (!ObjectUtils.isEmpty(course.getFileInput())
				&& !ObjectUtils.isEmpty(course.getFileInput().getOriginalFilename())
				&& !course.getFileInput().getOriginalFilename().isEmpty()) {

			String fileUploadName = course.getFileInput().getOriginalFilename();
			course.setFileName(fileUploadName);

			LOGGER.info("Add course with image: {}", course);
			storageService.save(course.getFileInput());
		} else {
			LOGGER.info("Add course without any image: {}", course);
			course.setFileName("");
		}

		Course savedCourse = courseService.save(course);
		model.addAttribute("course", savedCourse);
		model.addAttribute("maxUploadSize", configProp.getMaxSizeFileUpload());
		return "editCourse";
	}

	@GetMapping("/editCourse")
	public String editCourse(@RequestParam String id, Model model) {
		LOGGER.info("Edit Course For Id = {}", id);

		Course course = courseService.findById(id);

		if (course != null && course.getFileName() != null) {
			try {
				storageService.load(course.getFileName());
			} catch (Exception e) {
				course.setFileName("");
			}
		}

		model.addAttribute("course", course);
		model.addAttribute("maxUploadSize", configProp.getMaxSizeFileUpload());
		return "editCourse";
	}

	@PostMapping("/editCourse")
	public String editCourse(@ModelAttribute("course") Course course, Model model) {
		LOGGER.info("Edit Course to Database");

		Course existingCourse = courseService.findById(course.getCourseId());

		if (!ObjectUtils.isEmpty(course.getFileInput())
				&& !ObjectUtils.isEmpty(course.getFileInput().getOriginalFilename())
				&& !course.getFileInput().getOriginalFilename().isEmpty()) {

			String fileUploadName = course.getFileInput().getOriginalFilename();
			course.setFileName(fileUploadName);
			LOGGER.info("Update course information with image");
			storageService.save(course.getFileInput());
		} else if (existingCourse != null) {
			course.setFileName(existingCourse.getFileName());
		}

		Course updatedCourse = courseService.save(course);
		model.addAttribute("course", updatedCourse);
		model.addAttribute("maxUploadSize", configProp.getMaxSizeFileUpload());
		return "editCourse";
	}

	@PostMapping("/deleteCourses")
	public String deleteCourses(HttpServletRequest request) {
		LOGGER.info("Delete Course");

		List<String> deleteIdsList = new ArrayList<>();

		if (!ObjectUtils.isEmpty(request.getParameter("selectedIds"))) {
			String[] selectedIds = request.getParameter("selectedIds").split(",");
			for (String id : selectedIds) {
				deleteIdsList.add(id);
			}
		}

		LOGGER.info("Delete following records {}", deleteIdsList);
		courseService.deleteAllById(deleteIdsList);

		return "200";
	}

}
