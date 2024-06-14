package com.hotel.revervation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
 * This project is a demonstration of skills, understanding, and the use of Spring Boot functionalities.
 * Therefore, the return types might not strictly adhere to industry standards. It is intended to demonstrate
 * the use of exceptions, DTO objects, and ResponseEntity as return types to interact with the frontend
 * (e.g., Postman application).
 */

@RestController
public class RoomController {
	
	@Autowired
	RoomService roomService;
	
	@PostMapping("/room")
	public Room saveRoom(@RequestBody Room room) {
		return roomService.saveRoom(room);
	}
	
	@GetMapping("/room")
	public List<Room> getAllRooms(){
		return roomService.getAllRooms();
	}
	
	@GetMapping("/room/{id}")
	public Room getRoomById(@PathVariable int id) {
		return roomService.getRoomById(id);
	}
	
	@DeleteMapping("/room/{id}")
	public void deleteRoom(@PathVariable int id) {
		roomService.deleteRoom(id);
	}
	
	@PutMapping("/room")
	public Room updateRoom(@RequestBody Room room) {
		return roomService.updateRoom(room);
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
