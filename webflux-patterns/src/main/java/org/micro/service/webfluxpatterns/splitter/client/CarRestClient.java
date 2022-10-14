package org.micro.service.webfluxpatterns.splitter.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.micro.service.webfluxpatterns.splitter.model.request.CarReservationRequest;
import org.micro.service.webfluxpatterns.splitter.model.response.CarReservationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
@Service
public class CarRestClient {

    private final WebClient carClient;

    public CarRestClient(@Value("${base.url.car}") String baseUrl) {
        this.carClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @CircuitBreaker(name = "car-service", fallbackMethod = "fallbackCar")
    public Flux<CarReservationResponse> reserve(Flux<CarReservationRequest> carRequestFlux) {
        return this.carClient
                .post()
                .body(carRequestFlux, CarReservationRequest.class)
                .retrieve()
                .bodyToFlux(CarReservationResponse.class)
                .timeout(Duration.ofMillis(500));
    }

    public Flux<CarReservationResponse> fallbackCar(Flux<CarReservationRequest> carRequestFlux, Throwable t) {
        log.warn("fallback car is called");
        return Flux.empty();
    }
}
