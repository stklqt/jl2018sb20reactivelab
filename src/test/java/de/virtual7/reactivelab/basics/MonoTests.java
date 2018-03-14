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
        Mono<Void> mono = Mono.empty();

        mono.log().subscribe();
    }

    @Test
    public void testCreateScalarMono() {
        //TODO: create a Mono with the "Hello World" value
        Mono<String> mono = Mono.just("Hello, World");

        mono.subscribe(System.out::println);
    }

    @Test
    public void testFlatMapMono() {
        //TODO: create a Mono with the "Hello" value and append "World" to it using the flatMap operator
        Mono<String> mono = Mono.just("Hello ")
                .flatMap(s -> Mono.just(String.format("%s, World!", s)));

        mono.subscribe(System.out::println);
    }

    @Test
    public void testCreateFluxFromMono() {
        //TODO: Create a Mono from a List and then convert it to a Flux
        Flux<String> flux = Mono.just(Arrays.asList("A", "B", "C"))
                .flatMapMany(Flux::fromIterable);

        flux.subscribe(System.out::println);
    }

    @Test
    public void testMergeMonos() {
        //TODO: Create two Mono instances and merge them, what do you get ?
        Mono<String> hello = Mono.just("Hello,");
        Mono<String> world = Mono.just("World");

        Flux<String> flux = Mono.just("Hello ").mergeWith(Mono.just("World"));
        Flux<String> flux2 = Flux.merge(hello, world);

        flux2.subscribe(System.out::println);
    }

    @Test
    public void testZipMonos() {
        //TODO: Create two Mono instances and zip them
        Mono<String> monoOne = Mono.just("Hello ");
        Mono<String> monoTwo = Mono.just("World");

        Mono<String> mono = Mono.zip(monoOne, monoTwo)
                .map(tuple -> tuple.getT1() + tuple.getT2());

        mono.subscribe(System.out::println);
    }

    @Test
    public void testBlockingMono() {
        //TODO: Create a Mono instance and convert it to a blocking call
        String value = Mono.just("Blocking call").block();

        //maybe with a delay
        String mightBeEmpty = Mono.just("Blocking call")
                .delayElement(Duration.ofSeconds(1))
                .block(Duration.ofSeconds(2));

        System.out.println(mightBeEmpty);
    }
}
