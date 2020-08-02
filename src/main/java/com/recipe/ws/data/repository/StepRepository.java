package com.recipe.ws.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.recipe.ws.data.entities.IngredientEntity;
import com.recipe.ws.data.entities.RecipeEntity;
import com.recipe.ws.data.entities.StepEntity;

@Repository
public interface StepRepository extends CrudRepository<StepEntity, Long> {

	@Query(value = "SELECT Max(step_order) FROM steps u WHERE u.recipes_id=?1",nativeQuery = true)
	Integer maxStepOrder(long recipeId);
	
	List<StepEntity> findByRecipe(RecipeEntity recipe);
}
