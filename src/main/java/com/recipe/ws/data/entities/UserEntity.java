package com.recipe.ws.data.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class UserEntity {

	public UserEntity() {
		// TODO Auto-generated constructor stub
	}
	
	@JsonIgnore
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable=false)
	private String userId;
	
	@Column(nullable=false,length=50)
	private String firstName;
	
	@Column(nullable=false,length=50)
	private String lastName;
	
	@Column(nullable=false,length=120,unique=true)
	private String email;
	
	@JsonIgnore
	@Column(nullable=false)
	private String encryptedPassword;
	
	@JsonIgnore
	@Column()
	private String emailVerificationToken;
	
	@JsonIgnore
	@Column(nullable=false)
	private Boolean emailVerificationStatus = false;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<RecipeEntity> recipes;

	@JsonIgnore
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<RatingEntity> ratings;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
	private List<FavoriteEntity> favorites;
	
	
	public List<FavoriteEntity> getFavorites() {
		return favorites;
	}

	public void setFavorites(List<FavoriteEntity> favorites) {
		this.favorites = favorites;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getEmailVerificationToken() {
		return emailVerificationToken;
	}

	public void setEmailVerificationToken(String emailVerificationToken) {
		this.emailVerificationToken = emailVerificationToken;
	}

	public Boolean getEmailVerificationStatus() {
		return emailVerificationStatus;
	}

	public void setEmailVerificationStatus(Boolean emailVerificationStatus) {
		this.emailVerificationStatus = emailVerificationStatus;
	}

	public List<RecipeEntity> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<RecipeEntity> recipes) {
		this.recipes = recipes;
	}

	public List<RatingEntity> getRatings() {
		return ratings;
	}

	public void setRatings(List<RatingEntity> ratings) {
		this.ratings = ratings;
	}

	
	
	
	
	
	

}
