package ryanair.flights.infrastructure.repository.rest.api;

import java.io.Serializable;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Days implements Serializable {

	private static final long serialVersionUID = -1056664924173067099L;
	private int day;
	private Collection<Flights> flights;

	@JsonCreator
	public Days(@JsonProperty("day") int day, @JsonProperty("flights") Collection<Flights> flights) {
		this.day = day;
		this.flights = flights;
	}

	public int getDay() {
		return day;
	}

	public Collection<Flights> getFlights() {
		return flights;
	}
}