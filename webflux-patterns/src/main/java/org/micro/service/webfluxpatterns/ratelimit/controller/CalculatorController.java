package org.micro.service.webfluxpatterns.ratelimit.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class CalculatorController {

    @RateLimiter(name = "calculator-service", fallbackMethod = "fallback")
    @GetMapping("/calculate/{input}")
    public Mono<ResponseEntity<Integer>> doubleInput(@PathVariable Integer input) {
        return Mono.fromSupplier(() -> input * 2)
                .map(ResponseEntity::ok);
    }

    public Mono<ResponseEntity<String>> fallback(@PathVariable Integer input, Throwable t) {
        return Mono.just(ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(t.getMessage()));
    }
}
