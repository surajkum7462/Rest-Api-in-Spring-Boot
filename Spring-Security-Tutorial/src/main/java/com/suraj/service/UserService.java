package com.suraj.service;

import java.util.List;

import com.suraj.dto.UserRequest;
import com.suraj.model.User;

public interface UserService {
	
	public String login(UserRequest request);
	
	public List<User> getUser();

}
