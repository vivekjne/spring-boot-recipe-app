package com.recipe.ws.model.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class RecipeCreationRequest {

	@NotNull(message = "Name cannot be empty")
	@Size(min = 2,message = "Recipe name should be atleast 2 characters")
	private String name;
	
	
	@NotNull(message = "Description cannot be empty")
	@Size(min = 5,message = "Recipe name should be atleast  5 characters")
	private String description;
	
	
	private String image;
	


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


	@Override
	public String toString() {
		return "RecipeCreationRequest [name=" + name + ", description=" + description + ", image=" + image + "]";
	}
	
	
	
	
	

}
