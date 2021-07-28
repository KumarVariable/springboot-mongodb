/**
 * 
 */
package com.mongodb.springboot.controller;

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

/**
 * Controller to display custom error pages. Add server.error.path property in
 * application.properties file.
 * 
 * @author metanoia
 *
 */

@Controller
public class ApplicationErrorController implements ErrorController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ApplicationErrorController.class);

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
					return errorPage;

				case INTERNAL_SERVER_ERROR :

					errorPage = "500";
					return errorPage;

				default :
					break;
			}

		}

		return "error";
	}

}
