package com.recipe.ws.model.request;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class IngredientCreationRequest {


	@NotNull(message = "Ingredient name is required")
	@Size(min=2,max=100,message = "Ingredient name should be between 2 and 100 characters")
	private String name;
	
	@NotNull(message = "Ingredient description is required")
	@Size(max=400,message = "Ingredient description should be max 400 characters")
	private String description;
	
	@NotNull(message = "Ingredient quantity is required")
	private int quantity;
	

	
	public IngredientCreationRequest() {
		// TODO Auto-generated constructor stub
	}

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
	
	

}
