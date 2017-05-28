### Technologies used
* Maven
* Spring Boot
* Java8

## Development

### Build & Test

`mvn clean test`

### Run locally

`mvn clean spring-boot:run`

Swagger Documentation: http://localhost:8080/swagger-ui.html


Operations: 

http://localhost:8080/interconnections?departure={departure}&arrival={arrival}&departureDateTime={departureDateTime}&arrivalDateTime={arrivalDateTime}

(Example:
http://localhost:8080/interconnections?departure=DUB&arrival=WRO&departureDateTime=2018-03-01T07:00&arrivalDateTime=2018-03-03T21:00
)