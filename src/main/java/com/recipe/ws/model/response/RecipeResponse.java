package com.recipe.ws.model.response;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recipe.ws.data.entities.IngredientEntity;
import com.recipe.ws.data.entities.StepEntity;
import com.recipe.ws.data.entities.UserEntity;

public class RecipeResponse {

	private long id;
	

	private String name;
	
	
	private String slug;


	private String description;
	
	private String image;
	
	private List<IngredientEntity> ingredients;

	private List<StepEntity> steps;
	
	private UserEntity user;
	
	private Double RatingsAvg;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
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

	public List<IngredientEntity> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<IngredientEntity> ingredients) {
		this.ingredients = ingredients;
	}

	public List<StepEntity> getSteps() {
		return steps;
	}

	public void setSteps(List<StepEntity> steps) {
		this.steps = steps;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Double getRatingsAvg() {
		return RatingsAvg;
	}

	public void setRatingsAvg(Double ratingsAvg) {
		RatingsAvg = ratingsAvg;
	}
	
	
}
