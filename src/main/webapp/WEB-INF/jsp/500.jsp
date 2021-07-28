<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="responseCodesHeader.jsp" flush="true" />

<body>

	<div class="container">

		<h1 class="first-four">5</h1>

		<div class="cog-wheel1">
			<div class="cog1">
				<div class="top"></div>
				<div class="down"></div>
				<div class="left-top"></div>
				<div class="left-down"></div>
				<div class="right-top"></div>
				<div class="right-down"></div>
				<div class="left"></div>
				<div class="right"></div>
			</div>
		</div>

		<div class="cog-wheel2">
			<div class="cog2">
				<div class="top"></div>
				<div class="down"></div>
				<div class="left-top"></div>
				<div class="left-down"></div>
				<div class="right-top"></div>
				<div class="right-down"></div>
				<div class="left"></div>
				<div class="right"></div>
			</div>
		</div>


		<p class="wrong-para">This is awkward.We are having really a bad
			day.Our bad. &#128542;</p>
	</div>

	<!-- Template Javascript -->
	<script src="${basePath}js/error.js"></script>

</body>

</html>