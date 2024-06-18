package com.hotel.revervation.controller;

import java.util.List;

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

import com.hotel.revervation.dto.UpdateResponse;
import com.hotel.revervation.dto.Users;
import com.hotel.revervation.service.UserService;

/**
 * This project serves as a demonstration of skills, understanding, and the practical use of Spring Boot functionalities.
 * The return types in this project may not strictly adhere to industry standards. Instead, the focus is on showcasing the
 * implementation of exceptions handling, DTO objects, and ResponseEntity as return types for interaction with front-end
 * applications (e.g., Postman). More detailed Javadoc comments are provided selectively for complex functions due to time constraints.
 */

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/user")
	public Users saveUser(@RequestBody Users user) {
		return userService.saveUser(user);
	}
	
	@GetMapping("/user")
	public List<Users> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@GetMapping("/user/{id}")
	public Users getUserById(@PathVariable int id) {
		return userService.getUserById(id);
	}
	
	@DeleteMapping("/user/{id}")
	public void deleteUser(@PathVariable int id) {
		userService.deleteUser(id);
	}
	
	@PutMapping("/user")
	public ResponseEntity<Object> updateUser(@RequestBody Users users) {
		UpdateResponse updateResult = null;
		try {
			updateResult = userService.updateUser(users);
		} catch (Exception e) {
			// Unexpected exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		if (updateResult.isSucess()) {
			// Update successfully
			return ResponseEntity.ok(updateResult.getDTO());
		}
		// Update failed
		return ResponseEntity.badRequest().body(updateResult.getMessage());
	}
	
	
}
