package de.virtual7.reactivelab.basics;

import org.junit.Test;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by mihai.dobrescu
 */
public class FluxTests {

    @Test
    public void testCreateFluxJust() {
        Flux<String> stringFlux = Flux.just("aaa", "bbb", "ccc");
        stringFlux.log().subscribe(System.out::println);
    }

    @Test
    public void testCreateFluxFromList() {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc");
//        Flux<List<String>> listFlux = Flux.just(list); //FIXME, falsch wäre Flux mit Monos
        Flux<String> listFlux = Flux.fromIterable(list);

        listFlux.log().subscribe(System.out::println);
    }

    @Test
    public void testFluxCountElements() {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "dddd");
        Flux<String> listFlux = Flux.fromIterable(list);

        Mono<Long> count = listFlux.count();
        count.log().subscribe(System.out::println);

        //Achtung bei Inline, .mit count ist man u.u. aus dem Stream heraus
    }

    @Test
    public void testFluxRange() {
        Flux<Integer> rangeFlux = Flux.range(21, 22); //von 21 bis 42 :-)
        rangeFlux.log().subscribe(System.out::println);

    }

    @Test
    public void testCreateFluxUsingGenerate() throws InterruptedException {
        Flux<Object> generate = Flux.generate(
                () -> 0,
                (state, sink) -> {
                    sink.next(state);
                    if(state == 1000) {
                        sink.complete();
                    }
                    return state + 1;
                }

        );
        CountDownLatch cdl = new CountDownLatch(1);
        generate.doOnComplete(cdl::countDown).subscribe(System.out::println);

        cdl.await();
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
