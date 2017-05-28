package ryanair.flights.domain.ports.primary;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ryanair.flights.domain.model.FlightsRequest;
import ryanair.flights.domain.model.FlightsResponse;
import ryanair.flights.domain.model.InterconnectedFlights;
import ryanair.flights.domain.ports.secondary.FlightsRepository;

@Component
public class FlightsUseCase {

	@Autowired
	private FlightsRepository flightsRepository;

	public List<InterconnectedFlights> findFlights(String departure, String arrival, LocalDateTime departureDateTime,
			LocalDateTime arrivalDateTime) {
		FlightsRequest request = new FlightsRequest(departure, arrival, departureDateTime, arrivalDateTime);
		FlightsResponse response = new FlightsResponse();
		response.setFlights(flightsRepository.findFlights(request));
		return response.getFlights();
	}
}
