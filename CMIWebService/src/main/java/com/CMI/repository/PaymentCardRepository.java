package com.CMI.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CMI.entity.PaymentCard;
import com.CMI.entity.User;

public interface PaymentCardRepository extends JpaRepository<PaymentCard,Integer>{
	
		List<PaymentCard> getPaymentCardsByUser(User user);
}
