package com.hotel.revervation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.revervation.dto.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {
	
}
