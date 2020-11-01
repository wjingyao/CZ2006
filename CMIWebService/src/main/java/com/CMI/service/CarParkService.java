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
	public CarPark getCarParkByCarParkName(String name) {
		return repository.getCarParkByCarParkName(name);
	}
	
	public List<CarPark> getNearbyCarParksByCoord(double x , double y , double distance){
		return repository.getNearbyCarParksByCoord(x , y , distance);
	}
	
	public List<CarPark> getCarParksByAddress(String address){
		return repository.getCarParksByAddressContaining(address);
	}
	
	public String deleteCarPark(int id) {
		repository.deleteById(id);
		return "delete Car Park successfully";
	}
	public CarPark updateCarPark(CarPark carPark) {
		CarPark oldCarPark = repository.findById(carPark.getId()).orElse(null);
		oldCarPark.setHeavyVehicleRate(carPark.getHeavyVehicleRate());
		oldCarPark.setLot_available(carPark.getLot_available());
		oldCarPark.setTotal_lot(carPark.getTotal_lot());
		return repository.save(oldCarPark);
	}
	
}
