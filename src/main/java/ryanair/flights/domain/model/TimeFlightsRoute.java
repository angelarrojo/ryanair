package ryanair.flights.domain.model;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.Collection;

public class TimeFlightsRoute implements Serializable {

	private static final long serialVersionUID = 771127210490725541L;
	private Route route;
    private YearMonth yearMonth;
    private Collection<Leg> flights;

    public TimeFlightsRoute() {
		super();
	}

	public TimeFlightsRoute(Route route, YearMonth yearMonth, Collection<Leg> flights) {
        this.route = route;
        this.yearMonth = yearMonth;
        this.flights = flights;
    }

    public Collection<Leg> getFlights() {
        return flights;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public Route getRoute() {
        return route;
    }
}
