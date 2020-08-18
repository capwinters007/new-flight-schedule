/********************************************************************************************************************************************************************************
	- Class Name           : FlightService
	- Author               : Capgemini
	- Creation Date        : 13-8-2020
	- Description          : This class responsible for the business logic related to flight and implements the IFlightService interface.  

********************************************************************************************************************************************************************************/

package com.cg.flightschedule.services;

import java.math.BigInteger;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.flightschedule.entity.Flight;
import com.cg.flightschedule.repository.IFlightRepository;

@Service
public class FlightService implements IFlightService{

	private static final Logger log=LoggerFactory.getLogger(FlightService.class);
	
	@Autowired
	private IFlightRepository flightRepository;
	
	/*****************************************************************************************************************************************************************************
		- Method Name          : viewFlight
		- Input Parameters     : BigInteger theFlightNumber
		- Return type          : Optional<Flight>
		- Author               : Capgemini
		- Creation Date        : 13-8-2020
		- Description          : This method is responsible for returning flight object w.r.t. flight number by calling findById method of IFlightRepository interface.

	 *****************************************************************************************************************************************************************************/
	
	@Override
	@Transactional
	public Optional<Flight> viewFlight(BigInteger theFlightNumber) {
		
		log.debug("Inside viewFlightmethod by Flight Number in FlightService class");
		
		return flightRepository.findById(theFlightNumber);
	}
	
	
}
