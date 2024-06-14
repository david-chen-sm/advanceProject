package com.hotel.revervation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.revervation.dto.Hotel;
import com.hotel.revervation.service.HotelService;

/**
 * This project is a demonstration of skills, understanding, and the use of Spring Boot functionalities.
 * Therefore, the return types might not strictly adhere to industry standards. It is intended to demonstrate
 * the use of exceptions, DTO objects, and ResponseEntity as return types to interact with the frontend
 * (e.g., Postman application).
 */

@RestController
public class HotelController {
	
	@Autowired
	HotelService hotelService;
	
	@PostMapping("/hotel")
	public Hotel saveHotel(@RequestBody Hotel hotel) {
		return hotelService.saveHotel(hotel);
	}
	
	@GetMapping("/hotel")
	public List<Hotel> getAllHotels(){
		return hotelService.getAllHotels();
	}
	
	@GetMapping("/hotel/{id}")
	public Hotel getHotelById(@PathVariable int id) {
		return hotelService.getHotelById(id);
	}
	
	@DeleteMapping("/hotel/{id}")
	public void deleteHotel(@PathVariable int id) {
		hotelService.deleteHotel(id);
	}
	
	@PutMapping("/hotel")
	public Hotel updateHotel(@RequestBody Hotel hotel) {
		return hotelService.updateHotel(hotel);
	}
	
}
