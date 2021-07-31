package com.mongodb.springboot.exception;

public class FileStorageException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6139482562512343771L;

	public FileStorageException(String exceptionMessage) {

		super(exceptionMessage);

	}

}
