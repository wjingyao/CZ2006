package com.CMI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CMI.entity.Booking;
import com.CMI.entity.User;


public interface BookingRepository  extends JpaRepository<Booking,Integer>{
		
	List<Booking> getBookingsByUser(User user);
}
