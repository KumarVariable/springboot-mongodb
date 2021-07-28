package com.mongodb.springboot;

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

/**
 * @SpringBootApplication is a convenience annotation that adds all of the
 *                        following:-
 * 
 * @Configuration: Tags the class as a source of bean definitions for the
 *                 application context.
 * 
 * @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on
 *                           classpath settings, other beans, and various
 *                           property settings. For example, if spring-webmvc is
 *                           on the classpath, this annotation flags the
 *                           application as a web application and activates key
 *                           behaviors, such as setting up a DispatcherServlet.
 * 
 * @ComponentScan: Tells Spring to look for other components, configurations,
 *                 and services in the com/example package, letting it find the
 *                 controllers.
 */
@SpringBootApplication
@ConfigurationPropertiesScan("com.mongodb.springboot.config.properties")
public class SpringBootWebApplication extends SpringBootServletInitializer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootWebApplication.class);

	/**
	 * @SpringBootServletInitializer: An opinionated WebApplicationInitializer to
	 *                                run a SpringApplication from a traditional WAR
	 *                                deployment.
	 * 
	 *                                To configure the application either override
	 *                                the configure(SpringApplicationBuilder)
	 *                                method.
	 * 
	 *                                A WebApplicationInitializer is only needed if
	 *                                you are building a war file and deploying it.
	 *                                If preference is to run an embedded web server
	 *                                then you won't need this at all.
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootWebApplication.class);
	}

	// Main method to launch an application
	public static void main(String[] args) throws Exception {

		LOGGER.debug("BootStart Application - SpringBoot With MongoDB");

		SpringApplication.run(SpringBootWebApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			String beanNames[] = ctx.getBeanDefinitionNames();

			Arrays.sort(beanNames);

			// LOGGER.info("Configured bean provided by Spring boot: {} ",
			// Arrays.toString(beanNames));

		};
	}
	
}