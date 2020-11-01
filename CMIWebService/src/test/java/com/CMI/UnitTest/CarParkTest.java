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
import com.CMI.repository.CarParkRepository;
import com.CMI.service.CarParkService;


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
		carPark.setMotorcycleRate(6);
		carPark.setX(2);
		carPark.setY(2);
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
		carPark2.setMotorcycleRate(6);
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
   public void getCarParkByAddress() {
	   
	   when(repository.getCarParksByAddressContaining("CarPark Address")).thenReturn(Stream.of(carPark).collect(Collectors.toList()));
	   assertThat(service.getCarParksByAddress("CarPark Address").size()).isEqualTo(1);
   }
   
   @Test
   public void getNearbyCarParksByCoordTest() {
	   when(repository.getNearbyCarParksByCoord(2, 2, 2)).thenReturn(Stream.of(carPark).collect(Collectors.toList()));
	   assertThat(service.getNearbyCarParksByCoord(2 , 2 ,2 ).size()).isEqualTo(1);
   }
   @Test
   public void saveCarParkTest() {
	   when(repository.save(carPark)).thenReturn(carPark);
	   assertThat(service.saveCarPark(carPark)).isEqualTo(carPark);
   }
   @Test
   public void deleteCarParkTest() {
	   when(repository.findById(1)).thenReturn(Optional.of(carPark));
	   service.deleteCarPark(1);
	  verify(repository , times(1)).deleteById(1);
   }
   
   @Test
   public void updateCarParkTest() {
	   when(repository.findById(1)).thenReturn(Optional.of(carPark));
	   when(repository.save(carPark)).thenReturn(carPark);
	   carPark.setLot_available(180);
	   assertThat(service.updateCarPark(carPark).getLot_available()).isEqualTo(180);
   }
}
