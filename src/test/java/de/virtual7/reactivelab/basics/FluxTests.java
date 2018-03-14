package de.virtual7.reactivelab.basics;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * Created by mihai.dobrescu
 */
public class FluxTests {

    @Test
    public void testCreateFluxJust() {
        //TODO: create a Flux using Flux.just
        Flux<String> flux = Flux.just("1", "2", "3", "4")
                .doOnNext(System.out::println)
                .log();

        flux.subscribe(System.out::println);
    }

    @Test
    public void testCreateFluxFromList() {
        //TODO: create a Flux from a List
        Flux<String> flux = Flux.fromIterable(Arrays.asList("one", "two", "three"));

        flux.subscribe(System.out::println);
    }

    @Test
    public void testFluxCountElements() {
        //TODO: count the elements from a flux
        Mono<Long> count = Flux.fromIterable(Arrays.asList("one", "two", "three")).count();

        count.subscribe(System.out::println);
    }

    @Test
    public void testFluxRange() {
        //TODO: create a Flux using the .range operator. Possible usecases?
        Flux<Integer> flux = Flux.range(0, 100).log();

        flux.subscribe(System.out::println);
    }

    @Test
    public void testCreateFluxUsingGenerate() throws InterruptedException {
        //TODO: create a Flux using Flux.generate()

        Flux<String> flux = Flux.generate(
                AtomicInteger::new, //can be also with () -> 0 initialized, you must then increment the state yourself
                (state, sink) -> {
                    int intState = state.getAndIncrement();
                    sink.next("next is " + state);
                    if(intState == 100) {
                        sink.complete();
                    }
                    return state;
                }, (state) -> System.out.println("last state is: " + state));

        CountDownLatch countDownLatch = new CountDownLatch(1);

        flux.doOnTerminate(countDownLatch::countDown).subscribe(System.out::println);

        countDownLatch.await();
    }

    @Test
    public void testCreateFluxUsingInterval() throws InterruptedException {
        //TODO: create a Flux using Flux.interval()
        Flux<String> flux =
                Flux.interval(Duration.ofMillis(250))
                        .map(input -> {
                            return "event " + input;
                        });

        flux.subscribe(System.out::println);
        Thread.sleep(2100);
    }

    @Test
    public void testCreateFluxFromStream() {
        //TODO: create a Flux using Flux.fromStream()
        Flux<String> flux = Flux.fromStream(Arrays.asList("one", "two", "three").stream());

        flux.subscribe(System.out::println);
    }

    @Test
    public void testZipThem() throws InterruptedException {
        //TODO: create two Flux instances, one using fromStream() one using .interval with the duration of a second
        //zip them and observe the results

        CountDownLatch countDownLatch = new CountDownLatch(1);

        Flux<String> eventFlux = Flux.fromStream(Stream.generate(() -> Instant.now().toString()));
        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));

        Flux<String> result = Flux.zip(eventFlux, durationFlux).map(Tuple2::getT1).doOnComplete(countDownLatch::countDown);

        result.subscribe(System.out::println);

        countDownLatch.await();
    }

    @Test
    public void testSwitchIfEmpty() {
        //TODO: create a Flux instance of your choice and make sure it's empty. Call the switchIfEmpty method on it to supply a fallback
        Flux.empty()
                .switchIfEmpty(Flux.just("1", "2", "3", "4"))
                .switchIfEmpty(Flux.error(new RuntimeException("BOOM!")))
                .subscribe(System.out::println);
    }

    @Test
    public void testHandleAndSkipNulls() {
        //TODO: create a Flux using just and for each value call a method which could return a null
        //use handle() to filter out the nulls
        Flux.just(0, 2, 4, 3, 13, 7)
                .log()
                .handle((i,sink) -> {
                            String value = sometimesIReturnNull(i);
                            if (value != null) {
                                sink.next(value);
                            }
                        }
                ).subscribe(System.out::println);
    }

    private String sometimesIReturnNull(int i) {
        if(i % 2 == 0) {
            return null;
        }
        return String.valueOf(i);
    }

    @Test
    public void testFluxOnErrorResume() throws InterruptedException {
        //TODO: generate a Flux using range and then throw an exception for one of the elements. Use onErrorResume as fallback
        Flux.range(1, 6)
                .map(input -> {
                    if (input != 5) {
                        return "element " + input;
                    }
                    throw new RuntimeException("ERROR!");
                })
                //.onErrorReturn("error!")
                .onErrorResume(RuntimeException.class, (e) -> Flux.just("element 5!", "element 6!"))
                .subscribe(System.out::println, System.err::println, () -> System.out.print("DONE!"));

        Thread.sleep(3000);
    }

}
