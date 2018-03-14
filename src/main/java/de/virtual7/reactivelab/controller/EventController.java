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

    @GetMapping(path = "", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<TrackingEvent> getEvents() {
        logger.info("getEvents() called!");

        return Flux.interval(Duration.ofSeconds(1))
                .map(input -> {
                    return new TrackingEvent(input,
                            TrackingEventType.getRandomType(),
                            new BigDecimal(Math.random(), new MathContext(3)),
                            Instant.now());
                }).log();
    }

    @GetMapping(path = "/latest", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<TrackingEvent> getEventsLatest() {
        logger.info("getEventsLatest() called!");

        return Flux.range(0, 100)
                .map(i -> new TrackingEvent(new Long(i),
                                    TrackingEventType.getRandomType(),
                                    new BigDecimal(Math.random(), new MathContext(3)),
                                    Instant.now()));

    }

    @GetMapping(path = "/top", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<TrackingEvent> getTopEvents() {
        logger.info("getTopEvents() called!");

        return Flux.range(0, 100)
                .flatMap(integer -> generateEventFluxWithRandomId());
    }

    public Flux<TrackingEvent> generateEventFluxWithRandomId() {
        Random random = new Random();
        Integer randomInt = random.nextInt(300);
        logger.info("Generated random int " + randomInt);
        return
                Flux.fromArray(TrackingEventType.values())
                        .map(type -> new TrackingEvent(new Long(randomInt),
                                type,
                                new BigDecimal(Math.random(), new MathContext(3)),
                                Instant.now()));
    }


    @GetMapping(path = "/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TrackingEvent> getEvent(@PathVariable String eventId) {
        logger.info("getEvent() called!");

        return Mono.just(
                new TrackingEvent(
                        Long.parseLong(eventId),
                        TrackingEventType.getRandomType(),
                        new BigDecimal(Math.random(), new MathContext(3)),
                        Instant.now()));
    }

}
