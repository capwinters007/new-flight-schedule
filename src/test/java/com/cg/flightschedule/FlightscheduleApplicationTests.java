package com.cg.flightschedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.cg.flightschedule.entity.Airport;
import com.cg.flightschedule.entity.Flight;
import com.cg.flightschedule.entity.FlightSchedule;
import com.cg.flightschedule.entity.Schedule;
import com.cg.flightschedule.repository.IFlightRepository;
import com.cg.flightschedule.repository.IFlightScheduleRepository;
import com.cg.flightschedule.repository.IScheduleRepository;
import com.cg.flightschedule.services.IFlightScheduleService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)

@SpringBootTest
public class FlightscheduleApplicationTests {
	
	
	private MockMvc mock;

	@Autowired
	private IFlightScheduleService flightScheduleService;
	
	@MockBean
	private IFlightScheduleRepository flightScheduleRepository;
	
	@MockBean
	private IScheduleRepository scheduleRepository;
	
	@MockBean
	private IFlightRepository flightRepository;
	
	Flight flight=new Flight(new BigInteger("789456123"),null,null,0);
	Airport source=new Airport("LKO",null,null);
	Airport destination=new Airport("NDL",null,null);
	LocalDateTime departure=LocalDateTime.of(2020, 9, 11, 02,00);
	LocalDateTime arrival=LocalDateTime.of(2020, 9, 11, 04, 00);
	Schedule schedule=new Schedule(source,destination,arrival,departure);
	FlightSchedule flightSchedule=new FlightSchedule(210,flight,0,schedule,0);
	
	@Test
	public void scheduleFlightTest() {

		Optional<Flight> optFlight=Optional.of(flight);
		
		when(flightRepository.findById(flightSchedule.getFlight().getFlightNumber())).thenReturn(optFlight);
		
		when(scheduleRepository.save(flightSchedule.getSchedule())).thenReturn(schedule);
		
		when(flightScheduleRepository.save(flightSchedule)).thenReturn(flightSchedule);
		
		assertEquals("Added successfully", flightScheduleService.scheduleFlight(flightSchedule));
		
	}
	
	@Test
	public void viewScheduledFlightsTest() {
		
		Optional<FlightSchedule> optFlightSchedule=Optional.of(flightSchedule);
		
		when(flightScheduleRepository.findById(210)).thenReturn(optFlightSchedule);
		
		assertEquals(flightSchedule.getScheduleFlightId(),flightScheduleService.viewScheduledFlights(210).get().getScheduleFlightId());
	}
	
	@Test
	public void addFlightScheduleTest() throws Exception {
		
		when(flightScheduleService.validateScheduledFlight(flightSchedule)).thenReturn(null);
		
		when(flightScheduleService.scheduleFlight(flightSchedule)).thenReturn("Added successfully");
		
		MvcResult result=mock.perform(post("/flight/add").contentType("application/json").content(
				new ObjectMapper().writeValueAsString(flightSchedule))).andReturn();
		
		assertEquals("Added successfully", result.getResponse());
	}

}
