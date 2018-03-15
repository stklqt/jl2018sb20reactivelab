package de.virtual7.reactivelab.controller;

import de.virtual7.reactivelab.event.TrackingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    @GetMapping(path = "/latest")
    public Flux<TrackingEvent> getEventsLatest() {
        logger.info("getEventsLatest() called!");

        return null;
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
