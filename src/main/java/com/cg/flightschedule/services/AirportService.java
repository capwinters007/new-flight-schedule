/********************************************************************************************************************************************************************************
	- Class Name           : AirportService
	- Author               : Capgemini
	- Creation Date        : 13-8-2020
	- Description          : This class responsible for the business logic related to airport and implements the IAirportService interface.  

********************************************************************************************************************************************************************************/

package com.cg.flightschedule.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.flightschedule.entity.Airport;
import com.cg.flightschedule.repository.IAirportRepository;

@Service
public class  AirportService  implements IAirportService{
		
		@Autowired
		private IAirportRepository  airportRepository;
		
		/*****************************************************************************************************************************************************************************
			- Method Name          : viewAirport
			- Input Parameters     : String code
			- Return type          : Optional<Airport>
			- Author               : Capgemini
			- Creation Date        : 13-8-2020
			- Description          : This method is responsible for returning airport object w.r.t. airport code by calling findById method of IAirportRepository interface.

		*****************************************************************************************************************************************************************************/
		
		@Override
		@Transactional
		public Optional<Airport> viewAirport(String code) {
	
			return airportRepository.findById(code);
		}

}
