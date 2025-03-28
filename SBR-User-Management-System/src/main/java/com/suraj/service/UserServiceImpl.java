package com.suraj.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suraj.model.User;
import com.suraj.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public User saveUser(User user) {

		return userRepo.save(user);
	}

	@Override
	public List<User> getAllUser() {
		List<User> allUsers = userRepo.findAll();
		return allUsers;
	}

	@Override
	public User updateUser(User user) {
		return userRepo.save(user);

	}

	@Override
	public void deleteUser(Integer id) {
		Optional<User> findById = userRepo.findById(id);
		if (findById.isPresent()) {
			User user = findById.get();
			userRepo.delete(user);
		}
	}

	@Override
	public User getUserById(Integer id) {
		Optional<User> findByIdUser = userRepo.findById(id);
		if(findByIdUser.isPresent())
		{
			return findByIdUser.get();
		}
		return null;
	}

}
