package com.CMI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CMI.entity.Booking;
import com.CMI.entity.CarPark;
import com.CMI.entity.User;
import com.CMI.entity.Vehicle;
import com.CMI.service.BookingService;
import com.CMI.service.CarParkService;
import com.CMI.service.UserService;
import com.CMI.service.VehicleService;

@RestController
public class BookingController {
	
	@Autowired
	private BookingService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VehicleService vehicleService;
	
	@Autowired
	private CarParkService carParkService;
	
	@PostMapping("api/bookings/create")
	public Booking createBooking(@RequestBody Booking booking ,  @RequestParam int userId ,  @RequestParam int vehicleId , @RequestParam int carParkId) {
		User user = userService.getUserById(userId);
		Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
		CarPark carPark = carParkService.getCarParkById(carParkId);
		booking.setUser(user);
		booking.setVehicle(vehicle);
		booking.setCarPark(carPark);
		return service.saveBooking(booking);
	}
	
	@PutMapping("api/bookings/{id}")
	public Booking updateBooking(@PathVariable int id , @RequestBody Booking booking) {
		return service.updateBooking(booking);
	}
	@DeleteMapping("api/bookings/{id}")
	public String deleteUser(@PathVariable int id) {
		return service.deleteBooking(id);
	}
	
	@GetMapping("api/bookings/")
	public List<Booking> getUserByUsername(@RequestParam  String username) {
		return service.getBookingsByUser(userService.getUserByUsername(username));
	}
	
}
