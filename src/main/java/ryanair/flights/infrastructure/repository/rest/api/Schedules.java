package ryanair.flights.infrastructure.repository.rest.api;

import java.io.Serializable;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Schedules implements Serializable {

	private static final long serialVersionUID = -8897742777821771196L;
	private int month;
	private Collection<Days> days;

	
	public Schedules() {
		super();
	}

	@JsonCreator
	public Schedules(@JsonProperty("month") int month, @JsonProperty("days") Collection<Days> days) {
		this.days = days;
		this.month = month;
	}

	public Collection<Days> getDays() {
		return days;
	}

	public int getMonth() {
		return month;
	}
}
