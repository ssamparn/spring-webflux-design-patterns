package org.micro.service.webfluxpatterns.bulkhead.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@RestController
public class FibonacciController {

    private final Scheduler scheduler = Schedulers.newParallel("fibonacci", 6);

    @GetMapping("/fibonacci/{input}")
    public Mono<ResponseEntity<Long>> fibonacci(@PathVariable Long input) {
        return Mono.fromSupplier(() -> findFibonacci(input))
                .subscribeOn(scheduler)
                .map(ResponseEntity::ok);
    }

    private Long findFibonacci(Long input) {
        if (input < 2) return input;
        return findFibonacci(input - 1) + findFibonacci(input - 2);
    }
}
