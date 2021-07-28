package com.mongodb.springboot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

/**
 * @author metanoia
 * 
 *         {@summary Configuration to manage Mongo database.}
 *
 * @Configuration: Tags the class as a source of one or more bean definitions to
 *                 be processed by the Spring container.
 * @Service: Tags the class a service offered as as interface that stands alone
 *           in the model. This annotations allows implementation classes to be
 *           autodetected through classpath scanning
 */

@Configuration
public class MongoDBConfiguration {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MongoDBConfiguration.class);

	@Value("${spring.data.mongodb.host}")
	String DB_HOST_NAME;

	@Value("${spring.data.mongodb.port}")
	String DB_PORT_NUMBER;

	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public MongoClient getMongoClient() {

		MongoClient mongoClient = null;

		try {
			StringBuilder connectionString = new StringBuilder(
					String.format("mongodb://%s:%d", DB_HOST_NAME,
							Integer.parseInt(DB_PORT_NUMBER)));

			ConnectionString connString = new ConnectionString(
					connectionString.toString());

			WriteConcern writeConcern = new WriteConcern(1, 3000);

			MongoClientSettings clientSettings = MongoClientSettings.builder()
					.applyConnectionString(connString)
					.writeConcern(writeConcern).build();

			mongoClient = MongoClients.create(clientSettings);

		} catch (Exception ex) {

			LOGGER.error("Exception occured to get Mongo Clint(Instance) %s ",
					ex);
		}

		return mongoClient;
	}

}
