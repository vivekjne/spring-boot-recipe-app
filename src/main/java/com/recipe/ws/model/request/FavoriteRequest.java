package com.recipe.ws.model.request;

import javax.validation.constraints.NotNull;

public class FavoriteRequest {

	@NotNull(message = "recipes_id cannot be null")
	private long recipes_id;

	public long getRecipes_id() {
		return recipes_id;
	}

	public void setRecipes_id(long recipes_id) {
		this.recipes_id = recipes_id;
	}
	
	
}
