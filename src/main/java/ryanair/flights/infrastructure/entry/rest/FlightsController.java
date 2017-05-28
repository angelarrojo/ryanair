package ryanair.flights.infrastructure.entry.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ryanair.flights.domain.model.InterconnectedFlights;
import ryanair.flights.domain.ports.primary.FlightsUseCase;

@RestController
@RequestMapping("/interconnections")
@Api(value = "flights", description = "Operations Api Flights")
public class FlightsController {

	@Autowired
	private FlightsUseCase flightsUseCase;

	@ApiOperation(value = "See flights between the following filters", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<InterconnectedFlights>> searchFlights(@RequestParam("departure") String departure,
			@RequestParam("arrival") String arrival,
			@RequestParam("departureDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureDateTime,
			@RequestParam("arrivalDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime arrivalDateTime) {
		LocalDateTime start = truncate(departureDateTime);
		LocalDateTime end = truncate(arrivalDateTime);
		List<InterconnectedFlights> flights = flightsUseCase.findFlights(departure, arrival, start, end);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(APPLICATION_JSON_UTF8);
		return new ResponseEntity<>(flights, responseHeaders, HttpStatus.OK);
	}

	private LocalDateTime truncate(LocalDateTime dateTime) {
		return dateTime.truncatedTo(ChronoUnit.MINUTES);
	}
}
