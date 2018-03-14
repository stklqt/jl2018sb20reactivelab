package de.virtual7.reactivelab.basics;

import org.junit.Test;

/**
 * Created by mihai.dobrescu
 */
public class FluxTests {

    @Test
    public void testCreateFluxJust() {
        //TODO: create a Flux using Flux.just
    }

    @Test
    public void testCreateFluxFromList() {
        //TODO: create a Flux from a List
    }

    @Test
    public void testFluxCountElements() {
        //TODO: count the elements from a flux
    }

    @Test
    public void testFluxRange() {
        //TODO: create a Flux using the .range operator. Possible usecases?
    }

    @Test
    public void testCreateFluxUsingGenerate() throws InterruptedException {
        //TODO: create a Flux using Flux.generate()
    }

    @Test
    public void testCreateFluxUsingInterval() throws InterruptedException {
        //TODO: create a Flux using Flux.interval()
    }

    @Test
    public void testCreateFluxFromStream() {
        //TODO: create a Flux using Flux.fromStream()
    }

    @Test
    public void testZipThem() throws InterruptedException {
        //TODO: create two Flux instances, one using fromStream() one using .interval with the duration of a second
        //zip them and observe the results
    }

    @Test
    public void testSwitchIfEmpty() {
        //TODO: create a Flux instance of your choice and make sure it's empty. Call the switchIfEmpty method on it to supply a fallback
    }

    @Test
    public void testHandleAndSkipNulls() {
        //TODO: create a Flux using just and for each value call a method which could return a null
        //use handle() to filter out the nulls
    }

    @Test
    public void testFluxOnErrorResume() throws InterruptedException {
        //TODO: generate a Flux using range and then throw an exception for one of the elements. Use onErrorResume as fallback
    }

}
