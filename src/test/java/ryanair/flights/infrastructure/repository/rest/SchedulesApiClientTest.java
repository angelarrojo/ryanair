package ryanair.flights.infrastructure.repository.rest;

import java.time.YearMonth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import junit.framework.TestCase;
import ryanair.flights.infrastructure.SampleJettyApplication;
import ryanair.flights.infrastructure.repository.rest.api.Schedules;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SampleJettyApplication.class)
@WebAppConfiguration
public class SchedulesApiClientTest extends TestCase {

	@Autowired
	private SchedulesApiClient client;

	@Test
	public void shouldGetRoutes() {

		YearMonth y = YearMonth.of(2018, 3);
		Schedules schedules = client.getSchedules("DUB", "WRO", y);
		assertEquals(schedules.getMonth(), 3);
	}

}
