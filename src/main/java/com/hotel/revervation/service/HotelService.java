package com.hotel.revervation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hotel.revervation.dto.Hotel;
import com.hotel.revervation.dto.UpdateResponse;
import com.hotel.revervation.repository.HotelRepository;

import io.micrometer.common.util.StringUtils;

/**
 * This project serves as a demonstration of skills, understanding, and the practical use of Spring Boot functionalities.
 * The return types in this project may not strictly adhere to industry standards. Instead, the focus is on showcasing the
 * implementation of exceptions handling, DTO objects, and ResponseEntity as return types for interaction with front-end
 * applications (e.g., Postman). More detailed Javadoc comments are provided selectively for complex functions due to time constraints.
 */

@Service
public class HotelService {
	
	@Autowired
	private HotelRepository hotelRepository;
	
	// Save Hotel
	public Hotel saveHotel(Hotel hotel) {
		return hotelRepository.save(hotel);
	}
	
	// Retrieve all hotels
	public List<Hotel> getAllHotels(){
		return hotelRepository.findAll();
	}
	
	// Retrieve a hotel by ID
	public Optional<Hotel> getHotelById (int id) {
		// Optional<Hotel> optional = hotelRepository.findById(id);
		return hotelRepository.findById(id);
	}
	
	// Delete hotel by ID
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteHotel(int id) {
		hotelRepository.deleteById(id);
	}
	
	// Update hotel by ID
	public UpdateResponse updateHotel( Hotel hotel) {
		// Front end will have name change, address change, city change, country change, or any combinations.
		// Setting the update flag to false.
		boolean update = false;
		
		// Check if the ID is correct
		Optional<Hotel> optional = getHotelById(hotel.getId());
		if ( optional.isEmpty() ) {
			// No hotel with the argument ID was found.
			return new UpdateResponse(false, "Hotel not found with ID: " + hotel.getId());
		}
		
		// Hotel found with ID
		Hotel dbHotel = optional.get();
		
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
			Hotel updatedHotel = hotelRepository.save(dbHotel);
			return new UpdateResponse(true, updatedHotel);
		}
		
		// return the hotel with the ID and no changes
		return new UpdateResponse(false, "No changes were made to the room.");
	}
	
}
