package org.micro.service.webfluxpatterns.scattergather.client;

import org.micro.service.webfluxpatterns.scattergather.model.FlightResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class DeltaServiceClient {

    private final WebClient webClient;

    public DeltaServiceClient(@Value("${base.url.delta}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<FlightResult> gatherFlights(String source, String destination) {
        return this.webClient
                .get()
                .uri("/{source}/{destination}", source, destination)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.empty())
                .bodyToFlux(FlightResult.class)
                .retry(3)
                .timeout(Duration.ofMillis(250))
                .onErrorResume(ex -> Mono.empty());
    }
}
