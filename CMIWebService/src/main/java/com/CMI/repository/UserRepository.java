package com.CMI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CMI.entity.User;

public interface UserRepository extends JpaRepository<User,Integer>{

	User findByUsername(String username);
	
}
	