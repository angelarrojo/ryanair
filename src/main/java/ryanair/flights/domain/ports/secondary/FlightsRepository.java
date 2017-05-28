package ryanair.flights.domain.ports.secondary;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.jgrapht.DirectedGraph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.AllDirectedPaths;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ryanair.flights.domain.model.FlightsRequest;
import ryanair.flights.domain.model.InterconnectedFlights;
import ryanair.flights.domain.model.Leg;
import ryanair.flights.domain.model.TimeFlightsRoute;
import ryanair.flights.infrastructure.repository.rest.RoutesApiClient;
import ryanair.flights.infrastructure.repository.rest.SchedulesApiClient;
import ryanair.flights.infrastructure.repository.rest.api.Days;
import ryanair.flights.infrastructure.repository.rest.api.Flights;
import ryanair.flights.infrastructure.repository.rest.api.Route;
import ryanair.flights.infrastructure.repository.rest.api.Schedules;

@Component
public class FlightsRepository {

	private static final int WITHOUTSTOPS = 0;

	private static final int DEEP_LEGS = 2;

	@Autowired
	private RoutesApiClient routesApiClient;

	@Autowired
	private SchedulesApiClient schedulesApiClient;

	public List<InterconnectedFlights> findFlights(FlightsRequest request) {

		if (request.getDeparture().equals(request.getArrival()))
			return new ArrayList<InterconnectedFlights>();
		if (request.getDepartureDateTime().compareTo(request.getArrivalDateTime()) >= 0)
			return new ArrayList<InterconnectedFlights>();

		List<List<ryanair.flights.domain.model.Route>> routes = retrieveRoutes(request.getDeparture(), request.getArrival());
		List<YearMonth> dates = getMonthsBetweenStartAndEnd(request.getDepartureDateTime(), request.getArrivalDateTime());

		List<List<Leg>> flightsPerLeg = new ArrayList<List<Leg>>();
		List<Leg> legsListFrom = new ArrayList<Leg>();
		List<Leg> legsListTo = new ArrayList<Leg>();
		List<InterconnectedFlights> interconnectedFlights = new ArrayList<InterconnectedFlights>();
		for (List<ryanair.flights.domain.model.Route> routesLeg : routes) {
			flightsPerLeg = getFlightsLeg(request.getDepartureDateTime(), request.getArrivalDateTime(), dates, routesLeg);
			legsListFrom = initializeLegListFrom(flightsPerLeg);
			if (flightsPerLeg.size() > 1) {
				legsListTo = initializeLegListTo(flightsPerLeg);

				for (Leg legsFrom : legsListFrom) {
					for (Leg legsTo : legsListTo) {
						List<Leg> legsInterconnected = new ArrayList<Leg>();

						if (legsFrom.getDepartureTime().isAfter(request.getDepartureDateTime())
								&& legsTo.getDepartureTime().isAfter(legsFrom.getArrivalTime().plusMinutes(120))
								&& legsTo.getArrivalTime().isBefore(request.getArrivalDateTime())
								&& legsTo.getDepartureTime().isBefore(legsFrom.getArrivalTime().plusMinutes(1440))) {
							legsInterconnected.add(legsFrom);
							legsInterconnected.add(legsTo);
							InterconnectedFlights interconnectedFlight = new InterconnectedFlights(
									legsInterconnected.size() - 1, legsInterconnected);
							interconnectedFlights.add(interconnectedFlight);
						}
					}

				}
			} else {
				for (Leg from : legsListFrom) {
					List<Leg> listWithoutStop = new ArrayList<Leg>();
					listWithoutStop.add(from);
					InterconnectedFlights interconnectedFlight = new InterconnectedFlights(WITHOUTSTOPS,
							listWithoutStop);
					interconnectedFlights.add(interconnectedFlight);
				}

			}
		}
		return interconnectedFlights;
	}

	private List<List<ryanair.flights.domain.model.Route>> retrieveRoutes(String departure, String arrival) {
		DirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
		List<Route> routes = routesApiClient.getRoutes();
		routes.stream().forEach(route -> {
			graph.addVertex(route.getAirportFrom());
			graph.addVertex(route.getAirportTo());
			graph.addEdge(route.getAirportFrom(), route.getAirportTo());
		});

		if (departure.equals(arrival))
			emptyList();
		List<List<ryanair.flights.domain.model.Route>> result = new LinkedList<>();
		if (graph.containsEdge(departure, arrival)) {
			ryanair.flights.domain.model.Route r = new ryanair.flights.domain.model.Route(departure, arrival);
			result.add(singletonList(r));
		}
		List<List<ryanair.flights.domain.model.Route>> multiLegRoutes = findLegRoutes(departure, arrival, graph);
		result.addAll(multiLegRoutes);
		return result;
	}

	private List<List<ryanair.flights.domain.model.Route>> findLegRoutes(String departure, String arrival,
			DirectedGraph<String, DefaultEdge> routes) {
		List<GraphPath<String, DefaultEdge>> paths = new AllDirectedPaths<String, DefaultEdge>(routes)
				.getAllPaths(departure, arrival, true, DEEP_LEGS);
		List<List<ryanair.flights.domain.model.Route>> routesList = new ArrayList<List<ryanair.flights.domain.model.Route>>();
		for (GraphPath<String, DefaultEdge> path : paths) {
			List<String> airport = Graphs.getPathVertexList(path);
			List<ryanair.flights.domain.model.Route> legs = new LinkedList<>();
			ryanair.flights.domain.model.Route firstLeg = new ryanair.flights.domain.model.Route(airport.get(0),
					airport.get(1));
			ryanair.flights.domain.model.Route secondLeg = new ryanair.flights.domain.model.Route(airport.get(1),
					airport.get(2));
			legs.add(firstLeg);
			legs.add(secondLeg);
			routesList.add(legs);
		}
		return routesList;
	}

	private List<YearMonth> getMonthsBetweenStartAndEnd(LocalDateTime start, LocalDateTime end) {
		if (start.isAfter(end)) {
			List<YearMonth> months = getMonthsBetweenStartAndEnd(end, start);
			Collections.reverse(months);
			return months;
		} else {
			YearMonth from = YearMonth.from(start);
			YearMonth to = YearMonth.from(end);
			List<YearMonth> months = new LinkedList<>();
			months.add(from);
			if (to.isAfter(from)) {
				YearMonth m = from;
				do {
					m = m.plusMonths(1);
					months.add(m);
				} while (m.isBefore(to));
			}
			return months;
		}
	}

	private List<List<Leg>> getFlightsLeg(LocalDateTime departureDateTime, LocalDateTime arrivalDateTime,
			List<YearMonth> months, List<ryanair.flights.domain.model.Route> routing) {
		List<List<Leg>> legFlights = new ArrayList<List<Leg>>();
		for (ryanair.flights.domain.model.Route route : routing) {
			List<Leg> flightsConditions = getFlightsConditions(route.getFrom(), route.getTo(), months,
					departureDateTime, arrivalDateTime);
			legFlights.add(flightsConditions);
		}
		return legFlights;
	}

	private List<Leg> getFlightsConditions(String departure, String arrival, List<YearMonth> months, LocalDateTime from,
			LocalDateTime to) {
		Collection<Leg> flights = new ArrayList<Leg>();
		for (YearMonth month : months) {
			TimeFlightsRoute flightsTimeRoute = getFlightsTimeRoute(departure, arrival, month);
			flights = flightsTimeRoute.getFlights();
			if (flights == null) {
				return Collections.emptyList();
			}
			CollectionUtils.filter(flights, new FlightsPredicate(from, to));
		}
		return Collections.list(Collections.enumeration(flights));
	}

	private TimeFlightsRoute getFlightsTimeRoute(String departure, String arrival, YearMonth yearMonth) {
		ryanair.flights.domain.model.Route route = new ryanair.flights.domain.model.Route(departure, arrival);
		Schedules schedules = schedulesApiClient.getSchedules(departure, arrival, yearMonth);
		List<Leg> legs = new ArrayList<Leg>();
		if (schedules.getMonth() == yearMonth.getMonthValue()) {
			for (Days day : schedules.getDays()) {
				LocalDate date = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), day.getDay());
				for (Flights flight : day.getFlights()) {
					Leg leg = new Leg(flight.getNumber(), route, flight.getDepartureTime().atDate(date),
							flight.getArrivalTime().atDate(date));
					legs.add(leg);
					legs.sort(Leg.FLIGHT_TIME_COMPARATOR);
				}
			}
		}
		if (legs.isEmpty())
			return new TimeFlightsRoute();
		return new TimeFlightsRoute(route, yearMonth, legs);
	}

	public List<Leg> initializeLegListFrom(List<List<Leg>> flightsPerLeg) {
		return flightsPerLeg.get(0);
	}

	public List<Leg> initializeLegListTo(List<List<Leg>> flightsPerLeg) {
		return flightsPerLeg.get(1);
	}

	private class FlightsPredicate implements Predicate {
		private LocalDateTime from;
		private LocalDateTime to;

		public FlightsPredicate(LocalDateTime from, LocalDateTime to) {
			this.from = from;
			this.to = to;
		}

		@Override
		public boolean evaluate(Object object) {
			Leg leg = (Leg) object;
			return (!leg.getDepartureTime().isBefore(from) && !leg.getArrivalTime().isAfter(to));
		}

	}
}

