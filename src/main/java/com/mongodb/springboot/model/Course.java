package com.mongodb.springboot.model;

import org.springframework.web.multipart.MultipartFile;

public class Course {

	private int totalSeats;
	private int duration;
	private double courseFee;

	private String courseId;
	private String courseName;
	private String trainerName;
	private String startDate;
	private String fileName;
	private String loadFileFromLocation;

	private MultipartFile fileInput;

	public int getTotalSeats() {
		return totalSeats;
	}
	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public double getCourseFee() {
		return courseFee;
	}
	public void setCourseFee(double courseFee) {
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
	public MultipartFile getFileInput() {
		return fileInput;
	}
	public void setFileInput(MultipartFile fileInput) {
		this.fileInput = fileInput;
	}

	public String getLoadFileFromLocation() {
		return loadFileFromLocation;
	}
	public void setLoadFileFromLocation(String loadFileFromLocation) {
		this.loadFileFromLocation = loadFileFromLocation;
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