package com.CMI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.CMI.entity.Vehicle;
import com.CMI.service.VehicleService;

@RestController
public class VehicleController {

	@Autowired
	private VehicleService service;
	
	public Vehicle AddVehicle(Vehicle vehicle) {
		return service.saveVehicle(vehicle);
	}
	
	public String DeteleVehicle(int id) {
		return service.deleteVehicle(id);
	}
	
}
