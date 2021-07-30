package com.mongodb.springboot.exception;

public class NullRecordsFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2943756035152464591L;

	public NullRecordsFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}

}
