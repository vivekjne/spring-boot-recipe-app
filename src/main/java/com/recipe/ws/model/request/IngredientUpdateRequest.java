package com.recipe.ws.model.request;

import javax.validation.constraints.Size;

public class IngredientUpdateRequest {

	@Size(min=2,max=100,message = "Ingredient name should be between 2 and 100 characters")
	private String name;
	
	@Size(max=400,message = "Ingredient description should be max 400 characters")
	private String description;
	
	private int quantity;
	
	
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public int getQuantity() {
		return quantity;
	}



	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}



	public IngredientUpdateRequest() {
		// TODO Auto-generated constructor stub
	}

}
