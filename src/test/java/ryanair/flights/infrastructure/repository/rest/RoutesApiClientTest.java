package ryanair.flights.infrastructure.repository.rest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import junit.framework.TestCase;
import ryanair.flights.infrastructure.SampleJettyApplication;
import ryanair.flights.infrastructure.repository.rest.api.Route;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SampleJettyApplication.class)
@WebAppConfiguration
public class RoutesApiClientTest extends TestCase {

    @Autowired
    private RoutesApiClient client;

    @Test
    public void shouldGetRoutes() {
        List<Route> routes = client.getRoutes();
        assertThat(routes.size(),greaterThan(0));
    }

}
