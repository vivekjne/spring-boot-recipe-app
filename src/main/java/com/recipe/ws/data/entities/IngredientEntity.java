package com.recipe.ws.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ingredients")
public class IngredientEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	@Column(nullable = false,length = 100)
	private String name;
	
	@Column(nullable = true,columnDefinition = "TEXT")
	private String description;
	
	@Column(nullable=false)
	private int quantity;
	
	@JsonIgnore
	@ManyToOne()
	@JoinColumn(name = "recipes_id")
	private RecipeEntity recipe;
	
	public IngredientEntity() {
		// TODO Auto-generated constructor stub
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


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	


	public RecipeEntity getRecipe() {
		return recipe;
	}


	public void setRecipe(RecipeEntity recipe) {
		this.recipe = recipe;
	}


	@Override
	public String toString() {
		return "IngredientEntity [id=" + id + ", name=" + name + ", description=" + description + ", quantity="
				+ quantity + ", recipe=" + recipe + "]";
	}
	
	

}
