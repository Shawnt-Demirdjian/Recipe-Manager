package com.demirdjian.recipemanager.models;

public class Ingredient {
	private String name;
	private int quantity;
	private String unit;

	public Ingredient() {
	}

	public Ingredient(String name, int quantity, String unit) {
		this.name = name;
		this.quantity = quantity;
		this.unit = unit;
	}

	public String toString() {
		StringBuilder returnStr = new StringBuilder();
		returnStr.append("Ingredient Name:\t" + this.name + "\n");
		returnStr.append("Quantity:\t" + this.quantity + "\n");
		returnStr.append("Unit:\t" + this.unit + "\n");

		return returnStr.toString();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
