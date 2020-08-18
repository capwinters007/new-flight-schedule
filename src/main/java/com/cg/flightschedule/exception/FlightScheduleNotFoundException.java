/********************************************************************************************************************************************************************************
	- Class Name           : FlightScheduleNotFoundException
	- Author               : Capgemini
	- Creation Date        : 13-8-2020
	- Description          : This class is the custom exception class to raise exceptions related to flight schedule. It extends Exception class.  

********************************************************************************************************************************************************************************/

package com.cg.flightschedule.exception;

public class FlightScheduleNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -212242984426439241L;
	
	private String message;
	
	public FlightScheduleNotFoundException(String message) {
		this.message=message;
	}
	
	@Override
	public String toString() {
		return message;
	}

}
