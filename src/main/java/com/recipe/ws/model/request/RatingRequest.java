package com.recipe.ws.model.request;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recipe.ws.data.entities.UserEntity;

public class RatingRequest {

	@NotNull(message = "recipes_id is required")
	private long recipes_id;
	
	@NotNull(message = "rating is required")
	private Double rating;

	public long getRecipes_id() {
		return recipes_id;
	}

	public void setRecipes_id(long recipes_id) {
		this.recipes_id = recipes_id;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}
	
	
}
