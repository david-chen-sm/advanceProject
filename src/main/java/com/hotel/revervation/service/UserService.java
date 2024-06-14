package com.hotel.revervation.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hotel.revervation.dto.Users;
import com.hotel.revervation.repository.UserRepository;

import io.micrometer.common.util.StringUtils;

/**
 * This project is a demonstration of skills, understanding, and the use of Spring Boot functionalities.
 * Therefore, the return types might not strictly adhere to industry standards. It is intended to demonstrate
 * the use of exceptions, DTO objects, and ResponseEntity as return types to interact with the frontend
 * (e.g., Postman application).
 */

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	// Save User
	public Users saveUser(Users user){
		return userRepository.save(user);
	}
	
	// Retrieve all users
	public List<Users> getAllUsers(){
		return userRepository.findAll();
	}
	
	// Retrieve user by ID
	public Users getUserById (int id){
		Optional<Users> optional = userRepository.findById(id);
		return optional.orElseThrow(()-> new NoSuchElementException("User not found with ID: " + id));
	}
	
	// Delete user by ID
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteUser(int id){
		userRepository.deleteById(id);
	}
	
	// Update user by ID
	public Users updateUser( Users users) {
		
		// Front end will have username change, password change, or both changed.
		// Setting the update flag to false.
		boolean update = false;
		
		// Checking if the ID is correct.
		Users dbUser = getUserById(users.getId());
		if (dbUser == null) {
			return dbUser;
		}
		
		// Update the username
		if ( StringUtils.isNotBlank( users.getUsername())) {
			// modify user username
			dbUser.setUsername(users.getUsername());
			update = true;
		}
		
		// Update the password
		if ( StringUtils.isNotBlank( users.getPassword())) {
			// modify user password
			dbUser.setPassword(users.getPassword());
			update = true;
		}
		
		// If the flag is true perform the actual update.
		if (update) {
			return userRepository.save(dbUser);
		}
		
		// return the user with the ID with no changes
		return dbUser;
	}
}
