package com.mongodb.springboot.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicationErrorController implements ErrorController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ApplicationErrorController.class);

	@RequestMapping("/error")
	public String handleError(Model model, HttpServletRequest request) {
		if (model == null) {
			return "error";
		}
		String errorPage = "error";

		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (status != null) {
			LOGGER.error("Invalid request {}", status);
			HttpStatus httpStatus = HttpStatus.resolve((int) status);

			if (httpStatus != null) {
				switch (httpStatus) {
					case NOT_FOUND:
						errorPage = "404";
						model.addAttribute("erroMessage", "Uh Oh! Page not found!");
						break;
					case INTERNAL_SERVER_ERROR:
						errorPage = "500";
						model.addAttribute("erroMessage",
								"This is awkward. We are having really a bad day. Our bad.");
						break;
					default:
						model.addAttribute("erroMessage",
								"Sorry! Something went wrong. Please try again after some time.");
						break;
				}
			}
		}

		return errorPage;
	}

}
