package com.recipe.ws.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.recipe.ws.data.entities.FavoriteEntity;
import com.recipe.ws.data.entities.UserEntity;

@Repository
public interface FavoriteRepository extends CrudRepository<FavoriteEntity, Long> {

	@Query(value = "SELECT * FROM favorites u WHERE u.users_id=?1 AND u.recipes_id=?2",nativeQuery = true)
	FavoriteEntity findByUserIdAndRecipeId(long userId,long recipeId);
	
	List<FavoriteEntity> findAllByUser(UserEntity user);
}
