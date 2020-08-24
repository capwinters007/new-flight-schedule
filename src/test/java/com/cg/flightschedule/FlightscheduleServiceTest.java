/********************************************************************************************************************************************************************************
	- Class Name           : FlightScheduleServiceTest
	- Author               : Capgemini
	- Creation Date        : 17-8-2020
	- Description          : This class responsible for unit testing all the methods implemented in FlightScheduleService class 

********************************************************************************************************************************************************************************/


package com.cg.flightschedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.cg.flightschedule.entity.Airport;
import com.cg.flightschedule.entity.Flight;
import com.cg.flightschedule.entity.FlightSchedule;
import com.cg.flightschedule.entity.Schedule;
import com.cg.flightschedule.repository.IAirportRepository;
import com.cg.flightschedule.repository.IFlightRepository;
import com.cg.flightschedule.repository.IFlightScheduleRepository;
import com.cg.flightschedule.repository.IScheduleRepository;
import com.cg.flightschedule.services.IFlightScheduleService;

@RunWith(SpringRunner.class)

@SpringBootTest
public class FlightscheduleServiceTest {

	@Autowired
	private IFlightScheduleService flightScheduleService;
	
	@MockBean
	private IFlightScheduleRepository flightScheduleRepository;
	
	@MockBean
	private IScheduleRepository scheduleRepository;
	
	@MockBean
	private IFlightRepository flightRepository;
	
	@MockBean
	private IAirportRepository airportRepository;
	
	Flight flight=new Flight(new BigInteger("789456123"),null,null,0);
	Airport source=new Airport("LKO",null,null);
	Airport destination=new Airport("NDL",null,null);
	LocalDateTime departure=LocalDateTime.of(2020, 9, 11, 02,00);
	LocalDateTime arrival=LocalDateTime.of(2020, 9, 11, 04, 00);
	Schedule schedule=new Schedule(source,destination,arrival,departure);
	FlightSchedule flightSchedule=new FlightSchedule(210,flight,0,schedule,0);
	
	/*****************************************************************************************************************************************************************************
	- Method Name          : scheduleFlightTest
	- Input Parameters     : N/A
	- Return type          : void
	- Author               : Capgemini
	- Creation Date        : 17-8-2020
	- Description          : This method is responsible for unit testing scheduleFlight method in service class.

	 *****************************************************************************************************************************************************************************/

	
	@Test
	public void scheduleFlightTest() {

		Optional<Flight> optFlight=Optional.of(flight);
		
		when(flightRepository.findById(flightSchedule.getFlight().getFlightNumber())).thenReturn(optFlight);
		when(scheduleRepository.save(flightSchedule.getSchedule())).thenReturn(schedule);
		when(flightScheduleRepository.save(flightSchedule)).thenReturn(flightSchedule);
		
		assertEquals("Added successfully", flightScheduleService.scheduleFlight(flightSchedule));
		
		verify(flightRepository).findById(flightSchedule.getFlight().getFlightNumber());
		verify(scheduleRepository).save(flightSchedule.getSchedule());
		verify(flightScheduleRepository).save(flightSchedule);
		
	}
	
	/*****************************************************************************************************************************************************************************
	- Method Name          : viewScheduledFlightByIdTest
	- Input Parameters     : N/A
	- Return type          : void
	- Author               : Capgemini
	- Creation Date        : 17-8-2020
	- Description          : This method is responsible for unit testing viewScheduledFlight method in FlightScheduleService class.

	 *****************************************************************************************************************************************************************************/

	
	@Test
	public void viewScheduledFlightsByIdTest() {
		
		Optional<FlightSchedule> optFlightSchedule=Optional.of(flightSchedule);
		
		when(flightScheduleRepository.findById(210)).thenReturn(optFlightSchedule);
		
		assertEquals(flightSchedule.getScheduleFlightId(),flightScheduleService.viewScheduledFlights(210).get().getScheduleFlightId());
	
		verify(flightScheduleRepository).findById(210);
	}
	
	/*****************************************************************************************************************************************************************************
	- Method Name          : deleteScheduledFlightTest
	- Input Parameters     : N/A
	- Return type          : void
	- Author               : Capgemini
	- Creation Date        : 17-8-2020
	- Description          : This method is responsible for unit testing deleteScheduledFlight method in FlightScheduleService class.

	 *****************************************************************************************************************************************************************************/

	
	@Test
	public void deleteScheduledFlightTest() {
		
		Optional<FlightSchedule> optFlightSchedule=Optional.of(flightSchedule);
		
		when(flightScheduleRepository.findById(210)).thenReturn(optFlightSchedule);
		
		flightScheduleService.deleteScheduledFlight(210);
		verify(flightScheduleRepository).deleteById(210);
	}
	
	/*****************************************************************************************************************************************************************************
	- Method Name          : modifyScheduledFlightTest
	- Input Parameters     : N/A
	- Return type          : void
	- Author               : Capgemini
	- Creation Date        : 17-8-2020
	- Description          : This method is responsible for unit testing modifyScheduledFlight method in FlightScheduleService class.

	 *****************************************************************************************************************************************************************************/

	
	@Test
	public void modifyScheduledFlightTest() {
		
		when(scheduleRepository.save(flightSchedule.getSchedule())).thenReturn(schedule);
		when(flightScheduleRepository.save(flightSchedule)).thenReturn(flightSchedule);
		
		assertEquals("Modified successfully", flightScheduleService.modifyScheduledFlight(flightSchedule));
		
		verify(scheduleRepository).save(flightSchedule.getSchedule());
		verify(flightScheduleRepository).save(flightSchedule);
	}
	
	/*****************************************************************************************************************************************************************************
	- Method Name          : viewScheduledFlightAllTest
	- Input Parameters     : N/A
	- Return type          : void
	- Author               : Capgemini
	- Creation Date        : 17-8-2020
	- Description          : This method is responsible for unit testing viewScheduledFlight method in FlightScheduleService class.

	 *****************************************************************************************************************************************************************************/

	
	@Test
	public void viewScheduledFlightsAllTest() {
		
		List<FlightSchedule> flightScheduleList=new ArrayList<>();
		flightScheduleList.add(flightSchedule);
		
		when(flightScheduleRepository.findAll()).thenReturn(flightScheduleList);
		
		assertEquals(1, flightScheduleService.viewScheduledFlights().size());
		
		verify(flightScheduleRepository).findAll();
	}
	
	/*****************************************************************************************************************************************************************************
	- Method Name          : validateScheduledFlightTest
	- Input Parameters     : N/A
	- Return type          : void
	- Author               : Capgemini
	- Creation Date        : 17-8-2020
	- Description          : This method is responsible for unit testing validateScheduledFlight method in FlightScheduleService class.

	 *****************************************************************************************************************************************************************************/

	
	@Test
	public void validateScheduledFlightTest() {
		
		Optional<Flight> optFlight=Optional.of(flight);
		
		when(flightRepository.findById(flight.getFlightNumber())).thenReturn(optFlight);
		
		assertEquals("valid data",flightScheduleService.validateScheduledFlight(flightSchedule));
		verify(flightRepository).findById(flight.getFlightNumber());
		
	}
	
	/*****************************************************************************************************************************************************************************
	- Method Name          : validateScheduledFlightTest2
	- Input Parameters     : N/A
	- Return type          : void
	- Author               : Capgemini
	- Creation Date        : 17-8-2020
	- Description          : This method is responsible for unit testing validateScheduledFlight method in FlightScheduleService class.

	 *****************************************************************************************************************************************************************************/

	
	@Test
	public void validateScheduleFlightTest2() {
		
		Optional<Flight> flightOpt=Optional.empty();
		
		when(flightRepository.findById(flight.getFlightNumber())).thenReturn(flightOpt);
		
		assertEquals("No Flight with flight Number "+flight.getFlightNumber()+" exists!!",flightScheduleService.validateScheduledFlight(flightSchedule));
		verify(flightRepository).findById(flight.getFlightNumber());
	}

}
