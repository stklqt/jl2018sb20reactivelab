package de.virtual7.reactivelab.basics;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;

/**
 * Created by mihai.dobrescu
 */
public class MonoTests {

    @Test
    public void testCreateVoidMono() {
        //TODO: create an empty Mono
    }

    @Test
    public void testCreateScalarMono() {
        //TODO: create a Mono with the "Hello World" value
    }

    @Test
    public void testFlatMapMono() {
        //TODO: create a Mono with the "Hello" value and append "World" to it using the flatMap operator
    }

    @Test
    public void testCreateFluxFromMono() {
        //TODO: Create a Mono from a List and then convert it to a Flux
    }

    @Test
    public void testMergeMonos() {
        //TODO: Create two Mono instances and merge them, what do you get ?
    }

    @Test
    public void testZipMonos() {
        //TODO: Create two Mono instances and zip them
    }

    @Test
    public void testBlockingMono() {
        //TODO: Create a Mono instance and convert it to a blocking call
    }
}
