package com.CMI.entity;

public class Vehicle {
		private int id;
		private User user;
		private String plateNum;
		private String typeOfVehicle;
		
		public Vehicle() {
			
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

		public String getPlateNum() {
			return plateNum;
		}

		public void setPlateNum(String plateNum) {
			this.plateNum = plateNum;
		}

		public String getTypeOfVehicle() {
			return typeOfVehicle;
		}

		public void setTypeOfVehicle(String typeOfVehicle) {
			this.typeOfVehicle = typeOfVehicle;
		}
		
}
