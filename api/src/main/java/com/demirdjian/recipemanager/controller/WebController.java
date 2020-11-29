package com.demirdjian.recipemanager.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.demirdjian.recipemanager.models.CreateRecipeBody;
import com.demirdjian.recipemanager.models.Recipe;
import com.demirdjian.recipemanager.models.UpdateRecipeBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api")
public class WebController {
	@Value("${psql.url}")
	private String url;
	@Value("${psql.user}")
	private String user;
	@Value("${psql.password}")
	private String password;

	private Connection psqlConn;

	private static final Logger rmLogger = LoggerFactory.getLogger(WebController.class);

	/**
	 * Returns the recipe associated with the provided ID.
	 * 
	 * @param id
	 * @return Recipe
	 * @return 404/null, No recipe with provided ID found
	 */
	@GetMapping("/recipes/{id}")
	public Recipe getRecipe(@PathVariable("id") @NotNull @Min(1) int id, HttpServletResponse httpResponse) {
		this.connectPSQL();
		Recipe response = new Recipe();

		String sqlStr = "SELECT * FROM recipes WHERE id = ?";

		try (PreparedStatement pstmt = this.psqlConn.prepareStatement(sqlStr);) {
			pstmt.setInt(1, id);
			try (ResultSet result = pstmt.executeQuery();) {
				if (result.next()) {
					response.setTitle(result.getString(1));
					response.setIngredients((String[]) result.getArray(2).getArray());
					response.setDescription(result.getString(3));
					response.setSteps((String[]) result.getArray(4).getArray());
					response.setId(result.getInt(5));

					String responseStr = response.toString();
					rmLogger.debug("Recipe Found: \n{}", responseStr);
				} else {
					httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
					response = null;
					rmLogger.debug("No Recipe Found.");
				}
			}
		} catch (SQLException e) {
			httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response = null;
			rmLogger.error("SQLException at /getRecipe", e);
		}

		this.safeClose();
		return response;
	}

	/**
	 * Returns the recipes that have the query string in their title or description
	 * 
	 * @param queryString
	 * @return List<Recipe>
	 * @return 404/null, No recipes found
	 */
	@GetMapping("/recipes")
	public List<Recipe> getRecipes(@RequestParam("queryString") @NotBlank String queryString,
			HttpServletResponse httpResponse) {
		this.connectPSQL();
		ArrayList<Recipe> response = new ArrayList<>();

		// TODO Sanitize queryString
		queryString = queryString.replaceAll("\\s+", " & ");

		String sqlStr = "SELECT * FROM recipes WHERE document_vectors @@ to_tsquery(?)";

		try (PreparedStatement pstmt = this.psqlConn.prepareStatement(sqlStr);) {
			pstmt.setString(1, queryString);
			try (ResultSet result = pstmt.executeQuery();) {
				while (result.next()) {
					Recipe newRecipe = new Recipe();
					newRecipe.setTitle(result.getString(1));
					newRecipe.setIngredients((String[]) result.getArray(2).getArray());
					newRecipe.setDescription(result.getString(3));
					newRecipe.setSteps((String[]) result.getArray(4).getArray());
					newRecipe.setId(result.getInt(5));

					response.add(newRecipe);
				}

				if (!response.isEmpty()) {
					String responseStr = response.toString();
					rmLogger.debug("Recipes Found: \n{}", responseStr);
				} else {
					rmLogger.debug("No Recipes Found.");
				}
			}
		} catch (SQLException e) {
			httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response = null;
			rmLogger.error("SQLException at /getRecipe", e);
		}

		this.safeClose();
		return response;
	}

	/**
	 * Creates a new recipe with the provided values.
	 * 
	 * @param newRecipe
	 * @return 201, Created Successfully
	 * @return 400, Missing required values, etc.
	 */
	@PostMapping("/recipes")
	public void createRecipe(@Valid @RequestBody CreateRecipeBody newRecipe, HttpServletResponse httpResponse) {
		this.connectPSQL();

		String sqlStr = "INSERT INTO recipes(title, ingredients, description, steps) VALUES(?,?,?,?)";

		try (PreparedStatement pstmt = this.psqlConn.prepareStatement(sqlStr);) {
			pstmt.setString(1, newRecipe.getTitle());
			pstmt.setArray(2, this.psqlConn.createArrayOf("text", newRecipe.getIngredients()));
			pstmt.setString(3, newRecipe.getDescription());
			pstmt.setArray(4, this.psqlConn.createArrayOf("text", newRecipe.getSteps()));
			int result = pstmt.executeUpdate();
			if (result >= 0) {
				httpResponse.setStatus(HttpServletResponse.SC_CREATED);
				String newRecipeStr = newRecipe.toString();
				rmLogger.debug("Recipe Created: \n{}", newRecipeStr);
			} else {
				httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}

		} catch (SQLException e) {
			httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			rmLogger.error("SQLException at /createRecipe", e);
		}

		this.safeClose();
	}

	/**
	 * Updates the recipe of the provided ID with the provided values
	 * 
	 * @param newRecipe
	 * @return 200, Successfully updated
	 * @return 400, Missing ID or values
	 */
	@PatchMapping("/recipes/{id}")
	public void updateRecipe(@PathVariable("id") @NotNull @Min(1) String id,
			@Valid @RequestBody UpdateRecipeBody newRecipe, HttpServletResponse httpResponse) {
		this.connectPSQL();

		// set id from URL param
		newRecipe.setId(Integer.parseInt(id));

		StringBuilder sqlStr = new StringBuilder("UPDATE recipes SET ");

		ArrayList<String> updatedColumns = new ArrayList<>();
		if (!newRecipe.getTitle().isEmpty()) {
			updatedColumns.add("title");
		}
		if (newRecipe.getIngredients().length != 0) {
			updatedColumns.add("ingredients");
		}
		if (!newRecipe.getDescription().isEmpty()) {
			updatedColumns.add("description");
		}
		if (newRecipe.getSteps().length != 0) {
			updatedColumns.add("steps");
		}

		// Fail if no update is to be made
		if (updatedColumns.isEmpty()) {
			httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		// add comma to every statement except last
		for (int i = 0; i < updatedColumns.size(); i++) {
			if (i != updatedColumns.size() - 1) {
				sqlStr.append(updatedColumns.get(i));
				sqlStr.append("  = ? ,");
			} else {
				sqlStr.append(updatedColumns.get(i));
				sqlStr.append("  = ? ");
			}
		}
		// Add WHERE clause
		sqlStr.append(" WHERE id = ? ;");

		try (PreparedStatement pstmt = this.psqlConn.prepareStatement(sqlStr.toString());) {
			// Set all parameters
			int i;
			for (i = 0; i < updatedColumns.size(); i++) {
				switch (updatedColumns.get(i)) {
					case "title":
						pstmt.setString(i + 1, newRecipe.getTitle());
						break;

					case "ingredients":
						pstmt.setArray(i + 1, this.psqlConn.createArrayOf("text", newRecipe.getIngredients()));
						break;

					case "description":
						pstmt.setString(i + 1, newRecipe.getDescription());
						break;

					case "steps":
						pstmt.setArray(i + 1, this.psqlConn.createArrayOf("text", newRecipe.getSteps()));
						break;
					default:
						throw new IllegalArgumentException("Update Recipe: Unsupported Column");
				}
			}
			pstmt.setInt(i + 1, newRecipe.getId());

			// Run update
			int result = pstmt.executeUpdate();
			if (result > 0) {
				httpResponse.setStatus(HttpServletResponse.SC_OK);
				String newRecipeStr = newRecipe.toString();
				rmLogger.debug("Recipe Updated: \n{}", newRecipeStr);
			} else {
				httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}

		} catch (SQLException | IllegalArgumentException e) {
			httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			rmLogger.error(e.getMessage(), e);
		}

		this.safeClose();
	}

	/**
	 * Deletes the recipe associated with the provided ID.
	 * 
	 * @param id
	 * @return 200, Successfully deleted
	 * @return 404, No recipe with provided ID found
	 */
	@DeleteMapping("/recipes/{id}")
	public void deleteRecipe(@PathVariable("id") @NotNull @Min(1) String id, HttpServletResponse httpResponse) {
		this.connectPSQL();

		String sqlStr = "DELETE FROM recipes WHERE id = ?";

		try (PreparedStatement pstmt = this.psqlConn.prepareStatement(sqlStr);) {
			pstmt.setInt(1, Integer.parseInt(id));
			// Run delete
			int result = pstmt.executeUpdate();
			if (result > 0) {
				httpResponse.setStatus(HttpServletResponse.SC_OK);
				rmLogger.debug("Recipe Deleted with ID: {}", id);
			} else {
				httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
			}

		} catch (SQLException e) {
			httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			rmLogger.error("SQLException at /deleteRecipe", e);
		}

		this.safeClose();
	}

	/**
	 * Connects to the PostgreSQL Database using class variables
	 * 
	 * @return boolean
	 */
	public boolean connectPSQL() {
		this.psqlConn = null;
		try {
			this.psqlConn = DriverManager.getConnection(url, user, password);
			rmLogger.debug("Connected to pSQL");
		} catch (SQLException e) {
			rmLogger.info(e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Attempts to safely close a PSQL connection
	 */
	public void safeClose() {
		try {
			this.psqlConn.close();
			rmLogger.debug("Disconnected from pSQL");
		} catch (SQLException e) {
			rmLogger.error("SQLException at safeClose", e);
		}
	}

}