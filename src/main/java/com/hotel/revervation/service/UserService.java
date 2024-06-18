package com.hotel.revervation.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hotel.revervation.dto.UpdateResponse;
import com.hotel.revervation.dto.Users;
import com.hotel.revervation.repository.UserRepository;

import io.micrometer.common.util.StringUtils;

/**
 * This project serves as a demonstration of skills, understanding, and the practical use of Spring Boot functionalities.
 * The return types in this project may not strictly adhere to industry standards. Instead, the focus is on showcasing the
 * implementation of exceptions handling, DTO objects, and ResponseEntity as return types for interaction with front-end
 * applications (e.g., Postman). More detailed Javadoc comments are provided selectively for complex functions due to time constraints.
 */

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
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
	public UpdateResponse updateUser( Users users) {
		
		// Front end will have username change, password change, or both changed.
		
		// Initialize user objects
	    Users dbUser = null;
		// Setting the update flag to false.
		boolean update = false;
		
		// Checking if the ID is correct.
		try {
			dbUser = getUserById(users.getId());
		} catch (Exception e) {
			// no user is found with the ID
			return new UpdateResponse(false, "User not found with ID: " + users.getId());
		}
		
		// User is found with ID
		
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
			Users updatedUser = userRepository.save(dbUser);
			return new UpdateResponse(true, updatedUser);
		}
		
		// when no changes were made
		return new UpdateResponse(false, "No changes were made to the user.");
	}
}
