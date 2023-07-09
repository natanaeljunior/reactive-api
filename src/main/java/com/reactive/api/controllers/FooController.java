package com.reactive.api.controllers;

import com.reactive.api.models.Foo;
import com.reactive.api.repository.FooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/api")
public class FooController {

    private final FooRepository fooRepository;

    @Autowired
    public FooController(FooRepository fooRepository) {
        this.fooRepository = fooRepository;
    }

    @GetMapping("/foo")
    public Flux<Foo> getFoo() {
        return fooRepository.findAll()
                .delayElements(Duration.ofSeconds(1))
                .take(10);
    }

    @GetMapping("/foo/{id}")
    public Mono<Foo> getFooById(@PathVariable("id") int id) {
        return fooRepository.findById(id);
    }
    
}
