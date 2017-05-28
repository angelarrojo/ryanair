package ryanair.flights.infrastructure.repository.rest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import ryanair.flights.infrastructure.repository.rest.RoutesApiClient;
import ryanair.flights.infrastructure.repository.rest.SchedulesApiClient;

@Configuration
public class RyanairApiConfiguration {

    @Bean
    public RoutesApiClient routesApiClient(@Value("${routesApi.url}") final String baseUrl) {
        return new RoutesApiClient(new RestTemplate(), baseUrl);
    }

    @Bean
    public SchedulesApiClient schedulesApiClient(@Value("${schedulesApi.url}") final String baseUrl) {
        return new SchedulesApiClient(new RestTemplate(), baseUrl);
    }
}
