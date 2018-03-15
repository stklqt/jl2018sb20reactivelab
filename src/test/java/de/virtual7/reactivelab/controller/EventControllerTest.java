package de.virtual7.reactivelab.controller;

import de.virtual7.reactivelab.event.TrackingEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

/**
 * Created by mihai.dobrescu
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EventControllerTest {

    private WebTestClient webTestClient;

    @Before
    public void setUp() throws Exception {
        this.webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
    }

}