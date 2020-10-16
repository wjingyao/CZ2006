package com.CMI.EntityTest;

import static org.assertj.core.api.Assertions.assertThat;

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
public class PaymentCardEntityTest {
	@Autowired
	private TestEntityManager entityManager;
	
	private User user;
	private PaymentCard paymentCard;
	
	@BeforeEach
	public void setUp() {
		user = new User();
		user.setUsername("test");
		user.setPassword("password");
		user.setEmail("email@gmail.com");
		user.setFirstName("firstName");
		user.setLastName("lastName");

		paymentCard = new PaymentCard();
		paymentCard.setCardNum("1234567891345");
		paymentCard.setCcv(123);
		paymentCard.setExpiry_date("07/25");

		user.addPaymentCard(paymentCard);
	}
	
	@Test
	public void saveCard() {
		User savedUser = this.entityManager.persistAndFlush(user);
		PaymentCard savedCard = this.entityManager.persistAndFlush(paymentCard);
		assertThat(savedCard.getCardNum()).isEqualTo("1234567891345");
		assertThat(savedCard.getUser()).isEqualTo(user);
	}
	
}
