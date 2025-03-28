package com.suraj.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suraj.util.CommonUtil;

@RestController
public class HomeController {
	
	@GetMapping("/company")
	public ResponseEntity<?> getCompany()
	{
		List<String> company = Arrays.asList("Microsoft","IBM","TCS","Google","Wipro");
		return new ResponseEntity<>(company,HttpStatus.OK);
	}
	
	@GetMapping("/com")
	public ResponseEntity<?> getCompanys()
	{
		List<String> company = Arrays.asList("Microsoft","IBM","TCS","Google","Wipro");
		return CommonUtil.createBuildResponse(company,"success",HttpStatus.OK);
	}
	
	@GetMapping("/users")
	public ResponseEntity<?> users()
	{
		List<String> company = Arrays.asList("suraj","rahul","shivam","swayam","aarti");
		return CommonUtil.createBuildResponse(company,"success",HttpStatus.OK);
	}
	
	
	

	@GetMapping("/save-user")
	public ResponseEntity<?> saveuser()
	{
		// service login
		return CommonUtil.createBuildResponse("Save Success","success",HttpStatus.CREATED);
	}
	
	
	@GetMapping("/error")
	public ResponseEntity<?> error()
	{
		// service login
		try {
			int x=8/0;
		} catch (Exception e) {
			// TODO: handle exception
			return CommonUtil.createBuildResponse(e.getMessage(),"failed",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return CommonUtil.createBuildResponse("Save Success","success",HttpStatus.CREATED);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
