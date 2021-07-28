package com.mongodb.springboot.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "views")
public class ConfigProperties {

	private String uploadFileLocation;

	public String getUploadFileLocation() {
		return uploadFileLocation;
	}

	public void setUploadFileLocation(String uploadFileLocation) {
		this.uploadFileLocation = uploadFileLocation;
	}

}
