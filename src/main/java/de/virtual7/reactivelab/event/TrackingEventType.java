package de.virtual7.reactivelab.event;

/**
 * Created by mihai.dobrescu.
 */
public enum TrackingEventType {

    DELIVERED, PROCESSING, ARRIVED, DISPATCHED;

    public static TrackingEventType getRandomType() {
        return values()[(int) (Math.random() * values().length)];
    }
}