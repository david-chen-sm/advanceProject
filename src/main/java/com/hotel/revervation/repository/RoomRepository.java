package com.hotel.revervation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.revervation.dto.Room;

public interface RoomRepository extends JpaRepository<Room, Integer>{
	
}
