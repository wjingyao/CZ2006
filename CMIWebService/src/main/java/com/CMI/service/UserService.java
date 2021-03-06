package com.CMI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CMI.entity.User;
import com.CMI.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository repository;
	
	public User saveUser(User user) {
		return repository.save(user);
	}
	
	public User getUserById(int id){
		return repository.findById(id).orElse(null);
	}
	public List<User> getUsers(){
		return repository.findAll();
	}
	
	public User getUserByUsername(String username) {
		return repository.findByUsername(username);
	}
	
	public String deleteUser(int id) {
		repository.deleteById(id);
		return "delete user successfully";
	}
	public User updateUser(User user) {
		User oldUser = repository.findById(user.getId()).orElse(null);
		oldUser.setPassword(user.getPassword());
		return repository.save(oldUser);
	}
}
