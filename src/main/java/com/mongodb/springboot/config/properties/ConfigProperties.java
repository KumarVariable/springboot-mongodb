package com.mongodb.springboot.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring")
public class ConfigProperties {

	private String storeFileToLocation;

	public String getStoreFileToLocation() {
		return storeFileToLocation;
	}

	public void setStoreFileToLocation(String storeFileToLocation) {
		this.storeFileToLocation = storeFileToLocation;
	}

}
