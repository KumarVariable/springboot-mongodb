package com.mongodb.springboot.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.springboot.exception.FileStorageException;
import com.mongodb.springboot.service.FileStorageService;

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

	@Override
	@PostConstruct
	public void init() {
		try {
			LOGGER.info("<<< --- Uncomment code to create \"images\" directory "
					+ "through boot application --- >>> ");
		} catch (Exception e) {
			LOGGER.error("Could not initialize storage location -->>> ", e);
		}
	}

	@Override
	public void save(MultipartFile file) {
		LOGGER.info("Root Directory Location {} , Upload File location {}",
				rootLocation, uploadFileLocation);
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

	@Override
	public Resource load(String filename) {
		try {
			Path filePath = rootLocation.resolve(filename);
			Resource resource = new UrlResource(filePath.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				LOGGER.info("Could not load image file Or Image unavailable in source location: {}",
						filename);
				return resource;
			}
		} catch (MalformedURLException malformedURLException) {
			LOGGER.error("MalformedURLException -->>> ", malformedURLException);
		}

		return null;
	}

	@Override
	@PreDestroy
	public void deleteAll() {
		LOGGER.info("<<< --- Uncomment code if want to delete \"images\" directory "
				+ "once container shuts down --- >>> ");
	}

	@Override
	public Stream<Path> loadAll() {
		return null;
	}

}
