package com.hotel.revervation.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hotel.revervation.dto.Hotel;
import com.hotel.revervation.repository.HotelRepository;

import io.micrometer.common.util.StringUtils;

/**
 * This project is a demonstration of skills, understanding, and the use of Spring Boot functionalities.
 * Therefore, the return types might not strictly adhere to industry standards. It is intended to demonstrate
 * the use of exceptions, DTO objects, and ResponseEntity as return types to interact with the frontend
 * (e.g., Postman application).
 */

@Service
public class HotelService {
	
	@Autowired
	HotelRepository hotelRepository;
	
	// Save Hotel
	public Hotel saveHotel(Hotel hotel) {
		return hotelRepository.save(hotel);
	}
	
	// Retrieve all hotels
	public List<Hotel> getAllHotels(){
		return hotelRepository.findAll();
	}
	
	// Retrieve a hotel by ID
	public Hotel getHotelById (int id) {
		Optional<Hotel> optional = hotelRepository.findById(id);
		return optional.orElseThrow(()-> new NoSuchElementException("Hotel not found with ID: " + id));
	}
	
	// Delete hotel by ID
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteHotel(int id) {
		hotelRepository.deleteById(id);
	}
	
	// Update hotel by ID
	public Hotel updateHotel( Hotel hotel) {
		// Front end will have name change, address change, city change, country change, or any combinations.
		// Setting the update flag to false.
		boolean update = false;
		
		// Check if the ID is correct
		Hotel dbHotel = getHotelById(hotel.getId());
		if ( dbHotel == null ) {
			// No hotel with the argument ID was found.
			return dbHotel;
		}
		
		// update name
		if ( StringUtils.isNotBlank( hotel.getName())) {
			dbHotel.setName(hotel.getName());
			update = true;
		}
		
		// update address
		if ( StringUtils.isNotBlank( hotel.getAddress())) {
			dbHotel.setAddress(hotel.getAddress());
			update = true;
		}
		
		// update city
		if ( StringUtils.isNotBlank( hotel.getCity())) {
			dbHotel.setCity(hotel.getCity());
			update = true;
		}
		
		// update country
		if ( StringUtils.isNotBlank( hotel.getCountry())) {
			dbHotel.setCountry(hotel.getCountry());
			update = true;
		}
		
		// If the flag is true then perform the actual update.
		if (update) {
			return hotelRepository.save(dbHotel);
		}
		
		// return the hotel with the ID and no changes
		return dbHotel;
	}
	
}
