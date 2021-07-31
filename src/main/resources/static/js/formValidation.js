function validateUpload(callerName) {
	alert(callerName);

	const MAX_UPLOAD_SIZE = document.getElementById("maxUploadSize").value;
	var filePath = document.getElementById("fileInput").value;

	// Allowed file type
	var allowedExtensions = /(\.jpg|\.jpeg|\.png)$/i;

	if (filePath) {

		// Validate upload file extension
		if (!allowedExtensions.exec(filePath)) {
			alert('Invalid File type.Supported file types - [ *.jpeg / *.jpg / *.png ]');
			fileInput.value = '';
			event.preventDefault();

		} else {

			// Validate size of upload file (in MB)
			var fileUpload = document.getElementById("fileInput");
			var fileSize = parseFloat(fileUpload.files[0].size / (1024 * 1024)).toFixed(2);

			if (fileSize > MAX_UPLOAD_SIZE) {
				alert("File must not exceed 2 MB !");
				fileUpload.value = '';
				event.preventDefault();
			} else {
				document.getElementById(callerName).action = "/" + callerName;
			}

		}

	} else {
		// No File Selected to upload
		document.getElementById(callerName).action = "/" + callerName;
	}

}

function allowNumeric(evt) {

	// Allow only ASCII character(s) in this range
	var ASCIICode = (evt.which) ? evt.which : evt.keyCode
	if (ASCIICode > 31 && (ASCIICode < 48 || ASCIICode > 57))
		return false;
	return true;

}

function allowAlphanumeric(evt) {

	//Allow only Alphabets Numbers Dash Underscore and Space

	var keyCode = evt.keyCode || evt.which;
	var pattern = /^[a-z\d\-_\s]+$/i;

	//Validating the textBox value against our regex pattern.
	var isValid = pattern.test(String.fromCharCode(keyCode));

	return isValid;

}

function allowAlphabets(evt) {
	//Allow only Alphabets and Spaces

	var keyCode = evt.keyCode || evt.which;
	var pattern = /^[a-zA-Z\s]*$/;
	var isValid = pattern.test(String.fromCharCode(keyCode));

	return isValid;

}

function allowDecimalNumbers(evt) {
	//Allow Numbers and Decimal

	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if (charCode != 46 && charCode > 31
		&& (charCode < 48 || charCode > 57))
		return false;

	return true;

}


