package com.suraj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.suraj.model.User;
import com.suraj.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/save")
	public ResponseEntity<?> saveUser(@RequestBody User user) {
		// System.out.println(user);
		User saveUser = userService.saveUser(user);
		if (ObjectUtils.isEmpty(saveUser)) {
			return new ResponseEntity<>("User not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
		}
	}

	@GetMapping("/getUsers")
	public ResponseEntity<?> getAllUser() {
		List<User> allUser = userService.getAllUser();

		return new ResponseEntity<>(allUser, HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		// System.out.println(user);
		User saveUser = userService.updateUser(user);
		if (ObjectUtils.isEmpty(saveUser)) {
			return new ResponseEntity<>("User not updated", HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
		}
	}
	
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
		userService.deleteUser(id);

		return new ResponseEntity<>("Delete Successfully", HttpStatus.OK);
	}
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Integer id)
	{
		User userById = userService.getUserById(id);
		if(ObjectUtils.isEmpty(userById))
		{
			return new ResponseEntity<>("User Not Found",HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<>(userById,HttpStatus.OK);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
