package com.CMI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CMI.entity.PaymentCard;
import com.CMI.entity.User;
import com.CMI.entity.Vehicle;
import com.CMI.service.UserService;
import com.CMI.service.VehicleService;

@RestController
public class VehicleController {

	@Autowired
	private VehicleService service;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("api/vehicles/create")
	public Vehicle AddVehicle(@RequestBody Vehicle vehicle , @RequestParam int userId) {
		User user = userService.getUserById(userId);
		vehicle.setUser(user);
		System.out.println(vehicle.getPlateNum());
		return service.saveVehicle(vehicle);
	}
	
	@DeleteMapping("api/vehicles/{id}")
	public String DeteleVehicle(@PathVariable int id) {
		return service.deleteVehicle(id);
	}
	
	@GetMapping("api/vehicles/")
	public List<Vehicle> getVehiclesByUser(@RequestParam int userId){ 
		return service.getVehiclesByUser(userService.getUserById(userId));
		
	}

}
