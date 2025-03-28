package com.suraj.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.suraj.dto.UserRequest;
import com.suraj.model.User;
import com.suraj.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HomeController {
	
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/")
	public ResponseEntity<?> getDetails(HttpServletRequest request)
	{
		//String id = request.getSession().getId();
		
		
		return new ResponseEntity<>("Hello , Welcome to Spring Boot=",HttpStatus.OK);
	}
	
	
	
	@GetMapping("/user")
	public ResponseEntity<?> getUserDetails(HttpServletRequest request)
	{
		
		
		
		return new ResponseEntity<>(userService.getUser(),HttpStatus.OK);
	}
	

	// JWT step-3
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserRequest userRequest)
	{
		
		String token = userService.login(userRequest);
		
		if(token==null)
		{
			return new ResponseEntity<>("Invalid Credentials",HttpStatus.BAD_REQUEST);
		}
	
		return new ResponseEntity<>(token,HttpStatus.OK);
	}

}
