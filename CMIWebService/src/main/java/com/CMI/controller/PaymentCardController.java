package com.CMI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.CMI.entity.Booking;
import com.CMI.entity.PaymentCard;
import com.CMI.service.BookingService;
import com.CMI.service.PaymentCardService;
import com.CMI.service.UserService;

@RestController
public class PaymentCardController {
	@Autowired
	private PaymentCardService service;
	@Autowired
	private UserService userService;
		
	
	public PaymentCard AddPaymentCard(PaymentCard paymentCard) {
		return service.savePaymentCard(paymentCard);
	}
	
	public PaymentCard updatePaymentCard(PaymentCard paymentCard) {
		return service.updatePaymentCard(paymentCard);
	}
	
	public String DeletePaymentCard(int id) {
		return service.deletePaymentCard(id);
	}
	
	public List<PaymentCard> getPaymentCardsByUser(int UserID){ 
		return service.getPaymentCardsByUser(userService.getUserById(UserID));
		
	}
}
