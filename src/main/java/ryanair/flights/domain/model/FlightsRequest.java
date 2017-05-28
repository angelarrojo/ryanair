package ryanair.flights.domain.model;

import java.time.LocalDateTime;

public class FlightsRequest {
	private String departure;
	private String arrival;
	private LocalDateTime departureDateTime;
	private LocalDateTime arrivalDateTime;

	public FlightsRequest(String departure, String arrival, LocalDateTime departureDateTime,
			LocalDateTime arrivalDateTime) {
		super();
		this.departure = departure;
		this.arrival = arrival;
		this.departureDateTime = departureDateTime;
		this.arrivalDateTime = arrivalDateTime;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public LocalDateTime getDepartureDateTime() {
		return departureDateTime;
	}

	public void setDepartureDateTime(LocalDateTime departureDateTime) {
		this.departureDateTime = departureDateTime;
	}

	public LocalDateTime getArrivalDateTime() {
		return arrivalDateTime;
	}

	public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
		this.arrivalDateTime = arrivalDateTime;
	}

}
