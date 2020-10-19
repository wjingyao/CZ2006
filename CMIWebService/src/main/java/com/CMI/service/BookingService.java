package com.CMI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CMI.entity.Booking;
import com.CMI.entity.User;
import com.CMI.repository.BookingRepository;

@Service
public class BookingService {
	
	@Autowired
	private BookingRepository repository;
		
	public Booking saveBooking(Booking booking) {
		return repository.save(booking);
	}
	
	public Booking getBookingById(int id){
		return repository.findById(id).orElse(null);
	}
	public List<Booking> getBookingsByUser(User user){
		return repository.getBookingsByUser(user);
	}
	public List<Booking> getBookings(){
		return repository.findAll();
	}
	
	public String deleteBooking(int id) {
		repository.deleteById(id);
		return "delete Booking successfully";
	}
	public Booking updateBooking(Booking booking) {
		Booking oldBooking = repository.findById(booking.getId()).orElse(null);
		oldBooking.setActive(booking.isActive());
		oldBooking.setBookingDateTime(booking.getBookingDateTime());
		oldBooking.setCarPark(booking.getCarPark());
		oldBooking.setUser(booking.getUser());
		oldBooking.setVehicle(booking.getVehicle());
		return repository.save(oldBooking);
	}
}
