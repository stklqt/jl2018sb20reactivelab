package de.virtual7.reactivelab.controller;

import de.virtual7.reactivelab.event.TrackingEvent;
import de.virtual7.reactivelab.event.TrackingEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;

/**
 * Created by mihai.dobrescu
 */
@RestController
@RequestMapping("/events")
public class EventController {

    Logger logger = LoggerFactory.getLogger(EventController.class);

    @GetMapping(path = "")
    public Flux<TrackingEvent> getEvents() {
        logger.info("getEvents() called!");
        return null;
    }

    // http://localhost:8080/events/latest
    @GetMapping(path = "/latest", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<TrackingEvent> getEventsLatest() {
        logger.info("getEventsLatest() called!");

        return Flux.range(0, 100) // .log() //TODO loggen optional
                .map(i -> new TrackingEvent(Long.valueOf(i), TrackingEventType.getRandomType(),
                BigDecimal.TEN, Instant.now()));

    }

    @GetMapping(path = "/top")
    public Flux<TrackingEvent> getTopEvents() {
        logger.info("getTopEvents() called!");

        return null;
    }

    public Flux<TrackingEvent> generateEventFluxWithRandomId() {

        return null;
    }


    @GetMapping(path = "/{eventId}")
    public Mono<TrackingEvent> getEvent(@PathVariable String eventId) {
        return null;
    }

}
