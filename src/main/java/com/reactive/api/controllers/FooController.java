package com.reactive.api.controllers;

import com.reactive.api.models.Foo;
import com.reactive.api.repository.FooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/foo")
public class FooController {

    private final FooRepository fooRepository;

    @Autowired
    public FooController(FooRepository fooRepository) {
        this.fooRepository = fooRepository;
    }

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Foo> getFooEvents() {
        return Flux.interval(Duration.ofSeconds(1)).map(i -> new Foo(i.intValue(), "Foo " + i));
    }
    
    @GetMapping(value="/")
    public Flux<Foo> getFoo() {
        return fooRepository.findAll().take(10);
    }

    @GetMapping("/{id}")
    public Mono<Foo> getFooById(@PathVariable("id") int id) {
        return fooRepository.findById(id);
    }
}
