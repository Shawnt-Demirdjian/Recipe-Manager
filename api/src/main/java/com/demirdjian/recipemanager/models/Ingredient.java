package com.demirdjian.recipemanager.models;

public class Ingredient {
	private String name;
	private int quantity;
	private String unit;

	/**
	 * Empty Constructor.
	 */
	public Ingredient() {
	}

	/**
	 * Constructor.
	 * 
	 * @param name
	 * @param quantity
	 * @param unit
	 */
	public Ingredient(String name, int quantity, String unit) {
		this.name = name;
		this.quantity = quantity;
		this.unit = unit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * Custom toString for debug printing.
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuilder returnStr = new StringBuilder();
		returnStr.append("Ingredient Name:\t" + this.name + "\n");
		returnStr.append("Quantity:\t" + this.quantity + "\n");
		returnStr.append("Unit:\t" + this.unit + "\n");

		return returnStr.toString();
	}

}
