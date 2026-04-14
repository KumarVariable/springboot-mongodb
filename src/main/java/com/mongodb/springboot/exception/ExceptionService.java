package com.mongodb.springboot.exception;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExceptionService.class);

	@ExceptionHandler(NullRecordsFoundException.class)
	public ModelAndView handleRecordNotFoundException(
			NullRecordsFoundException ex, HttpServletRequest request) {

		LOGGER.error("Request: {} raised {}", request.getRequestURL(), ex);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("erroMessage",
				"Sorry! Something went wrong. Please try again after some time.");
		modelAndView.setViewName("500");

		return modelAndView;
	}

	@ExceptionHandler(FileStorageException.class)
	public ModelAndView handleFileStorageException(
			FileStorageException fileStorageException, HttpServletRequest request) {

		LOGGER.error("Request: {} raised {}", request.getRequestURL(), fileStorageException);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("erroMessage",
				"This is awkward. We are having really a bad day. Our bad.");
		modelAndView.setViewName("500");

		return modelAndView;
	}

}
