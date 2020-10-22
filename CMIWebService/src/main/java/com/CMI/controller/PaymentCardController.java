package com.CMI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CMI.entity.PaymentCard;
import com.CMI.service.PaymentCardService;
import com.CMI.service.UserService;

@RestController
public class PaymentCardController {
	
	@Autowired
	private PaymentCardService service;
	@Autowired
	private UserService userService;
		
	@PostMapping("api/paymentCards/create")
	public PaymentCard addPaymentCard(PaymentCard paymentCard) {
		return service.savePaymentCard(paymentCard);
	}
	@PutMapping("api/paymentCards/{id}")
	public PaymentCard updatePaymentCard(@PathVariable int id,PaymentCard paymentCard) {
		return service.updatePaymentCard(paymentCard);
	}
	@DeleteMapping("api/paymentCards/{id}")
	public String deletePaymentCard(@PathVariable int id) {
		return service.deletePaymentCard(id);
	}
	
	@GetMapping("api/paymentCards/")
	public List<PaymentCard> getPaymentCardsByUser(@RequestParam int UserID){ 
		return service.getPaymentCardsByUser(userService.getUserById(UserID));
		
	}
}
