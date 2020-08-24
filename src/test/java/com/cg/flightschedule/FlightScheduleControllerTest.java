/********************************************************************************************************************************************************************************
	- Class Name           : FlightScheduleControllerTest
	- Author               : Capgemini
	- Creation Date        : 17-8-2020
	- Description          : This class responsible for unit testing all the methods implemented in FlightScheduleController class 

********************************************************************************************************************************************************************************/

package com.cg.flightschedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	FlightSchedule flightSchedule=new FlightSchedule(146,flight,0,schedule,0);
	
	
	/*****************************************************************************************************************************************************************************
	- Method Name          : addFlightScheduleTest
	- Input Parameters     : N/A
	- Return type          : void
	- Author               : Capgemini
	- Creation Date        : 17-8-2020
	- Description          : This method is responsible for unit testing addFlightSchedule method in controller class.

	 *****************************************************************************************************************************************************************************/
	
	@SuppressWarnings("deprecation")
	@Test
	public void addFlightScheduleTest() throws Exception {
		
		when(flightScheduleService.validateScheduledFlight(Mockito.anyObject())).thenReturn("valid data");
		
		when(flightScheduleService.scheduleFlight(Mockito.anyObject())).thenReturn("Added successfully");
		
		String json="{\"scheduleFlightId\":146,\"flight\":{\"flightNumber\":789456123,\"carrierName\":\"Jet Airways\",\"flightModel\":\"Jumbo Jet\",\"seatNumber\":280},\"availableSeats\":280,\"schedule\":{\"scheduleId\":145,\"sourceAirport\":{\"airportCode\":\"LKO\",\"airportLocation\":\"Lucknow\",\"airportName\":\"Chaudhary Charan Singh Airport\"},\"destinationAirport\":{\"airportCode\":\"BOM\",\"airportLocation\":\"Mumbai\",\"airportName\":\"Chattrapati Shivaji Maharaj International Airport\"},\"arrivalTime\":\"2020-08-29T22:41:00\",\"departureTime\":\"2020-08-29T15:41:00\"},\"cost\":2111.0}";
		MvcResult result=mock.perform(post("/flight/add").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
		
		assertEquals(200, result.getResponse().getStatus());
		verify(flightScheduleService).validateScheduledFlight(Mockito.anyObject());
		verify(flightScheduleService).scheduleFlight(Mockito.anyObject());
	}
	
	/*****************************************************************************************************************************************************************************
	- Method Name          : deleteFlightScheduleTest
	- Input Parameters     : N/A
	- Return type          : void
	- Author               : Capgemini
	- Creation Date        : 17-8-2020
	- Description          : This method is responsible for unit testing deleteFlightSchedule method in controller class.

	 *****************************************************************************************************************************************************************************/

	
	@Test
	public void deleteFlightScheduleTest() throws Exception {
		
		Optional<FlightSchedule> optFlightSchedule=Optional.of(flightSchedule);
		
		when(flightScheduleService.deleteScheduledFlight(146)).thenReturn("Deleted successfully");
		
		when(flightScheduleService.viewScheduledFlights(146)).thenReturn(optFlightSchedule);
		
		MvcResult result=mock.perform(get("/flight/deleteFlightSchedule?id=146")).andReturn();
		assertEquals(200,result.getResponse().getStatus());
		
		verify(flightScheduleService).deleteScheduledFlight(146);
		verify(flightScheduleService).viewScheduledFlights(146);
	}
	
	/*****************************************************************************************************************************************************************************
	- Method Name          : updateFlightScheduleTest
	- Input Parameters     : N/A
	- Return type          : void
	- Author               : Capgemini
	- Creation Date        : 17-8-2020
	- Description          : This method is responsible for unit testing updateFlightSchedule method in controller class.

	 *****************************************************************************************************************************************************************************/

	
	@SuppressWarnings("deprecation")
	@Test
	public void updateFlightSchedule() throws Exception {
		
		when(flightScheduleService.validateScheduledFlight(Mockito.anyObject())).thenReturn("valid data");
		
		when(flightScheduleService.modifyScheduledFlight(Mockito.anyObject())).thenReturn("Modified successfully");
		
		String json="{\"scheduleFlightId\":146,\"flight\":{\"flightNumber\":789456123,\"carrierName\":\"Jet Airways\",\"flightModel\":\"Jumbo Jet\",\"seatNumber\":280},\"availableSeats\":280,\"schedule\":{\"scheduleId\":145,\"sourceAirport\":{\"airportCode\":\"LKO\",\"airportLocation\":\"Lucknow\",\"airportName\":\"Chaudhary Charan Singh Airport\"},\"destinationAirport\":{\"airportCode\":\"BOM\",\"airportLocation\":\"Mumbai\",\"airportName\":\"Chattrapati Shivaji Maharaj International Airport\"},\"arrivalTime\":\"2020-08-29T22:41:00\",\"departureTime\":\"2020-08-29T15:41:00\"},\"cost\":2111.0}";
		MvcResult result=mock.perform(post("/flight/update").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();
		
		assertEquals(200,result.getResponse().getStatus());
		
		verify(flightScheduleService).validateScheduledFlight(Mockito.anyObject());
		verify(flightScheduleService).modifyScheduledFlight(Mockito.anyObject());
		
	}
	
	/*****************************************************************************************************************************************************************************
	- Method Name          : getFlightScheduleByIdTest
	- Input Parameters     : N/A
	- Return type          : void
	- Author               : Capgemini
	- Creation Date        : 17-8-2020
	- Description          : This method is responsible for unit testing getFlightScheduleById method in controller class.

	 *****************************************************************************************************************************************************************************/

	
	@Test
	public void getFlightScheduleByIdTest() throws Exception {
		
		Optional<FlightSchedule>optFlightSchedule=Optional.of(flightSchedule);
		
		when(flightScheduleService.viewScheduledFlights(146)).thenReturn(optFlightSchedule);
		MvcResult result=mock.perform(get("/flight/viewById?id=146")).andReturn();
		
		assertEquals(200,result.getResponse().getStatus());
		
		verify(flightScheduleService).viewScheduledFlights(146);
	}
	
	/*****************************************************************************************************************************************************************************
	- Method Name          : getAllFlightScheduleTest
	- Input Parameters     : N/A
	- Return type          : void
	- Author               : Capgemini
	- Creation Date        : 17-8-2020
	- Description          : This method is responsible for unit testing getAllFlightSchedule method in controller class.

	 *****************************************************************************************************************************************************************************/

	
	@Test
	public void getAllFlightScheduleTest() throws Exception {
		
		List<FlightSchedule> flightScheduleList=new ArrayList<>();
		flightScheduleList.add(flightSchedule);
		
		when(flightScheduleService.viewScheduledFlights()).thenReturn(flightScheduleList);
		MvcResult result=mock.perform(get("/flight/viewAll")).andReturn();
		
		assertEquals(200, result.getResponse().getStatus());
		
		verify(flightScheduleService).viewScheduledFlights();
		
	}
}
