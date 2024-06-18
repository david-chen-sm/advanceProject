package com.hotel.revervation.service;

import java.util.List;
import java.util.NoSuchElementException;
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
 * This project serves as a demonstration of skills, understanding, and the practical use of Spring Boot functionalities.
 * The return types in this project may not strictly adhere to industry standards. Instead, the focus is on showcasing the
 * implementation of exceptions handling, DTO objects, and ResponseEntity as return types for interaction with front-end
 * applications (e.g., Postman). More detailed Javadoc comments are provided selectively for complex functions due to time constraints.
 */

@Service
public class RoomService {
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private UserService userService;
	
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
		/**
	     * Retrieves a room from the database based on the provided room ID.
	     * 
	     * Args:
	     *   int id: Id of the room to be retrieved.
	     * 
	     * Returns:
	     *   Room: Room DTO object if it was found successfully with the provided ID.
	     * 
	     * Throws:
	     *   NoSuchElementException: If no room is found with the provided ID.
	     */
		
		// Retrieve room with id
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
		/**
	     * Method to update a room. Front-end may change PricePerNight, room type, or both.
	     * 
	     * Args:
	     *   room (Room): DTO object containing ID and updated fields (PricePerNight, type).
	     * 
	     * Returns:
	     *   Room: Updated DTO object when the update is successful.
	     *   null: If no valid data is provided for the update.
	     * 
	     * Throws:
	     *   NoSuchElementException: If no room is found with the provided ID.
	     */

		// Setting the update flag to false.
		boolean update = false;
		
		// Check if the ID is correct
		// NoSuchElementException exception will be thrown if room is not found with ID
		Room dbRoom = getRoomById(room.getId());
				
		// Room found with the ID
		
		// Update PricePerNight if valid value provided
		if (room.getPricePernight() != null && room.getPricePernight() >= 0 ) {
			dbRoom.setPricePernight(room.getPricePernight());
			update = true;
		}
		
		// Update room type if provided
		if ( StringUtils.isNotBlank(room.getType())) {
			dbRoom.setType(room.getType());
			update = true;
		}
		
		// If the flag is true then perform the actual update.
		if (update) {
			return roomRepository.save(dbRoom);
		}
		
		// Return null when no valid data is provided for update.
		return null;
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
		/**
		 * Method to reserve a room for a specified user based on user and room IDs.
		 * 
		 * Args:
		 *   int userId: ID of the user requesting the room reservation.
		 *   int roomId: ID of the room to be reserved.
		 * 
		 * Returns:
		 *   ResponseEntity<String>: ResponseEntity indicating the success or failure of the room reservation.
		 *     - HttpStatus.NOT_FOUND if the room or user ID is not found.
		 *     - ResponseEntity returned by roomCurrentlyUnavailable() if the room is currently unavailable.
		 *     - ResponseEntity indicating successful room reservation if all checks pass.
		 * 
		 * Throws:
		 *   None.
		 */
		// Null or empty values will be tackled in getRoomById or getUserById method
		
		// Initialize room and user objects
	    Room dbRoom = null;
	    Users dbUsers = null;
		
		// Getting the room info with the id
		try {
			dbRoom = getRoomById(roomId);
		} catch (NoSuchElementException e) {
			// No room is found with roomId
			return createErrorResponse(HttpStatus.NOT_FOUND, 
					"Room ID: "+roomId+" not found.");
		}
		
		// Getting the User info with the id
		try {
			dbUsers = userService.getUserById(userId);
		} catch (NoSuchElementException e) {
			// No user is found with userId
			return createErrorResponse(HttpStatus.NOT_FOUND, 
					"User ID: "+userId+" not found.");
		}
		
		// Check if the room is available.
		if ( !dbRoom.isAvailability()) {
			// room is currently unavailable
			return roomCurrentlyUnavailable(roomId);
		}
		
		// Passed all checking points, will reserve the room for the user
		dbRoom.setAvailability(false);
		dbRoom.setReservedBy(dbUsers);
		roomRepository.save(dbRoom);
		return ResponseEntity.ok("Room reserved successfully");
	}
	
	public ResponseEntity<String> makeRoomAvailable(int id){
		// This method is used when cancellation or after the customer has checked out
		
		// Initialize room object
		Room dbRoom = null;
		
		// Getting the room info with the id
		try {
			dbRoom = getRoomById(id);
		} catch (NoSuchElementException e) {
			// No room is found with roomId
			return createErrorResponse(HttpStatus.NOT_FOUND, 
					"Room ID: "+ id +" not found.");
		}
		
		// Check if the room is available
		if ( dbRoom.isAvailability()) {
			// room is already available
			return createErrorResponse(HttpStatus.CONFLICT,
					"Room ID: "+ id +" is already available.");
		}
		
		// passed all checking points, making the room available
		dbRoom.setReservedBy(null);
		dbRoom.setAvailability(true);
		roomRepository.save(dbRoom);
		return ResponseEntity.ok("Room is now available.");
	}
	
}
