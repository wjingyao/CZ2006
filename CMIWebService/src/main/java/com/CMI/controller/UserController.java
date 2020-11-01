package com.CMI.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.validation.ValidationBindHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CMI.dtoView.UserView;
import com.CMI.entity.User;
import com.CMI.entity.Vehicle;
import com.CMI.security.JwtUtil;
import com.CMI.service.MyUserDetailsService;
import com.CMI.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
public class UserController {
	@Autowired
	private UserService service;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@PostMapping("api/users/register")
	public User createUser(@RequestBody User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return service.saveUser(user);
	}
	
	@JsonView(UserView.View.class)
	@PostMapping("api/users/login")
	public Map<String,Object> Login(@RequestBody User user) throws Exception {
		Map<String,Object> ret = new HashMap<String,Object>();
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
		
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		
		if(userDetails != null) {
			ret.put("user", service.getUserByUsername(user.getUsername()));
			ret.put("token", jwt);
		}
		
		User user1 = service.getUserByUsername(user.getUsername());

		return ret;
	

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
		user.setId(id);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return service.updateUser(user);
	}
	@DeleteMapping("api/users/{id}")
	public String deleteUser(@PathVariable int id) {
		return service.deleteUser(id);
	}
	
}
