package de.virtual7.reactivelab.basics;

import org.junit.Test;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mihai.dobrescu
 */
public class MonoTests {

    @Test
    public void testCreateVoidMono() {
        Mono<Object> voidMono = Mono.empty();
        voidMono.log().subscribe(System.out::println);

    }

    @Test
    public void testCreateScalarMono() {
        Mono<Object> scalarMono = Mono.just("One Mono");
        scalarMono.log().subscribe(System.out::println);

    }

    @Test
    public void testFlatMapMono() {
        Mono<String> flatMono = Mono.just("Hello");
        flatMono.flatMap( s -> Mono.just(s + ", World")).log().subscribe(System.out::println);
    }


    @Test
    public void testCreateFluxFromMono() {
        Mono<List<String>> stringList = Mono.just(Arrays.asList("a", "bb", "ccc"));
        stringList.flatMapMany(Flux::fromIterable).log().subscribe(System.out::println);
    }

    @Test
    public void testMergeMonos() {
        Mono<String> mono1 = Mono.just("Eins");
        Mono<String> mono2 = Mono.just("Zwei");

        Flux<String> stringFlux = mono1.mergeWith(mono2);

        stringFlux.log().subscribe(System.out::println);

    }



    @Test
    public void testZipMonos() {
        Mono<String> mono1 = Mono.just("Eins");
        Mono<String> mono2 = Mono.just("Zwei");

        Mono<Tuple2<String, String>> zip = Mono.zip(mono1, mono2);  // Max 8 Monos im ZIP

        zip.log().subscribe(System.out::println);
    }

    @Test
    public void testBlockingMono() {
        Mono<String> foooo = Mono.just("foooo");
        foooo.block(); //use with caution !

        foooo.log().subscribe(System.out::println);
    }
}
