package ryanair.flights.infrastructure.repository.rest;

import java.time.YearMonth;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import ryanair.flights.infrastructure.repository.rest.api.Schedules;


/**
 * Schedules API client.
 *
 * Calls https://api.ryanair.com/timetable/3/schedules/{departure}/{arrival}/years/{year}/months/{month}
 */
public class SchedulesApiClient extends BaseClient {

    public SchedulesApiClient(RestTemplate template, String baseUrl) {
        this.template = template;
        this.baseUrl = baseUrl;
    }

    public Schedules getSchedules(String departure, String arrival, YearMonth yearMonth) {
        try {
    		ParameterizedTypeReference<Schedules> type = new ParameterizedTypeReference<Schedules>() {
    		};
    		ResponseEntity<Schedules> instance = template.exchange(baseUrl, HttpMethod.GET, null, type, departure, arrival,
    				yearMonth.getYear(), yearMonth.getMonthValue());
    		if (instance.hasBody())
    			return instance.getBody();
    		else return new Schedules();
        }
        catch (Exception e) {
            return new Schedules();
        }
    }
}
