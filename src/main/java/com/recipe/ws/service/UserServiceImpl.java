package com.recipe.ws.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.recipe.ws.data.entities.UserEntity;
import com.recipe.ws.data.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		 UserEntity userEntity =  userRepository.findByEmail(email);
		 if(userEntity == null) throw new UsernameNotFoundException(email);
		 
		 return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),new ArrayList<>());
	}



}
