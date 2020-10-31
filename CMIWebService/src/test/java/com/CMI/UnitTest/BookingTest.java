package com.CMI.UnitTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
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

import com.CMI.entity.Booking;
import com.CMI.entity.CarPark;
import com.CMI.entity.PaymentCard;
import com.CMI.entity.User;
import com.CMI.entity.Vehicle;
import com.CMI.repository.BookingRepository;
import com.CMI.service.BookingService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingTest {
	
	@Autowired
	private BookingService service;
	
	@MockBean
	private BookingRepository repository;
	
	private User user;
	private Vehicle vehicle;
	private PaymentCard paymentCard;
	private Booking booking;
	private CarPark carPark;
	
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
		
		carPark = new CarPark();
		carPark.setId(1);
		carPark.setAddress("address");
		carPark.setCarkParkName("CarPark Name");
		carPark.setCarRate(7.3);
		carPark.setHeavyVehicleRate(10);
		carPark.setLot_available(199);
		carPark.setMotorcycleRate(6);
		carPark.setX(123.12);
		carPark.setY(123.4);
		
		booking = new Booking();
		booking.setId(1);
		booking.setActive(false);
		booking.setUser(user);
		booking.setVehicle(vehicle);
		booking.setCarPark(carPark);
		LocalDateTime lt = LocalDateTime.now();
		booking.setBookingDateTime(lt);
	}
	
	@Test
	public void getAllBookingsTest() {
		Booking booking2 = booking;
		booking2.setId(2);
		booking2.setActive(true);
		when(repository.findAll()).thenReturn(Stream.of(
				booking,booking2).collect(Collectors.toList()));
		assertThat(service.getBookings().size()).isEqualTo(2);
	}
	
   @Test
   public void getBookingByIdTest() {
	  when(repository.findById(1)).thenReturn(Optional.of(booking));
	   assertThat(service.getBookingById(1).getId()).isEqualTo(1);
   }
   
   @Test
   public void getBookingsByUserTest() {
	   Booking booking2 = booking;
		booking2.setId(2);
		booking2.setActive(true);
		when(repository.getBookingsByUser(user)).thenReturn(Stream.of(
				booking,booking2).collect(Collectors.toList()));
	   assertThat(service.getBookingsByUser(user).size()).isEqualTo(2);
   }
   
   @Test
   public void saveBookingTest() {
	   when(repository.save(booking)).thenReturn(booking);
	   assertThat(service.saveBooking(booking)).isEqualTo(booking);
   }
   @Test
   public void deleteBookingTest() {
	   when(repository.findById(1)).thenReturn(Optional.of(booking));
	   service.deleteBooking(1);
	  verify(repository , times(1)).deleteById(1);
   }
   
   @Test
   public void updateBookingTest() {
	   when(repository.findById(1)).thenReturn(Optional.of(booking));
	   when(repository.save(booking)).thenReturn(booking);
	   booking.setActive(true);
	   assertThat(service.updateBooking(booking).isActive()).isEqualTo(true);
   }
}
