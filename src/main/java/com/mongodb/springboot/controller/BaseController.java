package com.mongodb.springboot.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mongodb.springboot.config.properties.ConfigProperties;
import com.mongodb.springboot.model.Course;
import com.mongodb.springboot.service.FileStorageService;

/**
 * {@summary Base Controller to define all incoming request. URI's to serve
 * corresponding screen to user
 * <ul>
 * <li>This controller will be considered as Base for other controllers.</li>
 * <li>Define global constants,variables in Base Controller only</li>
 * </ul>
 * }
 * 
 * @author metanoia
 * @version 1.0
 */
@Controller
public class BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BaseController.class);

	/*
	 * @Autowired private MongoDBConfiguration mongoConfig;
	 */

	@Autowired
	ConfigProperties configProp;

	@Autowired
	FileStorageService storageService;

	String viewName = "";

	/**
	 * Returns main screen (landing page - About Us).
	 *
	 * @param request
	 *            URI or pattern
	 */
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public ModelAndView showCourses(HttpServletRequest request) {

		LOGGER.info("Show Courses ");

		ModelAndView model = new ModelAndView();

		model.addObject("basePath", getBasePath(request));

		// TODO Integrate business layer
		List<Course> courseList = getDummyCourseList();

		if (courseList.size() > 0) {
			model.addObject("hasCourses", Boolean.TRUE);
			model.addObject("courseList", courseList);
		} else {
			model.addObject("hasCourses", Boolean.FALSE);
		}
		
		model.setViewName("viewCourses");

		return model;

	}

	@RequestMapping(value = "/addCourse", method = RequestMethod.GET)
	public ModelAndView addCourse(HttpServletRequest request) {

		LOGGER.info("Get Add Course");

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("course", new Course());
		modelAndView.setViewName("addCourse");
		return modelAndView;

	}

	@RequestMapping(value = "/addCourse", method = RequestMethod.POST)
	public ModelAndView addCourse(@ModelAttribute("course") Course course) {

		LOGGER.info("Post Course TO Database");

		ModelAndView modelAndView = new ModelAndView();

		if (!ObjectUtils.isEmpty(course.getFileInput().getOriginalFilename())) {

			String fileUploadName = course.getFileInput().getOriginalFilename();

			course.setFileName(fileUploadName);

			String requestParameters = String.format(
					" <<<-- Request parameters are -->>> %s",
					course.toString());

			LOGGER.info("Add course with any image  " + requestParameters);

			storageService.save(course.getFileInput());

		} else {

			String requestParameters = String.format(
					" <<<-- Request parameters are -->>> %s",
					course.toString());

			LOGGER.info("Add course without any image  " + requestParameters);

		}

		viewName = "200";
		modelAndView.setViewName(viewName);
		return modelAndView;

	}

	@RequestMapping(value = "/editCourse", method = RequestMethod.GET)
	public ModelAndView editCourse(@RequestParam String id) {
		
		LOGGER.info("Edit Course For Id = " + id);

		//TODO Integrate with business logic
		Course course = getDummyData();
		Resource resource = storageService.load(course.getFileName());
		course.setFileName(resource.getFilename());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("course", course);
		modelAndView.setViewName("editCourse");
		
		return modelAndView;

	}

	@RequestMapping(value = "/editCourse", method = RequestMethod.POST)
	public ModelAndView editCourse(@ModelAttribute("course") Course course) {

		LOGGER.info("Edit Course to Database");
		
		//TODO Business Logic

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("course", getDummyData());
		modelAndView.setViewName("editCourse");
		return modelAndView;

	}

	@RequestMapping(value = "/deleteCourses", method = RequestMethod.POST)
	public ModelAndView deleteCourses(HttpServletRequest request) {

		LOGGER.info("Delete Course");
		
		//TODO business logic
		List<String> deleteIdsList = new ArrayList<String>();

		if (!ObjectUtils.isEmpty(request.getParameter("selectedIds"))) {
			String[] selectedIds = request.getParameter("selectedIds")
					.split(",");

			for (String ids : selectedIds) {
				deleteIdsList.add(ids);
			}
		}

		LOGGER.info(String.format("Delete following records %s",
				deleteIdsList.toString()));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("200");
		return modelAndView;

	}

	public String getBasePath(HttpServletRequest request) {

		String basePath = ServletUriComponentsBuilder.fromRequestUri(request)
				.replacePath(null).build().toUriString();

		basePath = basePath.concat("/");
		request.getSession().setAttribute("basePath", basePath);
		return basePath;
	}

	/*
	 * @GetMapping("/hello/**") public String testMongoDBConnectio() {
	 * 
	 * List<String> databases = new ArrayList<String>();
	 * 
	 * MongoClient client = mongoConfig.getMongoClient();
	 * 
	 * MongoIterable<String> databasesList = client.listDatabaseNames();
	 * 
	 * Iterator<String> iterator = databasesList.iterator();
	 * 
	 * while (iterator.hasNext()) { databases.add(iterator.next()); }
	 * 
	 * LOGGER.info("Current Available Databases" + databases);
	 * 
	 * return "hello"; }
	 */

	public Course getDummyData() {
		Course course = new Course();

		course.setCourseId("Q0011");
		course.setCourseName("Quarkus");
		course.setTrainerName("Red Foxman");
		course.setDuration(8);
		course.setTotalSeats(50);
		course.setCourseFee(500);
		course.setStartDate(new Date().toString());
		course.setFileName("mongodb-replica-set.png");

		return course;

	}

	public List<Course> getDummyCourseList() {

		List<Course> courseList = new ArrayList<Course>();

		Course course1 = new Course();

		course1.setCourseId("Q0011");
		course1.setCourseName("Quarkus");
		course1.setTrainerName("Red Foxman");
		course1.setDuration(8);
		course1.setTotalSeats(50);
		course1.setCourseFee(500);
		course1.setStartDate(new Date().toString());
		course1.setFileName("mongodb-replica-set.png");

		Course course2 = new Course();

		course2.setCourseId("G0011");
		course2.setCourseName("GraalVM");
		course2.setTrainerName("Frank Finn");
		course2.setDuration(3);
		course2.setTotalSeats(20);
		course2.setCourseFee(5500);
		course2.setStartDate(new Date().toString());
		course2.setFileName("K.png");

		courseList.add(course1);
		courseList.add(course2);

		return courseList;

	}

}