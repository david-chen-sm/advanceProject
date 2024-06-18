package com.hotel.revervation.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateResponse {
	private boolean sucess;
	private Object DTO;
	private String message;
	
	// Constructor for success case
	public UpdateResponse(boolean sucess, Object dTO) {
		super();
		this.sucess = sucess;
		DTO = dTO;
	}
	
	// Constructor for failure case with message
	public UpdateResponse(boolean sucess, String message) {
		super();
		this.sucess = sucess;
		this.message = message;
	}
	
	
}
