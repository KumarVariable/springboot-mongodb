package com.mongodb.springboot;

/**
 * @SpringBootApplication is a convenience annotation that adds/enables all
 *                        below features:-
 * 
 * @Configuration: Tags the class as a source of bean definitions for the
 *                 application context.
 * 
 * @EnableAutoConfiguration: Automatically configures Spring Application based
 *                           on any dependencies in the classpath settings other
 *                           beans, and various property settings,then Spring
 *                           boot auto-configures an in memory database.For
 *                           example, if spring-webmvc is on the classpath, this
 *                           annotation flags the application as a web
 *                           application and activates key behaviors, such as
 *                           setting up a DispatcherServlet.
 * 
 * 
 * @ComponentScan: Tells Spring to look for other components, configurations,
 *                 and services in the com/example package, letting it find the
 *                 controllers.
 * 
 * @SpringBootServletInitializer : Extension of
 *                               {@link WebApplicationInitializer}. Allows us to
 *                               configure our application when it's run by the
 *                               Servlet container,by overriding the configure()
 *                               method.A WebApplicationInitializer is only
 *                               needed if you are building a war file and
 *                               deploying it.If preference is to run an
 *                               embedded web server then you won't need this at
 *                               all.
 *
 * 
 * @author metanoia
 * @version %I%, %G%
 * @since 1.0
 */

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan("com.mongodb.springboot.config.properties")
public class SpringBootWebApplication extends SpringBootServletInitializer {

	// Get the logger named corresponding to the class passed as parameter in a
	// class.
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SpringBootWebApplication.class);

	// Simply registering our class as a configuration class of the application.
	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(SpringBootWebApplication.class);
	}

	// Main method to launch an application
	public static void main(String[] args) throws Exception {

		LOGGER.debug("BootStart Application - SpringBoot With MongoDB");

		SpringApplication.run(SpringBootWebApplication.class, args);
	}

	/**
	 * @CommandLineRunner :An interface to indicate that a bean (or beans)
	 *                    should run when it is contained within a Spring
	 *                    application.This is a functional interface and hence
	 *                    used as a assignment target for a lambda expression or
	 *                    method reference.
	 */
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			String beanNames[] = ctx.getBeanDefinitionNames();

			Arrays.sort(beanNames);

			/*
			 * LOGGER.info("Configured bean provided by Spring boot: {} ",Arrays
			 * .toString(beanNames));
			 */

		};
	}

}