package com.recipe.ws.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipe.ws.data.entities.RatingEntity;
import com.recipe.ws.data.entities.RecipeEntity;
import com.recipe.ws.data.entities.UserEntity;
import com.recipe.ws.data.repository.RatingRespository;
import com.recipe.ws.data.repository.RecipeRepository;
import com.recipe.ws.data.repository.UserRepository;
import com.recipe.ws.model.request.RatingRequest;
import com.recipe.ws.security.AuthenticationFacade;
import com.recipe.ws.shared.CrudResponse;

@RestController
@RequestMapping("ratings")
public class RatingController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	RatingRespository ratingRespository;
	
	@Autowired
	AuthenticationFacade authenticationFacade;
	
	@PostMapping
	public ResponseEntity create(@Valid @RequestBody RatingRequest ratingDetails) {
		 Authentication authentication = authenticationFacade.getAuthentication();
		UserEntity user =  userRepository.findByEmail((String)authentication.getPrincipal());
		
		Map errorMap = new HashMap<>();
		if(user==null) {
			errorMap.put("message", "User not found");
			return new ResponseEntity(errorMap,HttpStatus.NOT_FOUND);
		}
		
		Optional<RecipeEntity> recipeEntity =  recipeRepository.findById(ratingDetails.getRecipes_id());
		
		if(recipeEntity.isEmpty()) {
			errorMap.put("message", "Recipe not found");
			return new ResponseEntity(errorMap,HttpStatus.NOT_FOUND);
		}
		
		
		RatingEntity rating = new RatingEntity();
		
		RatingEntity ratingExists = ratingRespository.findByUserAndRecipe(user.getId(), recipeEntity.get().getId());
		if(ratingExists!=null) {
			rating = ratingExists;
		}
		rating.setRating(ratingDetails.getRating());
		rating.setUser(user);
		rating.setRecipe(recipeEntity.get());
		
		RatingEntity storedRating = ratingRespository.save(rating);
		
		return new CrudResponse().createResponse(storedRating);
		
	}
	
	
	@GetMapping(path = "/recipe/{recipeId}/user")
	public ResponseEntity showRecipeRatings(@PathVariable long recipeId) {
		 Authentication authentication = authenticationFacade.getAuthentication();
			UserEntity user =  userRepository.findByEmail((String)authentication.getPrincipal());
			
			Map errorMap = new HashMap<>();
			if(user==null) {
				errorMap.put("message", "User not found");
				return new ResponseEntity(errorMap,HttpStatus.NOT_FOUND);
			}
			
		
			
			RatingEntity rating = ratingRespository.findByUserAndRecipe(user.getId(), recipeId);
			
			
			return new CrudResponse().showAndUpdateResponse(rating,false);
		
		
	}

}
