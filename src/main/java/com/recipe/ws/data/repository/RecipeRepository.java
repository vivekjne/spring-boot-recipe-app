package com.recipe.ws.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.recipe.ws.data.entities.RecipeEntity;
import com.recipe.ws.data.entities.UserEntity;

@Repository
public interface RecipeRepository extends CrudRepository<RecipeEntity, Long>{

	List<RecipeEntity> findAllByUser(UserEntity user);

}
