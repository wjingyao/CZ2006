package com.CMI.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.CMI.entity.CarPark;

public interface CarParkRepository  extends JpaRepository<CarPark,Integer>{
		
 
	List<CarPark> getCarParksByCarParkName(String carParkName);
}
