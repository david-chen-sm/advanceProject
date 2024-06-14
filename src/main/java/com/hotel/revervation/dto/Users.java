package com.hotel.revervation.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_id")
	@SequenceGenerator( name = "user_id", initialValue = 300, allocationSize = 1)
	private int id;
	private String username;
	private String password;
}
