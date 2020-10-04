package com.CMI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CMI.entity.PaymentCard;
import com.CMI.entity.User;
import com.CMI.repository.PaymentCardRepository;

@Service
public class PaymentCardService {
	@Autowired
	private PaymentCardRepository repository;
		
	public PaymentCard savePaymentCard(PaymentCard paymentCard) {
		return repository.save(paymentCard);
	}
	
	public PaymentCard getPaymentCardById(int id){
		return repository.findById(id).orElse(null);
	}
	public List<PaymentCard> getPaymentCardsByUser(User user){
		return repository.getPaymentCardsByUser(user);
	}
	public List<PaymentCard> getPaymentCards(){
		return repository.findAll();
	}
	
	public String deletePaymentCard(int id) {
		repository.deleteById(id);
		return "delete payment Card successfully";
	}
	public PaymentCard updatePaymentCard(PaymentCard paymentCard) {
		PaymentCard oldPC = repository.findById(paymentCard.getId()).orElse(null);
		oldPC.setCardNum(paymentCard.getCardNum());
		oldPC.setCcv(paymentCard.getCcv());
		oldPC.setExpiry_date(paymentCard.getExpiry_date());
		oldPC.setUser(paymentCard.getUser());
		return repository.save(oldPC);
	}
}
