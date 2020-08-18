/********************************************************************************************************************************************************************************
	- Interface Name       : IFlightRepository
	- Author               : Capgemini
	- Creation Date        : 13-8-2020
	- Description          : This interface consist of unimplemented methods which are responsible for persisting data into database. It extends the JpaRepository.  

********************************************************************************************************************************************************************************/

package com.cg.flightschedule.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.flightschedule.entity.Flight;


@Repository
public interface IFlightRepository extends JpaRepository<Flight, BigInteger> {
	
	

}
