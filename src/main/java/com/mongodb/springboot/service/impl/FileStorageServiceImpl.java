package com.mongodb.springboot.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.springboot.service.FileStorageService;

@Service
public class FileStorageServiceImpl implements FileStorageService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FileStorageServiceImpl.class);

	@Value("${views.uploadFileLocation}")
	private String uploadFileLocation;

	private static Path rootLocation;

	@Value("${views.uploadFileLocation}")
	public void setRootLocation(String uploadFileLocation) {
		FileStorageServiceImpl.rootLocation = Paths
				.get(uploadFileLocation.trim());
	}

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

	@Override
	public void save(MultipartFile file) {

		LOGGER.info("uploadFileLocation " + uploadFileLocation);
		LOGGER.info("rootLocation " + rootLocation);

		try {

			File destination = new File(uploadFileLocation
					.concat(file.getOriginalFilename().trim()));

			file.transferTo(destination);

		} catch (IllegalStateException e) {
			LOGGER.error("IllegalStateException -->>> ", e);
		} catch (IOException e) {
			LOGGER.error("IOException -->>> ", e);
		}

	}

	@Override
	public Resource load(String filename) {

		try {

			Path filePath = rootLocation.resolve(filename);
			Resource resource = new UrlResource(filePath.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read the file!");
			}

		} catch (MalformedURLException e) {
			LOGGER.error("MalformedURLException -->>> ", e);
		}

		return null;
	}

	@Override
	@PreDestroy
	public void deleteAll() {
		LOGGER.info(
				"<<< --- Uncomment code if want to delete \"images\" directory "
						+ "once container shutsdown --- >>> ");
		// FileSystemUtils.deleteRecursively(rootLocation.toFile());

	}

}
