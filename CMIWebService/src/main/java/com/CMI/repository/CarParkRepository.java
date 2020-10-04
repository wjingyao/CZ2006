package com.CMI.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.CMI.entity.CarPark;

public interface CarParkRepository  extends JpaRepository<CarPark,Integer>{
		
	
}
