/********************************************************************************************************************************************************************************
	- Interface Name       : IAirportService
	- Author               : Capgemini
	- Creation Date        : 13-8-2020
	- Description          : This interface consist of unimplemented methods which are overridden in AirportService class.  

********************************************************************************************************************************************************************************/

package com.cg.flightschedule.services;

import java.util.Optional;

import com.cg.flightschedule.entity.Airport;

public interface IAirportService {
	
	public Optional<Airport> viewAirport(String code);

}
