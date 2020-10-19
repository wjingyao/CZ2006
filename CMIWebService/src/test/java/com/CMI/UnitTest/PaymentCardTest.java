package com.CMI.UnitTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

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
import com.CMI.repository.PaymentCardRepository;
import com.CMI.repository.VehicleRepository;
import com.CMI.service.PaymentCardService;
import com.CMI.service.VehicleService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentCardTest {
		
	@Autowired
	private PaymentCardService service;
	
	@MockBean
	private PaymentCardRepository repository;
	
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
		paymentCard.setId(1);
		paymentCard.setCardNum("1234567891345");
		paymentCard.setCcv(123);
		paymentCard.setExpiry_date("07/25");

		user.addVehicle(vehicle);
		user.addPaymentCard(paymentCard);
	}
	
	@Test
	public void getPaymentCardsByUserTest() {
		PaymentCard paymentCard2 = new PaymentCard();
		paymentCard2.setId(2);
		paymentCard2.setCardNum("931203912039000");
		paymentCard2.setCcv(345);
		paymentCard2.setExpiry_date("04/25");
		user.addPaymentCard(paymentCard2);
		when(repository.getPaymentCardsByUser(user)).thenReturn(user.getPaymentCards());
		assertThat(service.getPaymentCardsByUser(user).size()).isEqualTo(2);
	}
	@Test
	public void getPaymentCardByIdTest() {
		 when(repository.findById(1)).thenReturn(Optional.of(paymentCard));
		   assertThat(service.getPaymentCardById(1).getId()).isEqualTo(1);
	   }
	
	@Test
	public void savePaymentCardTest() {
		PaymentCard paymentCard2 = new PaymentCard();
		paymentCard2.setId(2);
		paymentCard2.setCardNum("931203912039000");
		paymentCard2.setCcv(345);
		paymentCard2.setExpiry_date("04/25");
		user.addPaymentCard(paymentCard2);
		when(repository.save(paymentCard2)).thenReturn(paymentCard2);
		assertThat(service.savePaymentCard(paymentCard2)).isEqualTo(paymentCard);
	}
	@Test
	public void deletePaymentCardTest() {
		 when(repository.findById(1)).thenReturn(Optional.of(paymentCard));
		   service.deletePaymentCard(1);
		  verify(repository , times(1)).deleteById(1);
	}
	@Test
	public void updatePaymentCardTest() {
		 when(repository.findById(1)).thenReturn(Optional.of(paymentCard));
		   when(repository.save(paymentCard)).thenReturn(paymentCard);
		   paymentCard.setCcv(999);
		   assertThat(service.updatePaymentCard(paymentCard).getCcv()).isEqualTo(999);
	   }
}