/********************************************************************************************************************************************************************************
	- Class Name           : FlightScheduleService
	- Author               : Capgemini
	- Creation Date        : 13-8-2020
	- Description          : This class responsible for all the business logic related to flight Schedule and implements IFlightScheduleService interface.  

********************************************************************************************************************************************************************************/

package com.cg.flightschedule.services;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.flightschedule.entity.Airport;
import com.cg.flightschedule.entity.Flight;
import com.cg.flightschedule.entity.FlightSchedule;
import com.cg.flightschedule.entity.Schedule;
import com.cg.flightschedule.repository.IAirportRepository;
import com.cg.flightschedule.repository.IFlightRepository;
import com.cg.flightschedule.repository.IFlightScheduleRepository;
import com.cg.flightschedule.repository.IScheduleRepository;

@Service
public class FlightScheduleService implements IFlightScheduleService{
	
	private static final Logger log=LoggerFactory.getLogger(FlightScheduleService.class);
	
	@Autowired
	IFlightScheduleRepository flightScheduleRepository;
	
	@Autowired
	IFlightRepository flightRepository;
	
	@Autowired
	IScheduleRepository scheduleRepository;
	
	@Autowired
	IAirportRepository airportReposidtory;
	
	/*****************************************************************************************************************************************************************************
		- Method Name          : scheduleFlight
		- Input Parameters     : FlightSchedule flightSchedule
		- Return type          : void
		- Author               : Capgemini
		- Creation Date        : 13-8-2020
		- Description          : This method is responsible for adding data into database by calling save method of IFlightScheduleRepository interface.

	*****************************************************************************************************************************************************************************/
	
	@Override
	@Transactional
	public void scheduleFlight(FlightSchedule flightSchedule) {
		
		log.debug("Inside scheduleFlight function");
		
		Optional<Flight> flightOpt=flightRepository.findById(flightSchedule.getFlight().getFlightNumber());
		if(flightOpt.isPresent()) {
		flightSchedule.setAvailableSeats(flightOpt.get().getSeatNumber());
		scheduleRepository.save(flightSchedule.getSchedule());
		
		flightScheduleRepository.save(flightSchedule);
		}
	}
	
	/*****************************************************************************************************************************************************************************
		- Method Name          : viewScheduledFlights
		- Input Parameters     : int id
		- Return type          : Optional<FlightSchedule>
		- Author               : Capgemini
		- Creation Date        : 13-8-2020
		- Description          : This method is responsible for returning flight schedule w.r.t. flight schedule ID by calling findById method of IFlightScheduleRepository interface.

	 *****************************************************************************************************************************************************************************/
	
	@Override
	@Transactional
	public Optional<FlightSchedule> viewScheduledFlights(int id){
		
		log.debug("Inside viewScheduledFlights by id function in FlightScheduleController");
		
		return flightScheduleRepository.findById(id);
	}

	/*****************************************************************************************************************************************************************************
		- Method Name          : viewScheduledFlights
		- Input Parameters     : Airport arrival, Airport destination, LocalDate date
		- Return type          : List<FlightSchedule>
		- Author               : Capgemini
		- Creation Date        : 13-8-2020
		- Description          : This method is responsible for returning scheduled flights on a given date between given source and destination airport.

	 *****************************************************************************************************************************************************************************/
	
	@Override
	@Transactional
	public List<FlightSchedule> viewScheduledFlights(Airport arrival, Airport destination, LocalDate date) {
		
		log.debug("Inside viewScheduledFlights by parameters function in FlightSchedule Service");
		
		List<FlightSchedule> flightScheduleList=flightScheduleRepository.getFlightScheduleByAirport(arrival, destination);
		List<FlightSchedule> flightsOnScheduleList=new ArrayList<>();
	
		
		for (FlightSchedule flightSchedule : flightScheduleList) {
			Schedule schedule=flightSchedule.getSchedule();
			if(schedule.getDepartureTime().toLocalDate().equals(date)) {
				flightsOnScheduleList.add(flightSchedule);
			}
		}
		
		return flightsOnScheduleList;
		
				
	}
	
	/*****************************************************************************************************************************************************************************
		- Method Name          : deleteScheduledFlights
		- Input Parameters     : int id
		- Return type          : void
		- Author               : Capgemini
		- Creation Date        : 13-8-2020
		- Description          : This method is responsible for deleting flight schedule w.r.t. ID by calling deleteById method of IFlightScheduleRepository interface.

	*****************************************************************************************************************************************************************************/
	
	@Override
	@Transactional
	public void deleteScheduledFlight(int id) {
		
		log.debug("Inside deleteScheduledFlight function in FlightScheduleService class");
		
		Optional<FlightSchedule> optFlightSchedule=flightScheduleRepository.findById(id);
		
		if(optFlightSchedule.isPresent()) {
		scheduleRepository.deleteById(optFlightSchedule.get().getSchedule().getScheduleId());
		flightScheduleRepository.deleteById(id);
		}
	}
	
	/*****************************************************************************************************************************************************************************
		- Method Name          : modifyScheduledFlights
		- Input Parameters     : FlightSchedule flightSchedule
		- Return type          : void
		- Author               : Capgemini
		- Creation Date        : 13-8-2020
		- Description          : This method is responsible for updating flight schedule in database by calling save method from IFlightScheduleRepository interface.

	 *****************************************************************************************************************************************************************************/
	
	@Override
	@Transactional
	public void modifyScheduledFlight(FlightSchedule flightSchedule) {
		
		log.debug("Inside modifyScheduledFlight function in FlightScheduleService class");
		
		scheduleRepository.save(flightSchedule.getSchedule());
		
		flightScheduleRepository.save(flightSchedule);
		
	}
	
	/*****************************************************************************************************************************************************************************
		- Method Name          : viewScheduledFlights
		- Input Parameters     : N/A
		- Return type          : List<FlightSchedule>
		- Author               : Capgemini
		- Creation Date        : 13-8-2020
		- Description          : This method is responsible for returning all the data of flight schedule from database by calling findAll method of IFlightScheduleRepository interface.

	 *****************************************************************************************************************************************************************************/
	
	@Override
	@Transactional
	public List<FlightSchedule> viewScheduledFlights(){
		
		log.debug("Inside viewScheduledFlights function in FlightScheduleService class");
		
		return flightScheduleRepository.findAll();
				
		
	}
	
	/*****************************************************************************************************************************************************************************
		- Method Name          : validateScheduledFlight
		- Input Parameters     : FlightSchedule flightSchedule
		- Return type          : String
		- Author               : Capgemini
		- Creation Date        : 13-8-2020
		- Description          : This method is responsible for validating all the parameters of FlightSchedule object.

	 *****************************************************************************************************************************************************************************/	
	
	@Override
	@Transactional
	public String validateScheduledFlight(FlightSchedule flightSchedule){
		
		Flight flight=flightSchedule.getFlight();
		Schedule schedule=flightSchedule.getSchedule();
		
		Optional<Flight> flightOpt=flightRepository.findById(flight.getFlightNumber());
		
		if(!flightOpt.isPresent()) {
			return "No Flight with flight Number "+flight.getFlightNumber()+" exists!!";
		}
		
		
		Airport source=schedule.getSourceAirport();
		Optional<Airport> airportSourceOpt=airportReposidtory.findById(source.getAirportCode());
		if(!airportSourceOpt.isPresent()) {
			return "No source airport exists of code "+source.getAirportCode();
		}
		
		Airport destination=schedule.getDestinationAirport();
		Optional<Airport> airportDestinationOpt=airportReposidtory.findById(destination.getAirportCode());
		if(!airportDestinationOpt.isPresent()) {
			return "No destination airport exists of code "+destination.getAirportCode();
		}
		
		LocalDateTime arrivalTime=schedule.getArrivalTime();
		int status=arrivalTime.toLocalDate().compareTo(LocalDate.now());
		if(status<1) {
			return "Arrival date should be greater than present date";
		}
		
		LocalDateTime departureTime=schedule.getDepartureTime();
		int status2=departureTime.toLocalDate().compareTo(LocalDate.now());
		if(status2<1) {
			return "Departure date should be greater than present date";
		}
		if(arrivalTime.toLocalDate().compareTo(departureTime.toLocalDate())<0)
			return "Arrival schedule cannot be less than departure schedule";
		
		else if((arrivalTime.toLocalDate().compareTo(departureTime.toLocalDate())==0) && (arrivalTime.toLocalTime().compareTo(departureTime.toLocalTime())<0))
			return "Arrival schedule cannot be less than departure schedule";
		else
			return "valid data";
		
		
	}
	

}
