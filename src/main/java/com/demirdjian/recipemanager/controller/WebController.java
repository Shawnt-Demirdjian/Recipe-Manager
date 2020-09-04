package com.demirdjian.recipemanager.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import com.demirdjian.recipemanager.models.Recipe;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
	@Value("${psql.url}")
	private String url;
	@Value("${psql.user}")
	private String user;
	@Value("${psql.password}")
	private String password;

	private Connection psqlConn;

	/**
	 * Returns the recipe associated with the provided ID.
	 * 
	 * @param id
	 * @return Recipe
	 * @return 404/null, No recipe with provided ID found
	 */
	@GetMapping("/getRecipe/{id}")
	public Recipe getRecipe(@PathVariable(value = "id") String id, HttpServletResponse httpResponse) {
		this.connectPSQL();
		Recipe response = new Recipe();

		String sqlStr = "SELECT * FROM recipes WHERE id = ?";

		try (PreparedStatement pstmt = this.psqlConn.prepareStatement(sqlStr);) {
			pstmt.setInt(1, Integer.parseInt(id));
			try (ResultSet result = pstmt.executeQuery();) {
				result.next();
				response.setTitle(result.getString(1));
				response.setIngredients((String[]) result.getArray(2).getArray());
				response.setDescription(result.getString(3));
				response.setSteps((String[]) result.getArray(4).getArray());
				response.setId(result.getInt(5));

				System.out.println(response.toString());
			}
		} catch (SQLException e) {
			httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response = null;
			e.printStackTrace();
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
	@PostMapping("/createRecipe")
	public void createRecipe(@RequestBody Recipe newRecipe, HttpServletResponse httpResponse) {
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
			} else {
				httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}

			System.out.println(newRecipe.toString());

		} catch (SQLException e) {
			httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
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
	@PatchMapping("/updateRecipe")
	public void updateRecipe(@RequestBody Recipe newRecipe, HttpServletResponse httpResponse) {

		// Fail if no ID was provided
		if (newRecipe.getId() == -1) {
			httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		this.connectPSQL();

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
			System.out.println(pstmt.toString());

			// Run update
			int result = pstmt.executeUpdate();
			if (result >= 0) {
				httpResponse.setStatus(HttpServletResponse.SC_OK);
			} else {
				httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}

		} catch (SQLException | IllegalArgumentException e) {
			httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}

		System.out.println(newRecipe.toString());

		this.safeClose();
	}

	/**
	 * Deletes the recipe associated with the provided ID.
	 * 
	 * @param id
	 * @return 200, Successfully deleted
	 * @return 404, No recipe with provided ID found
	 */
	@DeleteMapping("/deleteRecipe/{id}")
	public void deleteRecipe(@PathVariable(value = "id") String id, HttpServletResponse httpResponse) {
		this.connectPSQL();

		String sqlStr = "DELETE FROM recipes WHERE id = ?";

		try (PreparedStatement pstmt = this.psqlConn.prepareStatement(sqlStr);) {
			pstmt.setInt(1, Integer.parseInt(id));
			// Run delete
			int result = pstmt.executeUpdate();
			if (result > 0) {
				httpResponse.setStatus(HttpServletResponse.SC_OK);
			} else {
				httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}

		} catch (SQLException e) {
			httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
			e.printStackTrace();
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
		} catch (SQLException e) {
			System.out.println(e.getMessage());
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}