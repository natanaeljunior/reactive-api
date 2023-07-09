package com.reactive.api.repository;

import com.reactive.api.models.Foo;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Repository
public class FooRepository implements IFooRepository {

    private final Map<Integer, Foo> fooMap;

    public FooRepository() {
        fooMap = new HashMap<>();
        generateSampleData();
    }

    private void generateSampleData() {
        for (var i = 1; i <= 10; i++) {
            fooMap.put(i, new Foo(i, "Foo " + i));
        }
    }

    @Override
    public Flux<Foo> findAll() {
        return Flux.fromIterable(fooMap.values());
    }

    @Override
    public Mono<Foo> findById(int id) {
        return Mono.justOrEmpty(fooMap.get(id));
    }
}
