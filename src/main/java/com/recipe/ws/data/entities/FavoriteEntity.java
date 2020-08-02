package com.recipe.ws.data.entities;

import javax.persistence.CascadeType;
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
@Table(name="favorites")
public class FavoriteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_id")
	private UserEntity user;
	
	@ManyToOne()
	@JoinColumn(name = "recipes_id")
	private RecipeEntity recipe;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public RecipeEntity getRecipe() {
		return recipe;
	}

	public void setRecipe(RecipeEntity recipe) {
		this.recipe = recipe;
	}

	@Override
	public String toString() {
		return "FavoriteEntity [id=" + id + ", user=" + user + ", recipe=" + recipe + "]";
	}
	
	
	
	
}
