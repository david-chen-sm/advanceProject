package com.hotel.revervation.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

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

import com.hotel.revervation.dto.Room;
import com.hotel.revervation.service.RoomService;

/**
 * This project serves as a demonstration of skills, understanding, and the practical use of Spring Boot functionalities.
 * The return types in this project may not strictly adhere to industry standards. Instead, the focus is on showcasing the
 * implementation of exceptions handling, DTO objects, and ResponseEntity as return types for interaction with front-end
 * applications (e.g., Postman). More detailed Javadoc comments are provided selectively for complex functions due to time constraints.
 */

@RestController
public class RoomController {
	
	@Autowired
	private RoomService roomService;
	
	@PostMapping("/room")
	public ResponseEntity<Room> saveRoom(@RequestBody Room room) {
		Room savedRoom = roomService.saveRoom(room);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
	}
	
	@GetMapping("/room")
	public ResponseEntity<List<Room>> getAllRooms(){
		List<Room> dbRooms = roomService.getAllRooms(); 
		if ( dbRooms.isEmpty() ) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(dbRooms);
	}
	
	@GetMapping("/room/{id}")
	public ResponseEntity<Room> getRoomById(@PathVariable int id) {
		Room dbRoom = null;
		try {
			dbRoom = roomService.getRoomById(id);
		} catch (NoSuchElementException e) {
			// No room is found with roomId
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(dbRoom) ;
	}
	
	@DeleteMapping("/room/{id}")
	public void deleteRoom(@PathVariable int id) {
		roomService.deleteRoom(id);
	}
	
	@PutMapping("/room")
	public ResponseEntity<Room> updateRoom(@RequestBody Room room) {
		Room dbRoom = null;
		try {
			dbRoom = roomService.updateRoom(room);
		} catch (NoSuchElementException e) {
			// No room is found with roomId
			return ResponseEntity.notFound().build();
		}
		
		if (Objects.isNull(dbRoom)) {
			// no update was made
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(dbRoom);
	}
	
	@PostMapping("/room/{userId}/{roomId}")
	public ResponseEntity<String> reserveRoom(@PathVariable int userId,
			@PathVariable int roomId){
		return roomService.reserveRoom(userId, roomId);
	}
	
	@PostMapping("/room/{id}")
	public ResponseEntity<String> makeRoomAvailable(@PathVariable int id){
		return roomService.makeRoomAvailable(id);
	}
}
