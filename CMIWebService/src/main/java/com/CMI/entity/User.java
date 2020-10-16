package com.CMI.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.CMI.dtoView.UserView;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "User")
public class User {
	@JsonView(UserView.View.class)
	@Id
	@GeneratedValue
	@Column(unique = true)
	private int id;
	
	@JsonView(UserView.View.class)
	@Column(nullable = false, length = 30)
	private String username;
	
	@Column(nullable = false, length = 100)
	private String password;
	@JsonView(UserView.View.class)
	@Column(nullable = false, length = 30)
	private String firstName;
	@JsonView(UserView.View.class)
	@Column(nullable = false, length = 30)
	private String lastName;
	@JsonView(UserView.View.class)
	@Column(nullable = false, length = 100)
	private String email;
	
	@JsonView(UserView.View.class)
	@OneToMany(mappedBy = "user" , cascade = CascadeType.ALL , orphanRemoval = true)
	private List<PaymentCard> paymentCards;
	
	@JsonView(UserView.View.class)
	@OneToMany(mappedBy = "user" , cascade = CascadeType.ALL , orphanRemoval = true)
	private List<Vehicle> vehicles;

	public User() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<PaymentCard> getPaymentCards() {
		return paymentCards;
	}

	public void setPaymentCards(List<PaymentCard> paymentCards) {
		this.paymentCards = paymentCards;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	
   public void addPaymentCard(PaymentCard paymentCard) {
	   this.paymentCards.add(paymentCard);
	   paymentCard.setUser(this);
   }
   public void deletePaymentCard(PaymentCard paymentCard) {
	   this.paymentCards.remove(paymentCard);
	   paymentCard.setUser(null);
   }
   public void addVehicle(Vehicle vehicle) {
	   this.vehicles.add(vehicle);
	   vehicle.setUser(this);
   }
   public void deleteVehicle(Vehicle vehicle) {
	   this.vehicles.remove(vehicle);
	   vehicle.setUser(null);
   }
}
