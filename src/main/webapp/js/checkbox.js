$(document).ready(function() {

	$("#selectall").on("click", function() {
		$(".case").prop("checked", this.checked);
	});

	$(".case").on("click", function() {
		$("#selectall").prop(
			"checked",
			$(".case:checked").length === $(".case").length
		);
	});

});