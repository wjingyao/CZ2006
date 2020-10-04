package com.CMI.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "Booking")
public class Booking {
	
	@Id
	@GeneratedValue
	@Column(unique = true)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "user", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "vehicle", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Vehicle Vehicle;
	
	@ManyToOne
	@JoinColumn(name = "carPark", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
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
