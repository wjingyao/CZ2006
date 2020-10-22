package com.CMI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CMI.entity.CarPark;
import com.CMI.entity.PaymentCard;
import com.CMI.entity.User;
import com.CMI.repository.CarParkRepository;
import com.CMI.repository.PaymentCardRepository;

@Service
public class CarParkService {
	@Autowired
	private CarParkRepository repository;
		
	public CarPark saveCarPark(CarPark carPark) {
		return repository.save(carPark);
	}
	
	public CarPark getCarParkById(int id){
		return repository.findById(id).orElse(null);
	}
	/*
	public CarPark getCarParkByPostalCode(int postalCode) {
		return repository.getCarParksByPostalCode(postalCode);
	}*/
	public List<CarPark> getCarParks(){
		return repository.findAll();
	}
	
	public List<CarPark> getNearbyCarParksByCoord(double x , double y , double distance){
		return repository.getNearbyCarParksByCoord(x , y , distance);
	}
	
	public List<CarPark> getCarParksByCarParkName(String carParkName){
		return repository.getCarParksByCarParkName(carParkName);
	}
	
	public String deleteCarPark(int id) {
		repository.deleteById(id);
		return "delete Car Park successfully";
	}
	public CarPark updateCarPark(CarPark carPark) {
		CarPark oldCarPark = repository.findById(carPark.getId()).orElse(null);
		oldCarPark.setAddress(carPark.getAddress());
		oldCarPark.setCarkParkName(carPark.getCarParkName());
		oldCarPark.setCarRate(carPark.getCarRate());
		oldCarPark.setHeavyVehicleRate(carPark.getHeavyVehicleRate());
		oldCarPark.setLot_available(carPark.getLot_available());
		oldCarPark.setMotorcycleRate(carPark.getMotorcycleRate());
		oldCarPark.setPostalCode(carPark.getPostalCode());
		oldCarPark.setTotal_lot(carPark.getTotal_lot());
		oldCarPark.setLot_type(carPark.getLot_type());
		oldCarPark.setX(carPark.getX());
		oldCarPark.setY(carPark.getY());
		return repository.save(oldCarPark);
	}
}
