package ryanair.flights.domain.ports.primary;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ryanair.flights.domain.model.InterconnectedFlights;
import ryanair.flights.domain.model.Leg;
import ryanair.flights.domain.model.Route;

@RunWith(MockitoJUnitRunner.class)
public class FlightsUseCaseTest {

	@Mock
	private FlightsUseCase flightsUseCase;

	@Test
	public void findFlightsOk() {
		String departure = "DUB";
		String arrival = "WRO";
		LocalDateTime departureDateTime = LocalDateTime.now();
		LocalDateTime arrivalDateTime = LocalDateTime.now();
		List<InterconnectedFlights> interconnectedFlights = initialize(departureDateTime, arrivalDateTime);

		when(flightsUseCase.findFlights(departure, arrival, departureDateTime, arrivalDateTime))
				.thenReturn(interconnectedFlights);

		List<InterconnectedFlights> findFlights = flightsUseCase.findFlights(departure, arrival, departureDateTime,
				arrivalDateTime);

		assertThat(findFlights.size(), greaterThan(0));
	}

	private List<InterconnectedFlights> initialize(LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
		List<InterconnectedFlights> interconnectedFlights = new ArrayList<InterconnectedFlights>();
		Collection<Leg> flights = new ArrayList<Leg>();
		Route route = new Route("DUB", "WRO");
		Leg leg = new Leg("1", route, departureDateTime, arrivalDateTime);
		flights.add(leg);
		InterconnectedFlights interconnectedFlight = new InterconnectedFlights(0, flights);
		interconnectedFlights.add(interconnectedFlight);
		return interconnectedFlights;
	}
}
