package com.CMI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CMI.entity.CarPark;
import com.CMI.service.CarParkService;

@RestController
public class CarParkController {
	@Autowired
	private CarParkService service;
	
	
	@GetMapping("api/carParks/postalCode")
	public List<CarPark> searchCarparkByPostalCode(@RequestParam int PostalCode){
		//return placeholder
		return service.getCarParks();
	}
	@GetMapping("api/carParks/")
	public List<CarPark> searchCarparkByCoord(@RequestParam double x ,@RequestParam double y ,@RequestParam double distance){
		return service.getNearbyCarParksByCoord(x, y, distance);
	}
	
	@PutMapping("api/carParks/{id}")
	public CarPark updateCarparkAvailableLots(@PathVariable int id , @RequestBody int num) {
		CarPark cp = service.getCarParkById(id);
		cp.setLot_available(cp.getLot_available()+num);
		
		return service.updateCarPark(cp);
	}
	
}
