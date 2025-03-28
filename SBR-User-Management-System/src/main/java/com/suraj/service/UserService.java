package com.suraj.service;

import java.util.List;

import com.suraj.model.User;

public interface UserService {
	
	public User saveUser(User user);
	
	public List<User> getAllUser();
	
	public User updateUser(User user);
	
	public void deleteUser(Integer id);
	
	public User getUserById(Integer id);

}
