/********************************************************************************************************************************************************************************
	- Class Name           : FlightScheduleController
	- Author               : Capgemini
	- Creation Date        : 13-8-2020
	- Description          : This class consists of end points to manage the flight schedule

********************************************************************************************************************************************************************************/

package com.cg.flightschedule.controller;


import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.flightschedule.entity.Airport;
import com.cg.flightschedule.entity.FlightSchedule;
import com.cg.flightschedule.exception.FlightScheduleNotFoundException;
import com.cg.flightschedule.services.IAirportService;
import com.cg.flightschedule.services.IFlightScheduleService;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/flight")
public class FlightScheduleController {
	
	private static final Logger log=LoggerFactory.getLogger(FlightScheduleController.class);
	
	@Autowired
	IFlightScheduleService flightScheduleService;
	
	@Autowired
	IAirportService airportService;
	
	/*****************************************************************************************************************************************************************************
		- Method Name          : addFlightSchedule
		- Input Parameters     : FlightSchedule flightSchedule
		- Return type          : String
		- End point URL        : /add
		- Request Method type  : PostMapping
		- Author               : Capgemini
		- Creation Date        : 13-8-2020
		- Description          : This is responsible of calling the scheduleFlight method of FlightScheduleService method to persist the FlightSchedule into the database.

	 *****************************************************************************************************************************************************************************/
	
	@PostMapping(path="/add")
	public String addFlightSchedule(@RequestBody FlightSchedule flightSchedule)
	{
		log.debug("Inside add method in controller class");
		
		String validate=flightScheduleService.validateScheduledFlight(flightSchedule);
		
		if("valid data".equals(validate)) {
			validate=flightScheduleService.scheduleFlight(flightSchedule);
		}
		else {
			throw new FlightScheduleNotFoundException(validate);
		}
		return validate;
	}

	/*****************************************************************************************************************************************************************************
		- Method Name          : getFlightOnDate
		- Input Parameters     : String source, String destination, String date
		- Return type          : List<FlightSchedule>
		- End point URL        : /viewByAirport
		- Request Method type  : GetMapping
		- Author               : Capgemini
		- Creation Date        : 13-8-2020
		- Description          : This Method return the list of flights scheduled on a particular date between particular source and destination airports.

	*****************************************************************************************************************************************************************************/	

	@GetMapping("/viewByAirport")
	public List<FlightSchedule> getFlightOnDate(@RequestParam("source")String source,@RequestParam("destination") String destination,@RequestParam("date")String date){
		
		Airport airport1=null;
		Airport airport2=null;
		
		Optional<Airport> optAirport1=airportService.viewAirport(source);
		Optional<Airport> optAirport2=airportService.viewAirport(destination);
		
		if(optAirport1.isPresent()) 
			airport1=optAirport1.get();
		else
			throw new FlightScheduleNotFoundException("No such source airport exists");
		
		if(optAirport2.isPresent())
			airport2=optAirport2.get();
		else
			throw new FlightScheduleNotFoundException("No such destination airport exists");
		
		LocalDate date2=LocalDate.parse(date);
		
		if(date2.compareTo(LocalDate.now())<1) {
			throw new FlightScheduleNotFoundException("Date cannot be equal to present date");
		}
		
		List<FlightSchedule> flightScheduleList=flightScheduleService.viewScheduledFlights(airport1,airport2,date2);
		
		if(flightScheduleList.isEmpty()) {
			throw new FlightScheduleNotFoundException("No flights found!!");
		}
		
		return flightScheduleList;
	}
	
	/*****************************************************************************************************************************************************************************
		- Method Name          : deleteFlightSchedule
		- Input Parameters     : int id
		- Return type          : String
		- End point URL        : /deleteFlightSchedule
		- Request Method type  : GetMapping
		- Author               : Capgemini
		- Creation Date        : 13-8-2020
		- Description          : This Method first checks whether the flightSchedule of particular id exists in database then calls deleteScheduledFlight method from
	                         	 FlightScheduleService to delete the entry from the database.

	*****************************************************************************************************************************************************************************/
	
	@GetMapping("/deleteFlightSchedule")
	public String deleteFlightSchedule(@RequestParam("id") int id){
		
		log.debug("Inside delete method in controller class");
		
		Optional<FlightSchedule> flightScheduleOpt=flightScheduleService.viewScheduledFlights(id);
		
		if(!flightScheduleOpt.isPresent()) {
			throw new FlightScheduleNotFoundException("Flight Schedule not found");
		}
		
		String message=flightScheduleService.deleteScheduledFlight(id);
		
		return message;
	}
	
	/*****************************************************************************************************************************************************************************
		- Method Name          : updateFlightSchedule
		- Input Parameters     : FlightSchedule flightSchedule
		- Return type          : String
		- End point URL        : /update
		- Request Method type  : PostMapping
		- Author               : Capgemini
		- Creation Date        : 13-8-2020
		- Description          : This Method calls modifyScheduleFlight method from FlightScheduleService and updates the data in the database.

	*****************************************************************************************************************************************************************************/
	
	@PostMapping("/update")
	public String updateFlightSchedule(@RequestBody FlightSchedule flightSchedule) {
		
		log.debug("Inside update method in controller class");
		
		String validate=flightScheduleService.validateScheduledFlight(flightSchedule);
		
		if("valid data".equals(validate)) {
			validate=flightScheduleService.modifyScheduledFlight(flightSchedule);
		}
		else {
			throw new FlightScheduleNotFoundException(validate);
		}
		return validate;
		
	/*****************************************************************************************************************************************************************************
		- Method Name          : getFlightScheduleById
		- Input Parameters     : int id
		- Return type          : FlightSchedule
		- End point URL        : /viewById
		- Request Method type  : GetMapping
		- Author               : Capgemini
		- Creation Date        : 13-8-2020
		- Description          : This Method calls viewScheduledFlights from FlightScheduleService to retrieve flight schedule w.r.t flight schedule ID.

	*****************************************************************************************************************************************************************************/
		
	}
	@GetMapping("/viewById")
	public FlightSchedule getFlightScheduleById(@RequestParam("id")int id){
		
		log.debug("Inside getFlightScheduleById in controller class");
		
		Optional<FlightSchedule> flightScheduleOpt=flightScheduleService.viewScheduledFlights(id);
		
		if(!flightScheduleOpt.isPresent()) {
			throw new FlightScheduleNotFoundException("Flight schedule not found");
		}
		
		return flightScheduleOpt.get();
	}
	
	/*****************************************************************************************************************************************************************************
		- Method Name          : getAllFlightSchedule
		- Input Parameters     : N/A
		- Return type          : List<FlightSchedule>
		- End point URL        : /viewAll
		- Request Method type  : GetMapping
		- Author               : Capgemini
		- Creation Date        : 13-8-2020
		- Description          : This Method calls viewScheduledFlights from FlightScheduleService to retrieve all the flight schedules in the database.

	*****************************************************************************************************************************************************************************/
	
	@GetMapping("/viewAll")
	public List<FlightSchedule> getAllFlightSchedule(){
		return flightScheduleService.viewScheduledFlights();
	}
	
}
