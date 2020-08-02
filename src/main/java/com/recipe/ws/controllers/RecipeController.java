package com.recipe.ws.controllers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.print.attribute.standard.Destination;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.slugify.Slugify;
import com.recipe.ws.data.entities.IngredientEntity;
import com.recipe.ws.data.entities.RecipeEntity;
import com.recipe.ws.data.entities.StepEntity;
import com.recipe.ws.data.entities.UserEntity;
import com.recipe.ws.data.repository.IngredientRepository;
import com.recipe.ws.data.repository.RatingRespository;
import com.recipe.ws.data.repository.RecipeRepository;
import com.recipe.ws.data.repository.StepRepository;
import com.recipe.ws.data.repository.UserRepository;
import com.recipe.ws.model.request.IngredientCreationRequest;
import com.recipe.ws.model.request.IngredientUpdateRequest;
import com.recipe.ws.model.request.RecipeCreationRequest;
import com.recipe.ws.model.request.StepCreationRequest;
import com.recipe.ws.model.request.StepUpdateRequest;
import com.recipe.ws.model.response.RecipeResponse;
import com.recipe.ws.security.AuthenticationFacade;
import com.recipe.ws.security.IAuthenticationFacade;
import com.recipe.ws.shared.CrudResponse;

@RestController
@RequestMapping("recipes")
public class RecipeController {

	@Autowired
	private RecipeRepository recipeRepository;
	
	@Autowired
	private IngredientRepository ingredientRepository;
	
	@Autowired
	private StepRepository stepRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RatingRespository ratingRespository;
	
	@Autowired
	CrudResponse crudResponse;
	
	@Autowired 
	ModelMapper modelMapper;
	
	@Autowired
	Slugify slugify;
	
	@Autowired
	private AuthenticationFacade authenticationFacade;
	
	@GetMapping
	public ResponseEntity index() {
		
		
		
		List<RecipeEntity> recipes = (List<RecipeEntity>) recipeRepository.findAll();
		System.out.println("hello");
		List recipeResponse = new ArrayList<>();
		 recipes.stream().map(r->{
			 RecipeResponse mapData =modelMapper.map(r, RecipeResponse.class);
			 Double ratingCount = ratingRespository.avgRecipeRating(r.getId());
			 mapData.setRatingsAvg(ratingCount);
			return mapData;
		}).forEach(recipeResponse::add);
		return crudResponse.indexResponse(recipeResponse, recipes.size());
	}
	
	@GetMapping(path ="/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity show(@PathVariable long id){
		Optional<RecipeEntity> recipe = recipeRepository.findById(id);
		
		return crudResponse.showAndUpdateResponse(recipe,recipe.isEmpty());
	}
	
	
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity create(@Valid @RequestBody RecipeCreationRequest recipeCreationRequest) {
		  Authentication authentication = authenticationFacade.getAuthentication();
	     
		RecipeEntity newRecipe = modelMapper.map(recipeCreationRequest, RecipeEntity.class);
		newRecipe.setUser(userRepository.findByEmail((String)authentication.getPrincipal()));
		newRecipe.setSlug(slugify.slugify(newRecipe.getName()));
		
		RecipeEntity storedRecipe = recipeRepository.save(newRecipe);
		return crudResponse.createResponse(storedRecipe);
	}
	
	@PutMapping(path="/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity update(@PathVariable String id, @RequestBody RecipeCreationRequest recipeCreationRequest) {
		return new ResponseEntity(recipeCreationRequest,HttpStatus.OK);
	}
	
	@DeleteMapping(path="/{id}")
	public ResponseEntity destroy( @PathVariable String id) {
		return crudResponse.deleteResponse();
	}
	
	@GetMapping(path="/{id}/ingredients",produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity ingredientIndex(@PathVariable long id) {
		Optional<RecipeEntity> recipe = recipeRepository.findById(id);
		if(recipe.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			
			List<IngredientEntity> ingredients = ingredientRepository.findByRecipe(recipe.get());
			return crudResponse.indexResponse(ingredients, ingredients.size());
		}
	}
	
	@PostMapping(path = "/{id}/ingredients",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity createIngredients(@PathVariable long id,@Valid @RequestBody IngredientCreationRequest ingredientDetails) {
		
		Optional<RecipeEntity> recipe = recipeRepository.findById(id);
		if(recipe.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			IngredientEntity newIngredient = modelMapper.map(ingredientDetails, IngredientEntity.class);
			newIngredient.setRecipe(recipe.get());
			IngredientEntity storedIngredient = ingredientRepository.save(newIngredient);
			return crudResponse.createResponse(storedIngredient);
		}
	}
	
	
	
	
	@GetMapping(path = "{id}/steps",produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity stepIndex(@PathVariable long id) {
		Optional<RecipeEntity> recipe = recipeRepository.findById(id);
		if(recipe.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			List<StepEntity> steps = stepRepository.findByRecipe(recipe.get());
			return crudResponse.indexResponse(steps, steps.size());
		}
	}

	
	@PostMapping(path = "{id}/steps",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity createIndex(@PathVariable long id,@Valid @RequestBody StepCreationRequest stepDetails) {
		Optional<RecipeEntity> recipe = recipeRepository.findById(id);
		if(recipe.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			System.out.println(recipe.get().getSteps().toString());
			
			List<StepEntity> steps = recipe.get().getSteps();
		
			StepEntity newStep = modelMapper.map(stepDetails, StepEntity.class);
			
			Integer maxOrder = stepRepository.maxStepOrder(recipe.get().getId());
			
			newStep.setStepOrder(maxOrder==null?0:maxOrder + 1);
			System.out.println(maxOrder);
			if(newStep.getImage()==null) {
				newStep.setImage("https://via.placeholder.com/300");
			}
			
			
			newStep.setRecipe(recipe.get());
			StepEntity storedStep = stepRepository.save(newStep);
			return crudResponse.createResponse(storedStep);
		}
	}
	
	
	@PutMapping(path = "/{id}/steps/{stepId}")
	public ResponseEntity delete(@PathVariable long id,@PathVariable long stepId,@Valid @RequestBody StepUpdateRequest stepDetails) {
		 Authentication authentication = authenticationFacade.getAuthentication();
		  Optional<RecipeEntity> recipe = recipeRepository.findById(id);
		  if(recipe.isEmpty()) {
			  return ResponseEntity.notFound().build();
		  }
		UserEntity user =   userRepository.findByEmail((String)authentication.getPrincipal());
		System.out.println(recipe.get().getUser().toString());
		 if(recipe.get().getUser().getId() != user.getId()) {
			 Map<String, String> response = new HashMap<>();
			 response.put("message", "Recipe does not belong to user");
			 return new ResponseEntity(response,HttpStatus.FORBIDDEN);
		 }
		
		Optional<StepEntity> step = stepRepository.findById(stepId);
		if(step.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			
			
			if(stepDetails.getName()!=null) {
				step.get().setName(stepDetails.getName());
			}
			
			if(stepDetails.getDescription()!=null) {
				step.get().setDescription(stepDetails.getDescription());
			}
			
			if(stepDetails.getImage()!=null) {
				step.get().setImage(stepDetails.getImage());
			}
			
			
		StepEntity updatedStep = 	stepRepository.save(step.get());
			return crudResponse.showAndUpdateResponse(updatedStep, step.isEmpty());
		}
		
	}
	
	@DeleteMapping(path = "/{id}/steps/{stepId}")
	public ResponseEntity stepDelete(@PathVariable long id,@PathVariable long stepId) {
		  Authentication authentication = authenticationFacade.getAuthentication();
		  Optional<RecipeEntity> recipe = recipeRepository.findById(id);
		  if(recipe.isEmpty()) {
			  return ResponseEntity.notFound().build();
		  }
		UserEntity user =   userRepository.findByEmail((String)authentication.getPrincipal());
		System.out.println(recipe.get().getUser().toString());
		 if(recipe.get().getUser().getId() != user.getId()) {
			 Map<String, String> response = new HashMap<>();
			 response.put("message", "Recipe does not belong to user");
			 return new ResponseEntity(response,HttpStatus.FORBIDDEN);
		 }
		Optional<StepEntity> step = stepRepository.findById(stepId);
		if(step.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			stepRepository.delete(step.get());
			return crudResponse.deleteResponse();
		}
		
	}
	
	

	@PutMapping(path = "/{id}/ingredients/{ingredientId}")
	public ResponseEntity delete(@PathVariable long id,@PathVariable long ingredientId,@Valid @RequestBody IngredientUpdateRequest ingredientDetails) {
		 Authentication authentication = authenticationFacade.getAuthentication();
		  Optional<RecipeEntity> recipe = recipeRepository.findById(id);
		  if(recipe.isEmpty()) {
			  return ResponseEntity.notFound().build();
		  }
		UserEntity user =   userRepository.findByEmail((String)authentication.getPrincipal());
		System.out.println(recipe.get().getUser().toString());
		 if(recipe.get().getUser().getId() != user.getId()) {
			 Map<String, String> response = new HashMap<>();
			 response.put("message", "Recipe does not belong to user");
			 return new ResponseEntity(response,HttpStatus.FORBIDDEN);
		 }
		
		Optional<IngredientEntity> ingredient = ingredientRepository.findById(ingredientId);
		if(ingredient.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			
			
			if(ingredientDetails.getName()!=null) {
				ingredient.get().setName(ingredientDetails.getName());
			}
			
			if(ingredientDetails.getDescription()!=null) {
				ingredient.get().setDescription(ingredientDetails.getDescription());
			}
			
			System.out.println(ingredientDetails.getQuantity());
			if(ingredientDetails.getQuantity()!=0) {
				ingredient.get().setQuantity(ingredientDetails.getQuantity());
			}
		IngredientEntity updatedIngredient = 	ingredientRepository.save(ingredient.get());
			return crudResponse.showAndUpdateResponse(updatedIngredient, ingredient.isEmpty());
		}
		
	}

	
	@DeleteMapping(path = "/{id}/ingredients/{ingredientId}")
	public ResponseEntity update(@PathVariable long id,@PathVariable long ingredientId) {
		 Authentication authentication = authenticationFacade.getAuthentication();
		  Optional<RecipeEntity> recipe = recipeRepository.findById(id);
		  if(recipe.isEmpty()) {
			  return ResponseEntity.notFound().build();
		  }
		UserEntity user =   userRepository.findByEmail((String)authentication.getPrincipal());
		System.out.println(recipe.get().getUser().toString());
		 if(recipe.get().getUser().getId() != user.getId()) {
			 Map<String, String> response = new HashMap<>();
			 response.put("message", "Recipe does not belong to user");
			 return new ResponseEntity(response,HttpStatus.FORBIDDEN);
		 }
		
		Optional<IngredientEntity> ingredient = ingredientRepository.findById(ingredientId);
		if(ingredient.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			ingredientRepository.delete(ingredient.get());
			return crudResponse.deleteResponse();
		}
		
	}
}
