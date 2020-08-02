package com.recipe.ws.data.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "recipes")
public class RecipeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	@Column(nullable = false,length = 200)
	private String name;
	
	@Column(nullable = false,length = 254)
	private String slug;

	@Column(nullable = false,columnDefinition = "TEXT")
	private String description;
	
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

	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}

	public LocalDateTime getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(LocalDateTime updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	@Column(nullable = true,columnDefinition = "TEXT")
	private String image;
	
	@CreationTimestamp
	@JsonIgnore
	private LocalDateTime createDateTime;
	 
	@UpdateTimestamp
	@JsonIgnore
	private LocalDateTime updateDateTime;
	
	@OneToMany(mappedBy = "recipe",cascade = CascadeType.ALL)
	private List<IngredientEntity> ingredients;

	
	@OneToMany(mappedBy = "recipe",cascade = CascadeType.ALL)
	private List<StepEntity> steps;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "users_id")
	private UserEntity user;
	
	@JsonIgnore
	@OneToMany(mappedBy = "recipe",cascade = CascadeType.ALL)
	private List<RatingEntity> ratings;
	
	
	
	public List<IngredientEntity> getIngredients() {
		return ingredients;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public void setIngredients(List<IngredientEntity> ingredients) {
		this.ingredients = ingredients;
	}
	
	

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}
	
	

	public List<StepEntity> getSteps() {
		return steps;
	}

	public void setSteps(List<StepEntity> steps) {
		this.steps = steps;
	}
	
	

	public List<RatingEntity> getRatings() {
		return ratings;
	}

	public void setRatings(List<RatingEntity> ratings) {
		this.ratings = ratings;
	}
	



	@Override
	public String toString() {
		return "RecipeEntity [id=" + id + ", name=" + name + ", description=" + description + ", image=" + image
				+ ", createDateTime=" + createDateTime + ", updateDateTime=" + updateDateTime + "]";
	}
	
	

}
