package com.cg.flightschedule.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExHandler {
	
	 static final Logger log=LoggerFactory.getLogger(GlobalExHandler.class);

	
	@ExceptionHandler({FlightScheduleNotFoundException.class})
	public ResponseEntity<String> handleException(Exception ex) {
		return new ResponseEntity<>("Flight Schedule not found",HttpStatus.NOT_FOUND);
	}

}
