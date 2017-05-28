package ryanair.flights.domain.model;

import java.util.List;

public class FlightsResponse {
	List<InterconnectedFlights> flights;

	public List<InterconnectedFlights> getFlights() {
		return flights;
	}

	public void setFlights(List<InterconnectedFlights> flights) {
		this.flights = flights;
	}

}
