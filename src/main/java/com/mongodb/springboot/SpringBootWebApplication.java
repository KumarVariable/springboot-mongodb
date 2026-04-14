package com.mongodb.springboot;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan("com.mongodb.springboot.config.properties")
public class SpringBootWebApplication {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SpringBootWebApplication.class);

	public static void main(String[] args) throws Exception {
		LOGGER.debug("BootStart Application - SpringBoot With MongoDB");
		SpringApplication.run(SpringBootWebApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
		};
	}

}
