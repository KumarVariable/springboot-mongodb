package com.mongodb.springboot.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.springboot.exception.FileStorageException;
import com.mongodb.springboot.service.FileStorageService;

/**
 * Service Implementation for {@link FileStorageService} to handle our file
 * system for handling files related to storage operations.
 */

@Service
public class FileStorageServiceImpl implements FileStorageService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FileStorageServiceImpl.class);

	@Value("${spring.storeFileToLocation}")
	private String uploadFileLocation;

	private static Path rootLocation;

	@Value("${spring.storeFileToLocation}")
	public void setRootLocation(String uploadFileLocation) {
		FileStorageServiceImpl.rootLocation = Paths
				.get(uploadFileLocation.trim());
	}

	/**
	 * Method annotated with {@link PostConstruct} will be called only once just
	 * after initialization of bean properties. Initialize to create file system
	 * directory so as to handle our system's file management.
	 */
	@Override
	@PostConstruct
	public void init() {

		try {
			LOGGER.info("<<< --- Uncomment code to create \"images\" directory "
					+ "through boot application --- >>> ");
			// Files.createDirectories(rootLocation);
		} catch (Exception e) {
			LOGGER.error("Could not initialize storage location -->>> ", e);
		}
	}

	/**
	 * @param multipart
	 *            file to be stored/saved to directory or file system.
	 * 
	 */
	@Override
	public void save(MultipartFile file) {

		LOGGER.info(String.format(
				"Root Directory Location %s , Upload File location %s",
				rootLocation, uploadFileLocation));
		try {

			File destination = new File(uploadFileLocation
					.concat(file.getOriginalFilename().trim()));

			file.transferTo(destination);

		} catch (IllegalStateException e) {
			LOGGER.error("IllegalStateException -->>> ", e);
		} catch (IOException ioException) {
			String ioExceptionFormat = String.format(
					"Could not save image file: %s %s",
					file.getOriginalFilename().trim(), ioException);
			throw new FileStorageException(ioExceptionFormat);

		}

	}

	/**
	 * @param name
	 *            of file to be loaded/retrieved from directory or file system.
	 * 
	 * @return {@link Resource}
	 */
	@Override
	public Resource load(String filename) {

		try {

			Path filePath = rootLocation.resolve(filename);
			Resource resource = new UrlResource(filePath.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				LOGGER.info(String.format(
						"Could not load image file Or Image unavailable in source location: %s",
						filename));
				return resource;
			}

		} catch (MalformedURLException malformedURLException) {
			LOGGER.error("MalformedURLException -->>> ", malformedURLException);
		}

		return null;
	}

	/**
	 * Method annotated with {@link PreDestroy} runs only once to inform Spring
	 * to perform clean up tasks before the bean gets destroyed when the
	 * container shuts down.
	 */
	@Override
	@PreDestroy
	public void deleteAll() {
		LOGGER.info(
				"<<< --- Uncomment code if want to delete \"images\" directory "
						+ "once container shutsdown --- >>> ");
		// FileSystemUtils.deleteRecursively(rootLocation.toFile());

	}

	/**
	 * load all files from specified directory or file location
	 * 
	 */
	@Override
	public Stream<Path> loadAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
