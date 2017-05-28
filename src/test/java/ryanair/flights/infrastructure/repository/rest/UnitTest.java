package ryanair.flights.infrastructure.repository.rest;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import junit.framework.TestCase;
import ryanair.flights.infrastructure.SampleJettyApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SampleJettyApplication.class)
@WebAppConfiguration
public class UnitTest extends TestCase {

}
