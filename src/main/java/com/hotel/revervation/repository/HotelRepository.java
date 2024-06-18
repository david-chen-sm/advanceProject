package com.hotel.revervation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.revervation.dto.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer>{
	
}
