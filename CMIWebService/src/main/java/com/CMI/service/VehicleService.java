package com.CMI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CMI.entity.User;
import com.CMI.entity.Vehicle;
import com.CMI.repository.VehicleRepository;

@Service
public class VehicleService {
	@Autowired
	private VehicleRepository repository;	
	
	public Vehicle saveVehicle(Vehicle vehicle) {
		return repository.save(vehicle);
	}
	
	public Vehicle getVehicleById(int id){
		return repository.findById(id).orElse(null);
	}
	public List<Vehicle> getVehiclesByUser(User user){
		return repository.getVehiclesByUser(user);
	}
	
	public String deleteVehicle(int id) {
		repository.deleteById(id);
		return "delete Vehicle successfully";
	}
	public Vehicle updateVehicle(Vehicle vehicle) {
		Vehicle oldVehicle = repository.findById(vehicle.getId()).orElse(null);
		oldVehicle.setPlateNum(vehicle.getPlateNum());
		oldVehicle.setTypeOfVehicle(vehicle.getTypeOfVehicle());
		oldVehicle.setUser(vehicle.getUser());;
		return repository.save(oldVehicle);
	}
}
