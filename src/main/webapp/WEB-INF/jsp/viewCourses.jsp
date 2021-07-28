<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<!-- Header Include -->
<jsp:include page="header.jsp" flush="true" />

<script type="text/javascript">
	function deleteRecord() {
		var courseIds = [];

		$.each($("input[name='courseId']:checked"), function() {
			courseIds.push($(this).val());
		});

		if (Array.isArray(courseIds) && !courseIds.length) {
			alert("Please select record(s) to be deleted ");
		} else {
			document.getElementById("selectedIds").value = courseIds;
			document.getElementById("viewCourseForm").action = "/deleteCourses";
		}

	}
</script>

<script type="text/javascript">
	function show_hide_table() {

		var hasCourses = document.getElementById("hasCourses").value;

		if (hasCourses === 'false') {

			document.getElementById("availableCourses").style.display = "none";
			document.getElementById("emptyCourses").style.display = "block";

		} else {
			document.getElementById("availableCourses").style.display = "block";
			document.getElementById("emptyCourses").style.display = "none";
		}

	}
</script>

<body>

	<form id="viewCourseForm" method="post" action="#">

		<!-- Nav Bar Include -->
		<jsp:include page="navigationBar.jsp" flush="true" />


		<div class="container mt-5">
			<div class="row tm-content-row">
				<div class="col-sm-12 col-md-12 col-lg-12 col-xl-12 tm-block-col">
					<div class="tm-bg-primary-dark tm-block tm-block-products">

						<input type="hidden" id="hasCourses" value="${hasCourses}" />

						<div class="tm-product-table-container" id="availableCourses"
							style="display: block;">
							<table class="table table-hover tm-table-small tm-product-table">

								<thead>
									<tr>
										<th scope="col">&nbsp;</th>
										<th scope="col">COURSE</th>
										<th scope="col">TRAINER</th>
										<th scope="col">SEATS</th>
										<th scope="col">MONTHS</th>
										<th scope="col">COURSE FEE (&#x20B9;)</th>
										<th scope="col">START DATE</th>
										<th scope="col">&nbsp;</th>
									</tr>
								</thead>

								<tbody>
									<!-- loop starts <tr> to show products -->
									<tr>
										<th scope="row"><input type="checkbox" value="${dummyId}"
											name="courseId" /></th>
										<td class="tm-product-name">Java 11</td>
										<td class="tm-product-name">Kunal</td>
										<td class="tm-product-name">120</td>
										<td class="tm-product-name">5</td>
										<td class="tm-product-name">10000</td>
										<td class="tm-product-name">28 Nov 2021</td>
										<td><a href="/editCourse" class="tm-product-edit-link"
											title="Edit course details"> <i
												class="fas fa-edit tm-product-edit-icon"></i>
										</a></td>
									</tr>

									<tr>
										<th scope="row"><input type="checkbox" value="26"
											name="courseId" /></th>
										<td class="tm-product-name">Java 12</td>
										<td class="tm-product-name">Kumar</td>
										<td class="tm-product-name">10</td>
										<td class="tm-product-name">10</td>
										<td class="tm-product-name">40000</td>
										<td class="tm-product-name">11 Nov 2021</td>
										<td><a href="/editCourse" class="tm-product-edit-link"
											title="Edit course details"> <i
												class="fas fa-edit tm-product-edit-icon"></i>
										</a></td>
									</tr>


									<!-- loop ends <tr> to show products -->

								</tbody>

							</table>
						</div>

						<div class="tm-product-table-container" id="emptyCourses"
							style="display: none;">

							<table class="table table-hover tm-table-small tm-product-table">

								<thead>
									<tr>
										<th scope="col">&nbsp;</th>
										<th scope="col">COURSE</th>
										<th scope="col">TRAINER</th>
										<th scope="col">SEATS</th>
										<th scope="col">MONTHS</th>
										<th scope="col">PRICE</th>
										<th scope="col">START DATE</th>
										<th scope="col">&nbsp;</th>
									</tr>
								</thead>

							</table>
							<br /> <br />
							<p class="text-center text-white mb-0 px-4">No Courses to
								display. Add new Course(s).</p>

						</div>

						<!-- table container -->
						<a href="/addCourse"
							class="btn btn-primary btn-block text-uppercase mb-3">Add new
							course</a> <input id="selectedIds" name="selectedIds" type="hidden"
							value="">
						<button class="btn btn-primary btn-block text-uppercase"
							onclick="deleteRecord();">Delete selected course</button>
					</div>
				</div>

			</div>
		</div>

	</form>
	<!-- Footer Include -->
	<jsp:include page="footer.jsp" flush="true" />

	<!-- JavaScript Libraries -->
	<jsp:include page="jsLibraries.jsp" flush="true" />

<script>
	//call after page loaded
	window.onload = show_hide_table;
</script>

</body>
</html>