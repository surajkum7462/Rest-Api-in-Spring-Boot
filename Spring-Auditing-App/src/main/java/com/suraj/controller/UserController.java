package com.suraj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.suraj.model.User;
import com.suraj.repo.UserRepo;

@RestController
public class UserController {
	
	@Autowired
	private UserRepo userRepo;
	
	
	@PostMapping("/save")
	public ResponseEntity<?> saveUser(@RequestBody User user)
	{
		User save = userRepo.save(user);
		return new ResponseEntity<>(save,HttpStatus.CREATED);
	}
	

}
