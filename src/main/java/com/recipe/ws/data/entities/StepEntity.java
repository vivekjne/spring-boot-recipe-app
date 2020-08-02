package com.recipe.ws.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="steps")
public class StepEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false,length = 200)
	private String name;
	
	@Column(nullable = false,columnDefinition = "TEXT")
	private String description;
	
	@Column(nullable = true,columnDefinition = "TEXT")
	private String image;
	
	@Column(length = 3)
	private int stepOrder;
	
	@JsonIgnore
	@ManyToOne()
	@JoinColumn(name = "recipes_id")
	private RecipeEntity recipe;

	public RecipeEntity getRecipe() {
		return recipe;
	}

	public void setRecipe(RecipeEntity recipe) {
		this.recipe = recipe;
	}

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

	public int getStepOrder() {
		return stepOrder;
	}

	public void setStepOrder(int stepOrder) {
		this.stepOrder = stepOrder;
	}

	@Override
	public String toString() {
		return "StepEntity [id=" + id + ", name=" + name + ", description=" + description + ", image=" + image
				+ ", stepOrder=" + stepOrder + "]";
	}
	
	

}
