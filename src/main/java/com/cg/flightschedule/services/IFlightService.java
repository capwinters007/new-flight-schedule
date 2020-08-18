/********************************************************************************************************************************************************************************
	- Interface Name       : IFlightService
	- Author               : Capgemini
	- Creation Date        : 13-8-2020
	- Description          : This interface consist of unimplemented methods which are overridden in FlightService class.  

********************************************************************************************************************************************************************************/

package com.cg.flightschedule.services;

import java.math.BigInteger;
import java.util.Optional;

import com.cg.flightschedule.entity.Flight;

public interface IFlightService {

	public Optional<Flight> viewFlight(BigInteger id);
	
}
