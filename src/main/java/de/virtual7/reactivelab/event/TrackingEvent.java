package de.virtual7.reactivelab.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Created by mihai.dobrescu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackingEvent {

    private Long eventID;
    private TrackingEventType eventType;
    private BigDecimal eventValue;
    private Instant eventInstant;

}
