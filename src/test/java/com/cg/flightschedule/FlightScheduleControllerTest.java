package com.cg.flightschedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import java.math.BigInteger;
import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.cg.flightschedule.entity.Airport;
import com.cg.flightschedule.entity.Flight;
import com.cg.flightschedule.entity.FlightSchedule;
import com.cg.flightschedule.entity.Schedule;
import com.cg.flightschedule.services.IFlightScheduleService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FlightScheduleControllerTest {

	@Autowired
	private MockMvc mock;
	
	@MockBean
	private IFlightScheduleService flightScheduleService;
	
	Flight flight=new Flight(new BigInteger("789456123"),null,null,0);
	Airport source=new Airport("LKO",null,null);
	Airport destination=new Airport("NDL",null,null);
	LocalDateTime departure=LocalDateTime.of(2020, 9, 11, 02,00);
	LocalDateTime arrival=LocalDateTime.of(2020, 9, 11, 04, 00);
	Schedule schedule=new Schedule(source,destination,arrival,departure);
	FlightSchedule flightSchedule=new FlightSchedule(210,flight,0,schedule,0);
	
	@SuppressWarnings("deprecation")
	@Test
	public void addFlightScheduleTest() throws Exception {
		
		String message="valid data";
		
		when(flightScheduleService.validateScheduledFlight(Mockito.anyObject())).thenReturn(message);
		
		when(flightScheduleService.scheduleFlight(Mockito.anyObject())).thenReturn("Added successfully");
		
		String json="{\"scheduleFlightId\":146,\"flight\":{\"flightNumber\":789456123,\"carrierName\":\"Jet Airways\",\"flightModel\":\"Jumbo Jet\",\"seatNumber\":280},\"availableSeats\":280,\"schedule\":{\"scheduleId\":145,\"sourceAirport\":{\"airportCode\":\"LKO\",\"airportLocation\":\"Lucknow\",\"airportName\":\"Chaudhary Charan Singh Airport\"},\"destinationAirport\":{\"airportCode\":\"BOM\",\"airportLocation\":\"Mumbai\",\"airportName\":\"Chattrapati Shivaji Maharaj International Airport\"},\"arrivalTime\":\"2020-08-29T22:41:00\",\"departureTime\":\"2020-08-29T15:41:00\"},\"cost\":2111.0}";
		MvcResult result=mock.perform(post("/flight/add").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
		
		assertEquals(200, result.getResponse().getStatus());
	}

}
