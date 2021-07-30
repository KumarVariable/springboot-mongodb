package com.mongodb.springboot.service;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.springboot.exception.NullRecordsFoundException;

@ControllerAdvice
public class ExceptionService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExceptionService.class);

	@ExceptionHandler(NullRecordsFoundException.class)
	public ModelAndView handleRecordNotFoundException(
			NullRecordsFoundException ex, HttpServletRequest request) {

		LOGGER.error("Request: " + request.getRequestURL() + " raised " + ex);

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("erroMessage",
				"Sorry! Something went wrong.Please try again after some time.");
		modelAndView.setViewName("500");

		return modelAndView;

	}

}
