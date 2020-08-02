package com.recipe.ws.data.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.recipe.ws.data.entities.RatingEntity;
import com.recipe.ws.data.entities.RecipeEntity;
import com.recipe.ws.data.entities.UserEntity;

@Repository
public interface RatingRespository extends CrudRepository<RatingEntity, Long> {

	@Query(value = "SELECT AVG(rating) FROM ratings u WHERE u.recipes_id=?1",nativeQuery = true)
	Double avgRecipeRating(long recipeId);
	
	@Query(value = "SELECT * FROM ratings u WHERE u.users_id=?1 AND u.recipes_id=?2",nativeQuery = true)
	RatingEntity findByUserAndRecipe(long userId,long recipeId);
}
