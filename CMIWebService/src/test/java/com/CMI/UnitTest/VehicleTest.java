package com.CMI.UnitTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.CMI.entity.PaymentCard;
import com.CMI.entity.User;
import com.CMI.entity.Vehicle;
import com.CMI.repository.VehicleRepository;
import com.CMI.service.VehicleService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleTest {
		
	@Autowired
	private VehicleService service;
	
	@MockBean
	private VehicleRepository repository;
	
	private User user;
	private Vehicle vehicle;
	private PaymentCard paymentCard;
	
	@BeforeEach
	public void setUp() {
		user = new User();
		user.setId(1);
		user.setUsername("test");
		user.setPassword("password");
		user.setEmail("email@gmail.com");
		user.setFirstName("firstName");
		user.setLastName("lastName");

		vehicle = new Vehicle();
		vehicle.setId(1);
		vehicle.setPlateNum("ABC1234");
		vehicle.setTypeOfVehicle("Car");
		
		
		paymentCard = new PaymentCard();
		paymentCard.setCardNum("1234567891345");
		paymentCard.setCcv(123);
		paymentCard.setExpiry_date("07/25");

		user.addVehicle(vehicle);
		user.addPaymentCard(paymentCard);
	}
	
	@Test
	public void getVehiclesByUserTest() {
		Vehicle vehicle2 = new Vehicle();
		vehicle2.setPlateNum("A3334BB");
		vehicle2.setTypeOfVehicle("Car");
		user.addVehicle(vehicle2);
		when(repository.getVehiclesByUser(user)).thenReturn(user.getVehicles());
		assertThat(service.getVehiclesByUser(user).size()).isEqualTo(2);
	}
	@Test
	public void getVehicleByIdTest() {
		 when(repository.findById(1)).thenReturn(Optional.of(vehicle));
		   assertThat(service.getVehicleById(1).getId()).isEqualTo(1);
	   }
	
	@Test
	public void saveVehicleTest() {
		Vehicle vehicle2 = new Vehicle();
		vehicle2.setPlateNum("A3334BB");
		vehicle2.setTypeOfVehicle("Car");
		user.addVehicle(vehicle2);
		when(repository.save(vehicle2)).thenReturn(vehicle2);
		assertThat(service.saveVehicle(vehicle2)).isEqualTo(vehicle2);
	}
	@Test
	public void deleteVehicleTest() {
		 when(repository.findById(1)).thenReturn(Optional.of(vehicle));
		   service.deleteVehicle(1);
		  verify(repository , times(1)).deleteById(1);
	}
	@Test
	public void updateVehicleTest() {
		 when(repository.findById(1)).thenReturn(Optional.of(vehicle));
		   when(repository.save(vehicle)).thenReturn(vehicle);
		   vehicle.setPlateNum("YA1223AA");
		   assertThat(service.updateVehicle(vehicle).getPlateNum()).isEqualTo("YA1223AA");
	   }
}