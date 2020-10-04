package com.CMI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.CMI.entity.CarPark;
import com.CMI.service.CarParkService;

@RestController
public class CarParkController {
	@Autowired
	private CarParkService service;
	
	public List<CarPark> searchCarparkByPostalCode(int PostalCode){
		//return placeholder
		return service.getCarParks();
	}
	
	public CarPark updateCarparkAvailableLots(int id , int num) {
		CarPark cp = service.getCarParkById(id);
		cp.setLot_available(num);
		return service.updateCarPark(cp);
	}
	
}
