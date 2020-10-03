package com.CMI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CMI.entity.User;
import com.CMI.entity.Vehicle;

public interface VehicleRepository  extends JpaRepository<Vehicle,Integer>{
		
	List<Vehicle> getVehiclesByUser(User user);
}
