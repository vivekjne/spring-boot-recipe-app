package com.recipe.ws.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipe.ws.data.entities.FavoriteEntity;
import com.recipe.ws.data.entities.RecipeEntity;
import com.recipe.ws.data.entities.UserEntity;
import com.recipe.ws.data.repository.FavoriteRepository;
import com.recipe.ws.data.repository.RecipeRepository;
import com.recipe.ws.data.repository.UserRepository;
import com.recipe.ws.model.request.FavoriteRequest;
import com.recipe.ws.model.request.UserCreationRequest;
import com.recipe.ws.model.request.UserLoginRequest;
import com.recipe.ws.security.AuthenticationFacade;
import com.recipe.ws.shared.CrudResponse;

@RestController
@RequestMapping("users")
public class UserController {

	
	@Autowired 
	ModelMapper modelMapper;
	
	@Autowired
	CrudResponse crudResponse;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	FavoriteRepository favoriteRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	AuthenticationFacade authenticationFacade;
	
	public UserController() {
		// TODO Auto-generated constructor stub
	}
	
	@PostMapping
	public ResponseEntity create(@Valid @RequestBody UserCreationRequest userDetails ) {
		
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
		
		userEntity.setUserId(UUID.randomUUID().toString());
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		
		UserEntity storedUser = userRepository.save(userEntity);
		return crudResponse.createResponse(storedUser);
		
	}
	
	@GetMapping(path="/{userId}")
	public ResponseEntity show(@PathVariable String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if(userEntity==null) {
			return  ResponseEntity.notFound().build();
		}
		
		return crudResponse.showAndUpdateResponse(userEntity, false);
		
	}
	
	@PostMapping(path = "/login")
	public ResponseEntity login(@Valid @RequestBody UserLoginRequest userDetails) {
		throw new IllegalStateException("This method should not be called");
	}

	
	@GetMapping(path = "/{userId}/recipes")
	public ResponseEntity getUserRecipes(@PathVariable String userId) {
		
	
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity==null) {
			return ResponseEntity.noContent().build();
		}
		System.out.println(userEntity.toString());
		List<RecipeEntity> recipes = recipeRepository.findAllByUser(userEntity);
		
		return crudResponse.indexResponse(recipes, recipes.size());
		
	}
	
	@GetMapping(path="/{userId}/favorites")
	public ResponseEntity getUserFavorites(@PathVariable String userId) {
		
		UserEntity userEntity = userRepository.findByUserId(userId);
		Map<String, String> errorMap = new HashMap<>();
		if(userEntity==null) {
			errorMap.put("message", "user not found");
			return new ResponseEntity(errorMap,HttpStatus.NOT_FOUND);
		}
		
		List<FavoriteEntity> favouriteRecipes = userEntity.getFavorites();
		
		return new CrudResponse().indexResponse(favouriteRecipes, favouriteRecipes.size());
	}
	
	@PostMapping(path="/{userId}/favorites")
	public ResponseEntity createFavorites(@PathVariable String userId,@Valid @RequestBody FavoriteRequest favoriteDetails) {
		
		UserEntity userEntity = userRepository.findByUserId(userId);
		Authentication authentication = authenticationFacade.getAuthentication();
		Map<String, String> errorMap = new HashMap<>();
		if(userEntity==null) {
			errorMap.put("message", "user not found");
			return new ResponseEntity(errorMap,HttpStatus.NOT_FOUND);
		}
		
		System.out.println(authentication.getPrincipal().toString()+" - " + userEntity.getEmail().toString());
		if(!authentication.getPrincipal().equals(userEntity.getEmail())) {
			errorMap.put("message", "Not the same user");
			return new ResponseEntity(errorMap,HttpStatus.FORBIDDEN);
			
		}
		
		FavoriteEntity favouriteExists = favoriteRepository.findByUserIdAndRecipeId(userEntity.getId(), favoriteDetails.getRecipes_id());
		if(favouriteExists!=null) {
			errorMap.put("message", "Already favorited");
			return new ResponseEntity(errorMap,HttpStatus.ALREADY_REPORTED);
		}
		FavoriteEntity favourite = new FavoriteEntity();
		
		favourite.setRecipe(recipeRepository.findById(favoriteDetails.getRecipes_id()).get());
		favourite.setUser(userEntity);
		
		FavoriteEntity createdFavorite = favoriteRepository.save(favourite);
		
		
		return new CrudResponse().createResponse(createdFavorite);
	}
	
	@GetMapping(path = "/{userId}/favorites/recipe/{recipeId}")
	public ResponseEntity checkRecipeFavourited(@PathVariable String userId,@PathVariable long recipeId) {
		
		UserEntity userEntity = userRepository.findByUserId(userId);
		Authentication authentication = authenticationFacade.getAuthentication();
		Map<String, String> errorMap = new HashMap<>();
		
		System.out.println(authentication.getPrincipal().toString()+" - " + userEntity.getEmail().toString());
		if(!authentication.getPrincipal().equals(userEntity.getEmail())) {
			errorMap.put("message", "Not the same user");
			return new ResponseEntity(errorMap,HttpStatus.FORBIDDEN);
			
		}
		
		FavoriteEntity favouritedRecipe = favoriteRepository.findByUserIdAndRecipeId(userEntity.getId(),recipeId);
	
		Map favouriteResponse = new HashMap<>();
		favouriteResponse.put("favorited_id", favouritedRecipe!=null?favouritedRecipe.getId():null);
		return crudResponse.showAndUpdateResponse(favouriteResponse, favouritedRecipe==null);
	}
	
	@DeleteMapping(path = "/{userId}/favourites/{favouriteId}")
	public ResponseEntity deleteUserFavorite(String userId,long favouriteId ) {
			UserEntity userEntity = userRepository.findByUserId(userId);
			Authentication authentication = authenticationFacade.getAuthentication();
			Map<String, String> errorMap = new HashMap<>();
			
			System.out.println(authentication.getPrincipal().toString()+" - " + userEntity.getEmail().toString());
			if(!authentication.getPrincipal().equals(userEntity.getEmail())) {
				errorMap.put("message", "Not the same user");
				return new ResponseEntity(errorMap,HttpStatus.FORBIDDEN);
				
			}
			
			Optional<FavoriteEntity> favouritedRecipe = favoriteRepository.findById(favouriteId);
			
			if(favouritedRecipe.isEmpty()) {
				return ResponseEntity.notFound().build();
			}
			
			if(!favouritedRecipe.get().getUser().getEmail().equals(userEntity.getEmail())) {
				errorMap.put("message", "Not user favorited");
				return new ResponseEntity(errorMap,HttpStatus.FORBIDDEN);
			}
			
			favoriteRepository.delete(favouritedRecipe.get());
			
			return new CrudResponse().deleteResponse();
			
		}
		
	
}
