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

import com.CMI.entity.CarPark;
import com.CMI.entity.PaymentCard;
import com.CMI.entity.User;
import com.CMI.entity.Vehicle;
import com.CMI.repository.CarParkRepository;
import com.CMI.repository.UserRepository;
import com.CMI.service.CarParkService;
import com.CMI.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarParkTest {
		
	@Autowired
	private CarParkService service;
	
	@MockBean
	private CarParkRepository repository;
	
	private CarPark carPark;
	
	@BeforeEach
	public void setUp() {
		carPark = new CarPark();
		carPark.setId(1);
		carPark.setAddress("address");
		carPark.setCarkParkName("CarPark Name");
		carPark.setCarRate(7.3);
		carPark.setHeavyVehicleRate(10);
		carPark.setLot_available(199);
		carPark.setLot_type('g');
		carPark.setMotorcycleRate(6);
		carPark.setPostalCode(566663);
		carPark.setX(123.12);
		carPark.setY(123.4);
		}
	
	@Test
	public void getAllCarParkTest() {
		CarPark carPark2 = new CarPark();
		carPark2.setId(2);
		carPark2.setAddress("address2");
		carPark2.setCarkParkName("CarPark Name2");
		carPark2.setCarRate(7.3);
		carPark2.setHeavyVehicleRate(10);
		carPark2.setLot_available(199);
		carPark2.setLot_type('g');
		carPark2.setMotorcycleRate(6);
		carPark2.setPostalCode(123213);
		carPark2.setX(123.12);
		carPark2.setY(123.4);
		
		when(repository.findAll()).thenReturn(Stream.of(carPark , carPark2).collect(Collectors.toList()));
		assertThat(service.getCarParks().size()).isEqualTo(2);
	}
   @Test
   public void getCarParkByIdTest() {
	  when(repository.findById(1)).thenReturn(Optional.of(carPark));
	   assertThat(service.getCarParkById(1).getId()).isEqualTo(1);
   }
   
   
   @Test
   public void saveUserTest() {
	   when(repository.save(carPark)).thenReturn(carPark);
	   assertThat(service.saveCarPark(carPark)).isEqualTo(carPark);
   }
   @Test
   public void deleteUserTest() {
	   when(repository.findById(1)).thenReturn(Optional.of(carPark));
	   service.deleteCarPark(1);
	  verify(repository , times(1)).deleteById(1);
   }
   
   @Test
   public void updateUserTest() {
	   when(repository.findById(1)).thenReturn(Optional.of(carPark));
	   when(repository.save(carPark)).thenReturn(carPark);
	   carPark.setLot_available(180);
	   assertThat(service.updateCarPark(carPark).getLot_available()).isEqualTo(180);
   }
}
