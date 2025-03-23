package com.suraj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.suraj.dto.UserRequest;
import com.suraj.model.User;
import com.suraj.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private AuthenticationManager authenticationManager;

	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserRepo userRepo;
	
	//JWT step-2
	@Override
	public String login(UserRequest request) {

		Authentication authenticate = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		if (authenticate.isAuthenticated()) {
			return jwtService.generateToken(request.getUsername());
		}

		return null;
	}

	@Override
	public List<User> getUser() {
		List<User> all = userRepo.findAll();
		return all;
	}

}
