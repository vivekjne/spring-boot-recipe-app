package com.recipe.ws.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.recipe.ws.data.entities.IngredientEntity;
import com.recipe.ws.data.entities.RecipeEntity;

@Repository
public interface IngredientRepository extends CrudRepository<IngredientEntity, Long>{

	List<IngredientEntity> findByRecipe(RecipeEntity recipe);
	
}
