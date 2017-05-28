package ryanair.flights.infrastructure.repository.rest.api;

import java.io.Serializable;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Flights implements Serializable {

	private static final long serialVersionUID = -5776892395336805779L;
	private String number;
	private LocalTime departureTime;
	private LocalTime arrivalTime;

	@JsonCreator
	public Flights(@JsonProperty("number") String number, @JsonProperty("departureTime") String departureTime,
			@JsonProperty("arrivalTime") String arrivalTime) {
		this.number = number;
		this.departureTime = LocalTime.parse(departureTime);
		;
		this.arrivalTime = LocalTime.parse(arrivalTime);
		;
	}

	public LocalTime getArrivalTime() {
		return arrivalTime;
	}

	public LocalTime getDepartureTime() {
		return departureTime;
	}

	public String getNumber() {
		return number;
	}
}