package com.CMI.entity;

import java.time.LocalDateTime;

public class Booking {
	private int id;
	private User user;
	private Vehicle Vehicle;
	private CarPark carPark;
	private boolean active;
	private LocalDateTime bookingDateTime;
	
	public Booking() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Vehicle getVehicle() {
		return Vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		Vehicle = vehicle;
	}

	public CarPark getCarPark() {
		return carPark;
	}

	public void setCarPark(CarPark carPark) {
		this.carPark = carPark;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public LocalDateTime getBookingDateTime() {
		return bookingDateTime;
	}

	public void setBookingDateTime(LocalDateTime bookingDateTime) {
		this.bookingDateTime = bookingDateTime;
	}
	
}
