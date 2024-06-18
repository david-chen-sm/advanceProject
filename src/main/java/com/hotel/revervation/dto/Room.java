package com.hotel.revervation.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Room {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "room_id")
	@SequenceGenerator( name = "room_id", initialValue = 200, allocationSize = 1)
	private int id;
	private String type;
	
	// Using wrapper class for null handling.
	@Column(name = "price_pernight")
	private Double pricePernight;
	// Set default value of availability to true
    @Column(columnDefinition = "boolean default true")
	private boolean availability;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users reservedBy;
}
