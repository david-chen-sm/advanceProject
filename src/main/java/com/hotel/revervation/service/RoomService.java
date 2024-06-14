package com.hotel.revervation.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hotel.revervation.dto.Room;
import com.hotel.revervation.dto.Users;
import com.hotel.revervation.repository.RoomRepository;

import io.micrometer.common.util.StringUtils;

/**
 * This project is a demonstration of skills, understanding, and the use of Spring Boot functionalities.
 * Therefore, the return types might not strictly adhere to industry standards. It is intended to demonstrate
 * the use of exceptions, DTO objects, and ResponseEntity as return types to interact with the frontend
 * (e.g., Postman application).
 */

@Service
public class RoomService {
	
	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	UserService userService;
	
	// Save Room
	public Room saveRoom (Room room) {
		return roomRepository.save(room);
	}
	
	// Retrieve all rooms
	public List<Room> getAllRooms(){
		return roomRepository.findAll();
	}
	
	// Retrieve room by ID
	public Room getRoomById(int id) {
		Optional<Room> optional = roomRepository.findById(id);
		return optional.orElseThrow(()-> new NoSuchElementException("Room not found with ID: " + id));
	}
	
	// Delete room by ID
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteRoom(int id) {
		roomRepository.deleteById(id);
	}
	
	// Update room
	public Room updateRoom(Room room) {
		// Front end will have PricePernight change, type change, or both changed.
		// Setting the update flag to false.
		boolean update = false;
		
		// Check if the ID is correct
		Room dbRoom = getRoomById(room.getId());
		if ( Objects.isNull(dbRoom)) {
			// No room with the argument ID was found.
			return dbRoom;
		}
		
		// update PricePernight
		if (room.getPricePernight() != null && room.getPricePernight() >= 0 ) {
			dbRoom.setPricePernight(room.getPricePernight());
			update = true;
		}
		
		// update type
		if ( StringUtils.isNotBlank(room.getType())) {
			dbRoom.setType(room.getType());
			update = true;
		}
		
		// If the flag is true then perform the actual update.
		if (update) {
			return roomRepository.save(dbRoom);
		}
		
		// return the room with the ID and no changes
		return dbRoom;
	}
	
	// Check availability of the room
	public boolean checkAvailabilityOfRoom (int id) {
		Room dbRoom = getRoomById(id);
		return dbRoom.isAvailability();
	}
	
	// create Error Response 
	private ResponseEntity<String> createErrorResponse(HttpStatus status, String message) {
	    return ResponseEntity.status(status).body(message);
	}
	
	// Give response when room is unavailable.
	public ResponseEntity<String> roomCurrentlyUnavailable (int roomId){
		return createErrorResponse(HttpStatus.CONFLICT, 
				"Room ID: "+ roomId + " is currently unavailable.");
	}
	
	public ResponseEntity<String> reserveRoom(int userId, int roomId){
		// Null or empty values will be tackled in getRoomById or getUserById method
		
		// Getting the room info with the id
		Room dbRoom = getRoomById(roomId);
		// Getting the User info with the id
		Users dbUsers = userService.getUserById(userId);
		
		// Check if the room ID or User ID is correct
		if ( Objects.isNull(dbRoom) || Objects.isNull(dbUsers)) {
			// either room or user id are not found or both
			return createErrorResponse(HttpStatus.NOT_FOUND, 
					"Room ID: "+roomId+" or User ID: "+userId+" not found.");
		}
		
		// Check if the room is available.
		if ( !dbRoom.isAvailability()) {
			// room is currently unavailable
			return roomCurrentlyUnavailable(roomId);
		}
		
		// passed all checking points, will reserve the room for the user
		dbRoom.setAvailability(false);
		dbRoom.setReservedBy(dbUsers);
		roomRepository.save(dbRoom);
		return ResponseEntity.ok("Room reserved successfully");
	}
	
	public ResponseEntity<String> makeRoomAvailable(int id){
		// This method is used when cancellation or after the customer has checked out
		
		// Getting the room info with the id
		Room dbRoom = getRoomById(id);
		
		// Check if the room ID is correct
		if ( Objects.isNull(dbRoom) ) {
			// room id is not found
			return createErrorResponse(HttpStatus.NOT_FOUND,
					"Room ID: "+id+" not found.");
		}
		
		// Check if the room is available
		if ( dbRoom.isAvailability()) {
			// room is already available
			return createErrorResponse(HttpStatus.CONFLICT,
					"Room ID: "+id+" is already available.");
		}
		
		// passed all checking points, making the room available
		dbRoom.setReservedBy(null);
		dbRoom.setAvailability(true);
		roomRepository.save(dbRoom);
		return ResponseEntity.ok("Room is now available.");
	}
	
}
