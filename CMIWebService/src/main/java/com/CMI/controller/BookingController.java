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
import com.CMI.service.BookingService;
import com.CMI.service.UserService;

@RestController
public class BookingController {
	
	@Autowired
	private BookingService service;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("api/bookings/create")
	public Booking createBooking(@RequestBody Booking booking) {
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
