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
import com.CMI.repository.UserRepository;
import com.CMI.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
		
	@Autowired
	private UserService service;
	
	@MockBean
	private UserRepository repository;
	
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
	public void getAllUsersTest() {
		User user2 = user;
		user2 .setUsername("test2");
		user2.setEmail("email2@gmail.com");
		when(repository.findAll()).thenReturn(Stream.of(
				user,user2).collect(Collectors.toList()));
		assertThat(service.getUsers().size()).isEqualTo(2);
	}
   @Test
   public void getUserByIdTest() {
	  when(repository.findById(1)).thenReturn(Optional.of(user));
	   assertThat(service.getUserById(1).getId()).isEqualTo(1);
   }
   
   @Test
   public void getUserByUsernameTest() {
	   when(repository.findByUsername("test")).thenReturn(user);
	   assertThat(service.getUserByUsername("test")).isEqualTo(user);
   }
   
   @Test
   public void saveUserTest() {
	   when(repository.save(user)).thenReturn(user);
	   assertThat(service.saveUser(user)).isEqualTo(user);
   }
   @Test
   public void deleteUserTest() {
	   when(repository.findById(1)).thenReturn(Optional.of(user));
	   service.deleteUser(1);
	  verify(repository , times(1)).deleteById(1);
   }
   
   @Test
   public void updateUserTest() {
	   when(repository.findById(1)).thenReturn(Optional.of(user));
	   when(repository.save(user)).thenReturn(user);
	   user.setPassword("testtest");
	   assertThat(service.updateUser(user).getPassword()).isEqualTo("testtest");
   }
}
