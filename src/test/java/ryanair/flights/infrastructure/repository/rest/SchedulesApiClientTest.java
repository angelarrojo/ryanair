package ryanair.flights.infrastructure.repository.rest;

import java.time.YearMonth;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ryanair.flights.infrastructure.repository.rest.api.Schedules;

public class SchedulesApiClientTest extends UnitTest {

	@Autowired
	private SchedulesApiClient client;

	@Test
	public void shouldGetRoutes() {

		YearMonth y = YearMonth.of(2018, 3);
		Schedules schedules = client.getSchedules("DUB", "WRO", y);
		assertEquals(schedules.getMonth(), 3);
	}

}
