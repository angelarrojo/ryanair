package ryanair.flights.infrastructure.repository.rest;

import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import ryanair.flights.infrastructure.repository.rest.api.Route;

/**
 * Routes API client.
 *
 * Calls https://api.ryanair.com/core/3/routes/
 */
public class RoutesApiClient extends BaseClient {

    public RoutesApiClient(RestTemplate template, String baseUrl) {
        this.template = template;
        this.baseUrl = baseUrl;
    }

    public List<Route> getRoutes() {
        try {
            ParameterizedTypeReference<List<Route>> type = new ParameterizedTypeReference<List<Route>>() {};
            ResponseEntity<List<Route>> instance = template.exchange(baseUrl, HttpMethod.GET, null, type);
            if(instance.getStatusCode()== HttpStatus.OK)
                return instance.getBody();
            else return Collections.emptyList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
