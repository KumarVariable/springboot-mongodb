package com.mongodb.springboot.controller;

/**
 * 
 * Custom {@link ErrorController} implemented to disable and customize the
 * default Whitelabel error page for a Spring boot application to show a concise
 * page/screen that originates from the underlying application container.
 * 
 * To disable white label error page entirely. add below property in application
 * property (application.properties) of Spring boot application.
 * 
 * server.error.whitelabel.enabled=false
 * 
 * Set below property in application property (application.properties) to return
 * a custom path to call when an error occurred.
 * 
 * server.error.path=/error
 * 
 * @author	metanoia
 * @version	%I%, %G%
 * @since	1.0
 *
 */

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class ApplicationErrorController implements ErrorController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ApplicationErrorController.class);

	/*
	 * Handle the error and return the specific error page for different error
	 * types.
	 */
	@RequestMapping("/error")
	public String handleError(ModelMap map, HttpServletRequest request) {

		String errorPage = "";

		String basePath = ServletUriComponentsBuilder.fromRequestUri(request)
				.replacePath(null).build().toUriString();

		map.put("basePath", basePath.concat("/"));

		Object status = request
				.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		LOGGER.error("Invalid request " + status.toString());

		if (status != null) {
			HttpStatus httpStatus = HttpStatus.resolve((int) status);

			switch (httpStatus) {
				case NOT_FOUND :

					errorPage = "404";
					map.addAttribute("erroMessage", "Uh Oh! Page not found!");
					break;

				case INTERNAL_SERVER_ERROR :

					errorPage = "500";
					map.addAttribute("erroMessage",
							"This is awkward.We are having really a bad day.Our bad.");
					break;

				default :
					errorPage = "error";
					map.addAttribute("erroMessage",
							"Sorry! Something went wrong.Please try again after some time.");
					break;
			}

		}

		return errorPage;
	}

}
