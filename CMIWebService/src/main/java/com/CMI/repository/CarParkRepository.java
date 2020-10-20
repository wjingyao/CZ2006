package com.CMI.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CMI.entity.CarPark;

public interface CarParkRepository  extends JpaRepository<CarPark,Integer>{
		
 
	List<CarPark> getCarParksByCarParkName(String carParkName);
	
	String HAVERSINE_FORMULA = "(6371 * acos(cos(radians(:x)) * cos(radians(s.x)) *" +
	        " cos(radians(s.y) - radians(:y)) + sin(radians(:x)) * sin(radians(s.x))))";
	
	@Query("SELECT c FROM CarPark c WHERE " + HAVERSINE_FORMULA + " < :distance ORDER BY "+ HAVERSINE_FORMULA + " DESC")
	List<CarPark> getNearbyCarParksByCoord(@Param("x") double x, @Param("y") double y, @Param("distance") double distance);
}
