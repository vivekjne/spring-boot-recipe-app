package com.recipe.ws.shared;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CrudResponse {

	public ResponseEntity<Map<String,Object>> indexResponse(Object object,Integer count){
		Map<String,Object> response = new HashMap<String,Object>();
		response.put("data", object);
		if(count!=null) {
		response.put("itemCount",count);
		}
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
	
	public ResponseEntity<Map<String,Object>> createResponse(Object object){
		Map<String,Object> response = new HashMap<String,Object>();
		response.put("data", object);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	public ResponseEntity<Map<String,Object>> showAndUpdateResponse(Object object,boolean isEmpty){
		Map<String,Object> response = new HashMap<String,Object>();
		if(isEmpty) {
			response.put("message", "Record not found");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		response.put("data", object);
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
	}
	
	public ResponseEntity deleteResponse(){
		
		return  ResponseEntity.noContent().build();
	}
	
}
