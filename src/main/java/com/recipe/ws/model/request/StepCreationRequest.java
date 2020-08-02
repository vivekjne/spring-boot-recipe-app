package com.recipe.ws.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StepCreationRequest {

	@NotNull(message = "Step name is required")
	@Size(min = 2,message = "Step name should not be empty")
	private String name;
	
	@NotNull(message = "Step name is required")
	@Size(min = 2,max=400,message = "Step description should be between 2 and 400 characters")
	private String description;
	
	private String image;
	
	
	public StepCreationRequest() {
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	
	
	

}
