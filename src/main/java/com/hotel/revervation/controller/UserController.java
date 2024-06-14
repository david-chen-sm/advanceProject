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

import com.hotel.revervation.dto.Users;
import com.hotel.revervation.service.UserService;

/**
 * This project is a demonstration of skills, understanding, and the use of Spring Boot functionalities.
 * Therefore, the return types might not strictly adhere to industry standards. It is intended to demonstrate
 * the use of exceptions, DTO objects, and ResponseEntity as return types to interact with the frontend
 * (e.g., Postman application).
 */

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
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
	public Users updateUser(@RequestBody Users users) {
		return userService.updateUser(users);
	}
	
	
}
