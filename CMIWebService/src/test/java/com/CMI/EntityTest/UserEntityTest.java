package com.CMI.EntityTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.CMI.entity.PaymentCard;
import com.CMI.entity.User;
import com.CMI.entity.Vehicle;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class UserEntityTest {

	@Autowired
	private TestEntityManager entityManager;

	private User user;
	private Vehicle vehicle;
	private PaymentCard paymentCard;

	@BeforeEach
	public void setUp() {
		user = new User();
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
	public void saveUser() {
		User savedUser = this.entityManager.persistAndFlush(user);
		assertThat(savedUser.getUsername().equals("test"));
	}

	@Test
	public void addVehicle() {
		Vehicle vehicle2 = new Vehicle();

		vehicle2.setPlateNum("ABC1111");
		vehicle2.setTypeOfVehicle("Car");
		user.addVehicle(vehicle2);

		User savedUser = this.entityManager.persistAndFlush(user);
		assertThat(savedUser.getId()).isNotNull();
		assertThat(savedUser.getUsername()).isEqualTo("test");
		assertThat(savedUser.getVehicles().size()).isEqualTo(2);

	}

	@Test
	public void addPaymentCard() {
		PaymentCard pc = new PaymentCard();
		pc.setCardNum("111111111111111111");
		pc.setCcv(111);
		pc.setExpiry_date("07/25");
		user.addPaymentCard(pc);

		User savedUser = this.entityManager.persistAndFlush(user);
		assertThat(savedUser.getId()).isNotNull();
		assertThat(savedUser.getUsername()).isEqualTo("test");
		assertThat(savedUser.getPaymentCards().size()).isEqualTo(2);
	}

	@Test
	public void removeVehicle() {
		User savedUser = this.entityManager.persistAndFlush(user);

		savedUser.deleteVehicle(vehicle);
		User updatedUser = this.entityManager.persistAndFlush(savedUser);

		assertThat(updatedUser.getUsername()).isEqualTo("test");
		assertThat(updatedUser.getVehicles().size()).isEqualTo(0);

	}

	@Test
	public void removePaymentCard() {
		User savedUser = this.entityManager.persistAndFlush(user);

		savedUser.deletePaymentCard(paymentCard);
		User updatedUser = this.entityManager.persistAndFlush(savedUser);

		assertThat(updatedUser.getUsername()).isEqualTo("test");
		assertThat(updatedUser.getPaymentCards().size()).isEqualTo(0);

	}
}
