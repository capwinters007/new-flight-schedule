/********************************************************************************************************************************************************************************
	- Interface Name       : IFlightScheduleService
	- Author               : Capgemini
	- Creation Date        : 13-8-2020
	- Description          : This interface consist of unimplemented methods which are overridden in FlightScheduleService class.  

********************************************************************************************************************************************************************************/

package com.cg.flightschedule.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.cg.flightschedule.entity.Airport;
import com.cg.flightschedule.entity.FlightSchedule;


public interface IFlightScheduleService {
	
	public String scheduleFlight(FlightSchedule flightSchedule);
	
	public Optional<FlightSchedule> viewScheduledFlights(int id);
	
	public List<FlightSchedule> viewScheduledFlights(Airport arrival,Airport destination,LocalDate date);
	
	public String deleteScheduledFlight(int id);
	
	public String modifyScheduledFlight(FlightSchedule flightSchedule);
	
	public List<FlightSchedule> viewScheduledFlights();
	
	public String validateScheduledFlight(FlightSchedule flightSchedule);

}
