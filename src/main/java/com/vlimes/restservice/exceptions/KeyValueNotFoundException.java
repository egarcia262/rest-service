package com.vlimes.restservice.exceptions;

public class KeyValueNotFoundException extends RuntimeException {
	
	public KeyValueNotFoundException(Long id) {
		super("Could not find keyvalue " + id);
	}
}
