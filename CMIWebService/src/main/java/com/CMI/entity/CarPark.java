package com.CMI.entity;

public class CarPark {
	private int id;
	private String carkparkName;
	private int total_lot;
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

	public String getCarkparkName() {
		return carkparkName;
	}

	public void setCarkparkName(String carkparkName) {
		this.carkparkName = carkparkName;
	}

	public int getTotal_lot() {
		return total_lot;
	}

	public void setTotal_lot(int total_lot) {
		this.total_lot = total_lot;
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
