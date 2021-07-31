package com.mongodb.springboot.controller;

/**
 * Base Controller to define all incoming request. Serve respective business
 * logic for incoming request URIs.
 * 
 * @author metanoia
 * @version %I%, %G%
 * @since 1.0
 */

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
import com.mongodb.springboot.exception.NullRecordsFoundException;
import com.mongodb.springboot.model.Course;
import com.mongodb.springboot.service.FileStorageService;

@Controller
public class BaseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BaseController.class);

	@Autowired
	ConfigProperties configProp;

	@Autowired
	FileStorageService storageService;

	String viewName = "";

	/**
	 * @return the web view of available(Read operation) course(s) in the
	 *         system.
	 */
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public ModelAndView showCourses(HttpServletRequest request) {

		LOGGER.info("View available Courses ");

		ModelAndView model = new ModelAndView();

		model.addObject("basePath", getBasePath(request));

		// TODO Integrate business layer
		List<Course> courseList = getDummyCourseList();

		if (ObjectUtils.isEmpty(courseList)) {
			throw new NullRecordsFoundException(
					"Null Records returned by business layer");
		}

		if (courseList.size() > 0) {
			model.addObject("hasCourses", Boolean.TRUE);
			model.addObject("courseList", courseList);
		} else {
			model.addObject("hasCourses", Boolean.FALSE);
		}

		model.setViewName("viewCourses");

		return model;

	}

	/**
	 * @return the web view to add a new course in the system.
	 */
	@RequestMapping(value = "/addCourse", method = RequestMethod.GET)
	public ModelAndView addCourse(HttpServletRequest request) {

		LOGGER.info("Get Add Course");

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("course", new Course());
		modelAndView.addObject("maxUploadSize",
				configProp.getMaxSizeFileUpload());
		modelAndView.setViewName("addCourse");
		return modelAndView;

	}

	/**
	 * Method to add new course to the database. Image file for every course
	 * will be upload/loaded with the help of service {@link FileStorageService}
	 * 
	 * @param arguments
	 *            to be retrieved from the model.In this case model object with
	 *            form parameters (coming along with POST request) to be
	 *            inserted to our database.
	 * 
	 * @return the success page after add(Insert operation) a new course in the
	 *         system.
	 */
	@RequestMapping(value = "/addCourse", method = RequestMethod.POST)
	public String addCourse(@ModelAttribute("course") Course course) {

		LOGGER.info("Add new course tO database");

		ModelAndView modelAndView = new ModelAndView();

		if (!ObjectUtils.isEmpty(course.getFileInput().getOriginalFilename())) {

			String fileUploadName = course.getFileInput().getOriginalFilename();

			course.setFileName(fileUploadName);

			String requestParameters = String.format(
					" <<<-- Request parameters are -->>> %s",
					course.toString());

			LOGGER.info("Add course with image  " + requestParameters);

			storageService.save(course.getFileInput());

		} else {

			String requestParameters = String.format(
					" <<<-- Request parameters are -->>> %s",
					course.toString());

			LOGGER.info("Add course without any image  " + requestParameters);

		}

		viewName = "200";
		modelAndView.setViewName(viewName);
		// return modelAndView;

		return "redirect:/";

	}

	/**
	 * @param courseId
	 *            of the course to edit.
	 * 
	 * @return the web view to edit course information for the selected course.
	 */
	@RequestMapping(value = "/editCourse", method = RequestMethod.GET)
	public ModelAndView editCourse(@RequestParam String id) {

		LOGGER.info("Edit Course For Id = " + id);

		// TODO Integrate with business logic
		Course course = getDummyData();
		Resource resource = storageService.load(course.getFileName());
		course.setFileName(resource.getFilename());

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("course", course);
		modelAndView.addObject("maxUploadSize",
				configProp.getMaxSizeFileUpload());
		modelAndView.setViewName("editCourse");

		return modelAndView;

	}

	/**
	 * Method to edit information for selected course into the database. Image
	 * file for every course will be upload/loaded with the help of service
	 * {@link FileStorageService}
	 * 
	 * @param arguments
	 *            to be retrieved from the model.In this case model object with
	 *            form parameters (coming along with POST request) to be updated
	 *            with new information for the selected course id.
	 * 
	 * @return the success page after edit(Update operation) for selected course
	 *         in the system.
	 */
	@RequestMapping(value = "/editCourse", method = RequestMethod.POST)
	public String editCourse(@ModelAttribute("course") Course course) {

		LOGGER.info("Edit Course to Database");

		// TODO Business Logic

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("course", getDummyData());
		modelAndView.setViewName("editCourse");
		return "redirect:/";

	}

	/**
	 * Method to delete record(s) for selected course(s) from the database.
	 * Image file for every course will be removed from directory to store
	 * images too with the help of service {@link FileStorageService}
	 * 
	 * @param an
	 *            array of selected id(s) of course(s) to be removed from
	 *            database.
	 * 
	 * @return the success page after removing(Delete operation) records of
	 *         selected course(s)
	 */
	@RequestMapping(value = "/deleteCourses", method = RequestMethod.POST)
	public ModelAndView deleteCourses(HttpServletRequest request) {

		LOGGER.info("Delete Course");

		// TODO business logic
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

	// TODO To be removed later with real time database interaction to fetch
	// selected records
	public Course getDummyData() {
		Course course = new Course();

		course.setCourseId("Q0011");
		course.setCourseName("Quarkus");
		course.setTrainerName("Red Foxman");
		course.setDuration(8);
		course.setTotalSeats(50);
		course.setCourseFee(500.00);
		course.setStartDate(new Date().toString());
		course.setFileName("mongodb-replica-set.png");

		return course;

	}

	// TODO To be removed later with real time database interaction to fetch
	// all available records.
	public List<Course> getDummyCourseList() {

		List<Course> courseList = new ArrayList<Course>();

		Course course1 = new Course();

		course1.setCourseId("Q0011");
		course1.setCourseName("Quarkus");
		course1.setTrainerName("Red Foxman");
		course1.setDuration(8);
		course1.setTotalSeats(50);
		course1.setCourseFee(500.00);
		course1.setStartDate(new Date().toString());
		course1.setFileName("mongodb-replica-set.png");

		Course course2 = new Course();

		course2.setCourseId("G0011");
		course2.setCourseName("GraalVM");
		course2.setTrainerName("Frank Finn");
		course2.setDuration(3);
		course2.setTotalSeats(20);
		course2.setCourseFee(5500.59);
		course2.setStartDate(new Date().toString());
		course2.setFileName("K.png");

		courseList.add(course1);
		courseList.add(course2);

		return courseList;

	}

}