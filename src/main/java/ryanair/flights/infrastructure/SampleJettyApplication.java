package ryanair.flights.infrastructure;

import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableJSONDoc
@ComponentScan("ryanair.flights")
public class SampleJettyApplication {
	public static void main(final String[] args) {
		SpringApplication.run(SampleJettyApplication.class, args);
	}
}
