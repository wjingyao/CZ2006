package com.CMI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.CMI.entity.PaymentCard;
import com.CMI.entity.Vehicle;
import com.CMI.service.UserService;
import com.CMI.service.VehicleService;

@RestController
public class VehicleController {

	@Autowired
	private VehicleService service;
	
	@Autowired
	private UserService userService;
	
	public Vehicle AddVehicle(Vehicle vehicle) {
		return service.saveVehicle(vehicle);
	}
	
	public String DeteleVehicle(int id) {
		return service.deleteVehicle(id);
	}
	
	public List<Vehicle> getVehiclesByUser(int UserID){ 
		return service.getVehiclesByUser(userService.getUserById(UserID));
		
	}
	
}
