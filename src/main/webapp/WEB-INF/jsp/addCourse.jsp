<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">

<!-- Header Include -->
<jsp:include page="header.jsp" flush="true" />

<script type="text/javascript">
	function validateUpload() {
		const MAX_UPLOAD_SIZE = document.getElementById("maxUploadSize").value; 
		var filePath = document.getElementById("fileInput").value;

		// Allowed file type
		var allowedExtensions = /(\.jpg|\.jpeg|\.png)$/i;

		if (filePath) {
			
			// Validate upload file extension
			if (!allowedExtensions.exec(filePath)) {
				alert('Invalid File type.Supported file types - [ *.jpeg / *.jpg / *.png ]');
				fileInput.value = '';

			} else {
				
				// Validate size of upload file (in MB)
				var fileUpload = document.getElementById("fileInput");
				var fileSize = parseFloat(fileUpload.files[0].size / (1024 * 1024)).toFixed(2);
				
				if (fileSize > MAX_UPLOAD_SIZE) {
					alert("File must not exceed 2 MB !");
					fileUpload.value = '';
					event.preventDefault();
				} else {
					document.getElementById("viewCourseForm").action = "/addCourse";
				}

			}

		} else {
			// No File Selected to upload
			document.getElementById("viewCourseForm").action = "/addCourse";
		}

	}
</script>

<body>

<form:form id="addCourse" method="POST" class="tm-edit-product-form" enctype="multipart/form-data" 
modelAttribute="course" onsubmit="validateUpload()"> <!-- action="/addCourse"  -->

	<!-- Nav Bar Include -->
	<jsp:include page="navigationBar.jsp" flush="true" />
	<input type="hidden" id="maxUploadSize"  value="${maxUploadSize}">

	<div class="container tm-mt-big tm-mb-big">
		<div class="row">
		<div class="col-xl-9 col-lg-10 col-md-12 col-sm-12 mx-auto">
			<div class="tm-bg-primary-dark tm-block tm-block-h-auto">
				<div class="row">
					<div class="col-12">
						<h2 class="tm-block-title d-inline-block">Add Course</h2>
					</div>
				</div>
				<div class="row tm-edit-product-row">
					<div class="col-xl-6 col-lg-6 col-md-12">
						
							<div class="form-group mb-3">
								<form:label path="courseName">Course Name</form:label>
								<form:input path="courseName" class="form-control validate" required="required" />
							</div>
							
							<div class="form-group mb-3">
								<form:label path="trainerName">Trainer Name</form:label>
								<form:input path="trainerName" class="form-control validate" required="required" />
							</div>
							
							<div class="form-group mb-3">
								<form:label path="duration">Duration</form:label>
								<form:select path="duration" class="custom-select tm-select-accounts">
									<form:option value="-1" label="Select months" />
									
									<form:option value="2" label="2 Months" />
									<form:option value="4" label="4 Months" />
									<form:option value="5" label="6 Months" />
									<form:option value="8" label="8 Months" />
									<form:option value="10" label="10 Months" />
									<form:option value="12" label="12 Months" />
									
								</form:select>
							</div>
							
							<div class="row">
								<div class="form-group mb-3 col-xs-12 col-sm-5">
									<form:label path="startDate">Start Date </form:label>
									<form:input path="startDate" class="form-control validate" data-large-mode="true" />
								</div>
								<div class="form-group mb-3 col-xs-12 col-sm-3">
									<form:label path="totalSeats">Seats</form:label>
									<form:input path="totalSeats" class="form-control validate" required="required" />
								</div>
								<div class="form-group mb-3 col-xs-12 col-sm-4">
									<form:label path="courseFee">Course Fee </form:label>
									<form:input path="courseFee" class="form-control validate" required="required" />
								</div>
							</div>
							
					</div>
					
					<div class="col-xl-6 col-lg-6 col-md-12 mx-auto mb-4">
						<div class="tm-product-img-dummy mx-auto">
						
						<img src="img/file-upload.jpg" alt="Course image" class="img-fluid d-block mx-auto" onclick="document.getElementById('fileInput').click();"> 
						
						</div>
						
						<div class="custom-file mt-3 mb-3">
						
						<form:input path="fileInput" id="fileInput" type="file" style="display:none;" />
							
						<input type="button" class="btn btn-primary btn-block mx-auto" value="UPLOAD COURSE IMAGE" 
						onclick="document.getElementById('fileInput').click();"/>
							
						</div>
					</div>
					
					<div class="col-6">
						<a href="/" class="btn btn-primary btn-block text-uppercase mb-3">Cancel</a>
					</div>
					<div class="col-6">
						<button type="submit" class="btn btn-primary btn-block text-uppercase">Add Course Now</button>
					</div>
					
				</div>
			</div>
		</div>
		</div>
	</div>
	
</form:form>
	<!-- Footer Include -->
	<jsp:include page="footer.jsp" flush="true" />

	<!-- JavaScript Libraries -->
	<jsp:include page="jsLibraries.jsp" flush="true" />

</body>
</html>
