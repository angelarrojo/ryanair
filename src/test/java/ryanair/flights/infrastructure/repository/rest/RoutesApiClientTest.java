package ryanair.flights.infrastructure.repository.rest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ryanair.flights.infrastructure.repository.rest.api.Route;


public class RoutesApiClientTest extends UnitTest {

    @Autowired
    private RoutesApiClient client;

    @Test
    public void shouldGetRoutes() {
        List<Route> routes = client.getRoutes();
        assertThat(routes.size(),greaterThan(0));
    }

}
