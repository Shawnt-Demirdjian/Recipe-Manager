$(document).ready(() => {

	// Submit request to create a recipe
	$("#createRecipeForm").on("submit", function (event) {
		let steps = $("#createRecipeStepsInput").val().split('\n');
		let ingredients = $("#createRecipeIngredientsInput").val().split('\n');

		$.ajax({
			url: $(this).attr("action"),
			type: $(this).attr("method"),
			dataType: "text",
			processData: false,
			contentType: 'application/json',
			data: JSON.stringify({
				title: $("#createRecipeTitleInput").val(),
				description: $("#createRecipeDescriptionInput").val(),
				steps: steps,
				ingredients: ingredients
			})
		}).done((data, textStatus, jqXHR) => {
			console.log("done");
		}).fail((jqXHR, textStatus, errorThrown) => {
			console.log("fail");
		});
	});

	// Submit request to update a recipe
	$("#updateRecipeForm").on("submit", function (event) {
		// Only include the forms that are not empty
		let data = {};
		let title = $("#updateRecipeTitleInput").val();
		if (title !== "") data.title = title;
		let description = $("#updateRecipeDescriptionInput").val();
		if (description !== "") data.description = description;
		let steps = $("#updateRecipeStepsInput").val();
		if (steps !== "") data.steps = steps.split('\n');
		let ingredients = $("#updateRecipeIngredientsInput").val();
		if (ingredients !== "") data.ingredients = ingredients.split('\n');

		$.ajax({
			url: $(this).attr("action") + "/" + $("#updateRecipeIdInput").val(),
			type: $(this).attr("method"),
			dataType: "text",
			processData: false,
			contentType: 'application/json',
			data: JSON.stringify(data)
		}).done((data, textStatus, jqXHR) => {
			console.log("done");
		}).fail((jqXHR, textStatus, errorThrown) => {
			console.log("fail");
		});
	});

	// Submit request to get a recipe
	$("#getRecipeForm").on("submit", function (event) {
		$.ajax({
			url: $(this).attr("action") + "/" + $("#getRecipeIdInput").val(),
			type: $(this).attr("method"),
			dataType: "json",
		}).done((data, textStatus, jqXHR) => {
			console.log("done");
		}).fail((jqXHR, textStatus, errorThrown) => {
			console.log("fail");
		});
	});

	// Submit request to delete a recipe
	$("#deleteRecipeForm").on("submit", function (event) {
		$.ajax({
			url: $(this).attr("action") + "/" + $("#deleteRecipeIdInput").val(),
			type: $(this).attr("method"),
			dataType: "text",
		}).done((data, textStatus, jqXHR) => {
			console.log("done");
		}).fail((jqXHR, textStatus, errorThrown) => {
			console.log("fail");
		});
	});

	// Submit request to search for recipes
	$("#searchRecipeForm").on("submit", function (event) {
		$.ajax({
			url: $(this).attr("action") + "?queryString=" + $("#searchRecipeTitleInput").val(),
			type: $(this).attr("method"),
			dataType: "json",
		}).done((data, textStatus, jqXHR) => {
			console.log("done");
		}).fail((jqXHR, textStatus, errorThrown) => {
			console.log("fail");
		});
	});

});