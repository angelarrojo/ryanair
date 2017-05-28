package ryanair.flights.infrastructure.repository.rest.api;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Route implements Serializable {

	private static final long serialVersionUID = -5419623420247981503L;
	private String airportFrom;
	private String airportTo;
	private Boolean newRoute;
	private Boolean seasonalRoute;

	@JsonCreator
	public Route(@JsonProperty("airportFrom") String airportFrom, @JsonProperty("airportTo") String airportTo,
			@JsonProperty("newRoute") Boolean newRoute, @JsonProperty("seasonalRoute") Boolean seasonalRoute) {
		this.airportFrom = airportFrom;
		this.airportTo = airportTo;
		this.newRoute = newRoute;
		this.seasonalRoute = seasonalRoute;
	}

	public String getAirportFrom() {
		return airportFrom;
	}

	public void setAirportFrom(String airportFrom) {
		this.airportFrom = airportFrom;
	}

	public String getAirportTo() {
		return airportTo;
	}

	public void setAirportTo(String airportTo) {
		this.airportTo = airportTo;
	}

	public Boolean getNewRoute() {
		return newRoute;
	}

	public void setNewRoute(Boolean newRoute) {
		this.newRoute = newRoute;
	}

	public Boolean getSeasonalRoute() {
		return seasonalRoute;
	}

	public void setSeasonalRoute(Boolean seasonalRoute) {
		this.seasonalRoute = seasonalRoute;
	}
}
