package com.vlimes.restservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class KeyValueInvalidRequestAdvice {
	@ResponseBody
	@ExceptionHandler(KeyValueInvalidRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String keyValueNotFoundHandler(KeyValueInvalidRequestException ex) {
		return ex.getMessage();
	}
}
