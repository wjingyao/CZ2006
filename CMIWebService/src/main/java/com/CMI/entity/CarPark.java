package com.CMI.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CarPark")
public class CarPark {
	@Id
	@GeneratedValue
	@Column(unique = true)
	private int id;
	@Column(name = "carParkName")
	private String carParkName;
	private int total_lot;
	private char lot_type;
	private int lot_available;
	private double carRate;
	private double motorcycleRate;
	private double heavyVehicleRate;
	private int postalCode;
	private String Address;
	private double x;
	private double y;

	public CarPark() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getCarParkName() {
		return carParkName;
	}

	public void setCarkParkName(String carParkName) {
		this.carParkName = carParkName;
	}

	public int getTotal_lot() {
		return total_lot;
	}

	public void setTotal_lot(int total_lot) {
		this.total_lot = total_lot;
	}

	
	public char getLot_type() {
		return lot_type;
	}

	public void setLot_type(char lot_type) {
		this.lot_type = lot_type;
	}

	public int getLot_available() {
		return lot_available;
	}

	public void setLot_available(int lot_available) {
		this.lot_available = lot_available;
	}

	public double getCarRate() {
		return carRate;
	}

	public void setCarRate(double carRate) {
		this.carRate = carRate;
	}

	public double getMotorcycleRate() {
		return motorcycleRate;
	}

	public void setMotorcycleRate(double motorcycleRate) {
		this.motorcycleRate = motorcycleRate;
	}

	public double getHeavyVehicleRate() {
		return heavyVehicleRate;
	}

	public void setHeavyVehicleRate(double heavyVehicleRate) {
		this.heavyVehicleRate = heavyVehicleRate;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
