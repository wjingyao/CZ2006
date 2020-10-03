package com.CMI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CMI.entity.User;
import com.CMI.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService service;
	
	
	@PostMapping("api/users")
	public User createUser(@RequestBody User user) {
		return service.saveUser(user);
	}
	
	@GetMapping("api/users")
	public List<User> getUsers(){
		return service.getUsers();
	}
	
	@GetMapping("api/users/{id}")
	public User getUserById(@PathVariable int id) {
		return service.getUserById(id);
	}
	
	@GetMapping("api/users/")
	public User getUserByUsername(@RequestParam  String username) {
		return service.getUserByUsername(username);
	}	 
	
	@PutMapping("api/users/{id}")
	public User updateUser(@PathVariable int id ,@RequestBody User user) {
		return service.updateUser(user);
	}
	@DeleteMapping("api/users/{id}")
	public String deleteUser(@PathVariable int id) {
		return service.deleteUser(id);
	}
	
}
