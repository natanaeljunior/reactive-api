package com.reactive.api.controllers;

import com.reactive.api.models.Foo;
import com.reactive.api.repository.FooRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
@WebFluxTest(FooController.class)
public class FooControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private FooRepository fooRepository;



    @Test
    public void testGetFooEvents() {
        FooController fooController = new FooController(fooRepository);

        Flux<Foo> fooFlux = fooController.getFooEvents().take(5);

        StepVerifier.create(fooFlux)
                .expectNextMatches(foo -> foo.getId() == 0 && foo.getName().equals("Foo 0"))
                .expectNextMatches(foo -> foo.getId() == 1 && foo.getName().equals("Foo 1"))
                .expectNextMatches(foo -> foo.getId() == 2 && foo.getName().equals("Foo 2"))
                .expectNextMatches(foo -> foo.getId() == 3 && foo.getName().equals("Foo 3"))
                .expectNextMatches(foo -> foo.getId() == 4 && foo.getName().equals("Foo 4"))
                .expectComplete()
                .verify();
    }
    
    @Test
    public void testGetFoo() {
        Foo foo1 = new Foo(1, "Foo 1");
        Foo foo2 = new Foo(2, "Foo 2");
        Flux<Foo> fooFlux = Flux.just(foo1, foo2).delayElements(Duration.ofSeconds(1));

        Mockito.when(fooRepository.findAll()).thenReturn(fooFlux);

        webTestClient.get()
                .uri("/foo/")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Foo.class)
                .hasSize(2);
    }

    @Test
    public void testGetFooById() {
        Foo foo = new Foo(1, "Foo 1");
        int id = 1;

        Mockito.when(fooRepository.findById(id)).thenReturn(Mono.just(foo));

        webTestClient.get()
                .uri("/foo/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Foo.class);
    }
}
