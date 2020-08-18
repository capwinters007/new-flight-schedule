/********************************************************************************************************************************************************************************
	- Class Name           : GlobalExHandler
	- Author               : Capgemini
	- Creation Date        : 13-8-2020
	- Description          : This class responsible for all handling of all the custom exception explicitly thrown.  

********************************************************************************************************************************************************************************/

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

	/*****************************************************************************************************************************************************************************
		- Method Name          : handleException
		- Input Parameters     : Custom exception class
		- Return type          : ResponseEntity<String>
		- Author               : Capgemini
		- Creation Date        : 13-8-2020
		- Description          : This method is responsible for handling all exceptions that are defined in ExceptionHandler annotation parameter.

	*****************************************************************************************************************************************************************************/
	
	@ExceptionHandler({FlightScheduleNotFoundException.class})
	public ResponseEntity<String> handleException(RuntimeException ex) {
		return new ResponseEntity<>(ex.toString(),HttpStatus.NOT_FOUND);
	}

}
