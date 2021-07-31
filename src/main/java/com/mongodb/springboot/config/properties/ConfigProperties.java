package com.mongodb.springboot.config.properties;

/**
 * 
 * This class binds and validates application specific(external) Properties (e.g
 * from a *.properties file). Binding is either performed by calling setters on
 * the annotated class or binding to the constructor parameters.
 * 
 * @see {@link ConfigurationProperties}
 * 
 * @author	metanoia
 * @version	%I%, %G%
 * @since	1.0
 * 
 */

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/*
 * element 'prefix' to instruct {@link ConfigurationProperties} 
 * to bind all properties(*.properties) that start with 'spring' to the 
 * corresponding attributes of POJO class when the application is started.
 */
@Configuration
@ConfigurationProperties(prefix = "spring")
public class ConfigProperties {

	private String storeFileToLocation;
	
	private String maxSizeFileUpload;

	public String getStoreFileToLocation() {
		return storeFileToLocation;
	}

	public void setStoreFileToLocation(String storeFileToLocation) {
		this.storeFileToLocation = storeFileToLocation;
	}
	
	public String getMaxSizeFileUpload() {
		return maxSizeFileUpload;
	}

	public void setMaxSizeFileUpload(String maxSizeFileUpload) {
		this.maxSizeFileUpload = maxSizeFileUpload;
	}

}
