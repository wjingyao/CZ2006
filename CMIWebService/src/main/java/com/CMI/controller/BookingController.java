package com.CMI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.CMI.entity.Booking;
import com.CMI.service.BookingService;

@RestController
public class BookingController {
	
	@Autowired
	private BookingService service;
	
	public Booking createBooking(Booking booking) {
		return service.saveBooking(booking);
	}
	
	public Booking updateBooking(Booking booking) {
		return service.updateBooking(booking);
	}
	
}
