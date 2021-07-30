package com.mongodb.springboot.model;

/** 
 * Represents a course.
 * @author metanoia
 * @version %I%, %G%
 * @since 1.0
*/

import org.springframework.web.multipart.MultipartFile;

public class Course {

	/**
	 * Represents the total intakes of student for this course.
	 */
	private Integer totalSeats;

	/**
	 * Represents the duration(months) required to complete this course.
	 */
	private Integer duration;

	/**
	 * Represents the course fee to enroll for this course.
	 */
	private Double courseFee;

	/**
	 * course Id available in database.
	 */
	private String courseId;

	/**
	 * name of course.
	 */
	private String courseName;

	/**
	 * name of trainer for the course.
	 */
	private String trainerName;

	/**
	 * start date of the course.
	 */
	private String startDate;

	/**
	 * full name of file(image file with extension) of the course.
	 */
	private String fileName;

	/**
	 * directory,file path of the image of course to be read/load
	 */
	private String loadFileFromLocation;

	/**
	 * A representation of an uploaded file received in a multipart request.
	 */
	private MultipartFile fileInput;
	

	public Integer getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(Integer totalSeats) {
		this.totalSeats = totalSeats;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Double getCourseFee() {
		return courseFee;
	}

	public void setCourseFee(Double courseFee) {
		this.courseFee = courseFee;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTrainerName() {
		return trainerName;
	}

	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getLoadFileFromLocation() {
		return loadFileFromLocation;
	}

	public void setLoadFileFromLocation(String loadFileFromLocation) {
		this.loadFileFromLocation = loadFileFromLocation;
	}

	public MultipartFile getFileInput() {
		return fileInput;
	}

	public void setFileInput(MultipartFile fileInput) {
		this.fileInput = fileInput;
	}

	@Override
	public String toString() {
		return "Course [totalSeats=" + totalSeats + ", duration=" + duration
				+ ", courseFee=" + courseFee + ", courseId=" + courseId
				+ ", courseName=" + courseName + ", trainerName=" + trainerName
				+ ", startDate=" + startDate + ", fileName=" + fileName
				+ ", loadFileFromLocation=" + loadFileFromLocation
				+ ", fileInput=" + fileInput + "]";
	}

}