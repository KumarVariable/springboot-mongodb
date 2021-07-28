
$(document).ready(function(){
	$('[data-toggle="tooltip"]').tooltip();
	
	var actions = "";
		
	actions += '<a id="add" class="add" title="Add" data-toggle="tooltip">';
	actions += '<i class="material-icons">&#xE03B;</i></a>';
	
	actions += '<a id="edit" class="edit" title="Edit" data-toggle="tooltip">';
	actions += '<i class="material-icons">&#xE254;</i></a>';
	
	actions += '<a id="delete" class="delete" title="Delete" data-toggle="tooltip">';
	actions += '<i class="material-icons">&#xE872;</i></a>';
	
	
	// Append table with add row form on add new button click
    $(".add-new").click(function(){
		$(this).attr("disabled", "disabled");
		var index = $("table tbody tr:last-child").index();
        var row = '<tr>' +
			'<td><input type="text" class="form-control" id="employeeId" name="employeeId"  ></td>' +
            '<td><input type="text" class="form-control" id="employeeName" name="employeeName" ></td>' +
            '<td><input type="text" class="form-control" id="employeeDepartment" name="employeeDepartment" ></td>' +
            '<td><input type="text" class="form-control" id="employeeEmail" name="employeeEmail" ></td>' +
			'<td>' + actions + '</td>' +
        '</tr>';
    	$("table").append(row);		
		$("table tbody tr").eq(index + 1).find(".add, .edit").toggle();
        $('[data-toggle="tooltip"]').tooltip();
    });

	// Add row on add button click
	$(document).on("click", ".add", function(){
		var empty = false;
		var input = $(this).parents("tr").find('input[type="text"]');
        input.each(function(){
			if(!$(this).val()){
				$(this).addClass("error");
				empty = true;
			} else{
                $(this).removeClass("error");
            }
		});
		$(this).parents("tr").find(".error").first().focus();
		if(!empty){
			input.each(function(){
				$(this).parent("td").html($(this).val());
			});			
			$(this).parents("tr").find(".add, .edit").toggle();
			$(".add-new").removeAttr("disabled");
			
			submitForm('add');
			
			
		}		
    });

	// Edit row on edit button click
	/*$(document).on("click", ".edit", function(){		
        $(this).parents("tr").find("td:not(:last-child)").each(function(){
			$(this).html('<input type="text" class="form-control" value="' + $(this).text() + '">');
		});		
		$(this).parents("tr").find(".add, .edit").toggle();
		$(".add-new").attr("disabled", "disabled");
    });*/
	// Delete row on delete button click
	/*$(document).on("click", ".delete", function(){
        $(this).parents("tr").remove();
		$(".add-new").removeAttr("disabled");
    });*/
});
