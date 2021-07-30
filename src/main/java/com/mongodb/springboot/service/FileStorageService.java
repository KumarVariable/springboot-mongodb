package com.mongodb.springboot.service;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * An interface (or Service) to facilitate Controller to
 * interact/connect with a storage layer (e.g. a file system).
 * 
 * This interface declares abstract methods for initializing,
 * storing,loading,removing and retrieving files.
 * 
 * This interface has been taken from [https://spring.io/guides/gs/uploading-files/] 
 * with few modifications.
 * 
 */

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

	public void init();

	public void save(MultipartFile file);

	public Resource load(String filename);

	public void deleteAll();

	public Stream<Path> loadAll();

}
