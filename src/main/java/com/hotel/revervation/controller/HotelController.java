package com.hotel.revervation.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.revervation.dto.Hotel;
import com.hotel.revervation.dto.UpdateResponse;
import com.hotel.revervation.service.HotelService;

/**
 * This project serves as a demonstration of skills, understanding, and the practical use of Spring Boot functionalities.
 * The return types in this project may not strictly adhere to industry standards. Instead, the focus is on showcasing the
 * implementation of exceptions handling, DTO objects, and ResponseEntity as return types for interaction with front-end
 * applications (e.g., Postman). More detailed Javadoc comments are provided selectively for complex functions due to time constraints.
 */

@RestController
public class HotelController {
	
	@Autowired
	private HotelService hotelService;
	
	@PostMapping("/hotel")
	public ResponseEntity<Hotel> saveHotel(@RequestBody Hotel hotel) {
		try {
			Hotel savedHotel = hotelService.saveHotel(hotel);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedHotel);
		} catch (IllegalArgumentException e) {
			// Handles IllegalArgumentException exception thrown by hotelService.saveHotel
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/hotel")
	public ResponseEntity<List <Hotel>> getAllHotels(){
		List<Hotel> dbHotels = hotelService.getAllHotels();
		if (dbHotels.isEmpty()) {
			// No data in DB
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(dbHotels);
	}
	
	@GetMapping("/hotel/{id}")
	public ResponseEntity<Hotel> getHotelById(@PathVariable int id) {
		Optional<Hotel> optional = hotelService.getHotelById(id);
		if (optional.isEmpty()) {
			// No hotel found with the ID
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(optional.get());
	}
	
	@DeleteMapping("/hotel/{id}")
	public void deleteHotel(@PathVariable int id) {
		hotelService.deleteHotel(id);
	}
	
	@PutMapping("/hotel")
	public ResponseEntity<Object> updateHotel(@RequestBody Hotel hotel) {
		UpdateResponse updateResults = hotelService.updateHotel(hotel);
		
		if (updateResults.isSucess()) {
			// Update successfully
			return ResponseEntity.ok(updateResults.getDTO());
		}
		// Update failed
		return ResponseEntity.badRequest().body(updateResults.getMessage());
		
	}
	
}
