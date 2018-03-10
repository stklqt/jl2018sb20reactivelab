package de.virtual7.reactivelab.basics;

import org.junit.Test;

/**
 * Created by mihai.dobrescu
 */
public class FluxTests {


    @Test
    public void testCreateFlux() {
        //TODO: create a Flux from a List
    }

    @Test
    public void testCreateFluxJust() {
        //TODO: create a Flux using Flux.just
    }

    @Test
    public void testRange() {
        //TODO: create a Flux using the .range operator. Possible usecases?
    }

    @Test
    public void testCreateFluxUsingGenerate() {
        //TODO: create a Flux using Stream.generate()
    }

    @Test
    public void testCreateFluxUsingInterval() {
        //TODO: create a Flux using Stream.generate()
    }

    @Test
    public void testCreateFluxFromStream() {
        //TODO: create a Flux using Stream.generate()
    }

    @Test
    public void testZipThem() {
        //TODO: create two Flux instances, one using fromStream() one using .interval with the duration of a second
        // zip them and observe the results
    }

    @Test
    public void testSwitchIfEmpty() {
        //TODO: create a Flux instance of your choice and make sure it's empty. Call the switchIfEmpty method on it to supply a fallback
        // zip them and observe the results
    }

}
