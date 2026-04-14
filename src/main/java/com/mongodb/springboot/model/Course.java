package com.mongodb.springboot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

@Document(collection = "courses")
public class Course {

	@Id
	private String courseId;

	private String courseName;

	private String trainerName;

	private Integer totalSeats;

	private Integer duration;

	private Double courseFee;

	private String startDate;

	private String fileName;

	private String loadFileFromLocation;

	@Transient
	private MultipartFile fileInput;

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
		return "Course [courseId=" + courseId + ", courseName=" + courseName
				+ ", trainerName=" + trainerName + ", totalSeats=" + totalSeats
				+ ", duration=" + duration + ", courseFee=" + courseFee
				+ ", startDate=" + startDate + ", fileName=" + fileName + "]";
	}

}
