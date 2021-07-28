<!-- JavaScript Libraries -->

<!-- https://jquery.com/download/ -->
<script src="${basePath}js/jquery-3.3.1.min.js"></script>

<!-- https://getbootstrap.com/ -->
<script src="${basePath}js/bootstrap.min.js"></script>

<!-- https://jqueryui.com/download/ -->
<script src="jquery-ui-datepicker/jquery-ui.min.js"></script>


<script>
	$(function() {
		$(".tm-product-name").on("click", function() {
			window.location.href = "edit-product.html";
		});
	});
</script>

<script>
      $(function() {
        $("#startDate").datepicker();
      });
</script>



<!-- Template Javascript -->
<script src="${basePath}js/main.js"></script>