package ryanair.flights.domain.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class InterconnectedFlights implements Serializable {

	private static final long serialVersionUID = 4215525776392452020L;
	private final int stops;
	private final Collection<Leg> legs;

	@JsonCreator
	public InterconnectedFlights(@JsonProperty("stops") int stops, @JsonProperty("legs") Collection<Leg> legs) {
		this.legs = legs.stream().collect(Collectors.toList());
		this.stops = stops;
	}

	public int getStops() {
		return stops;
	}

	public Collection<Leg> getLegs() {
		return legs;
	}
}
