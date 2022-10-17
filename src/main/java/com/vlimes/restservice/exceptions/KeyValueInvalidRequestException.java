package com.vlimes.restservice.exceptions;

public class KeyValueInvalidRequestException extends RuntimeException {
	public KeyValueInvalidRequestException(String exceptionText) {
		super(exceptionText);
	}
}
