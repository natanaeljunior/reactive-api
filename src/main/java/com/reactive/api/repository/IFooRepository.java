package com.reactive.api.repository;

import com.reactive.api.models.Foo;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface IFooRepository {
    Flux<Foo> findAll();

    Mono<Foo> findById(int id);
}

