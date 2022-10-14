package org.micro.service.webfluxpatterns.scattergather.client;

import org.micro.service.webfluxpatterns.scattergather.model.FlightResult;
import org.springframework.beans.factory.annotation.Value;
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
                .bodyToFlux(FlightResult.class)
                .timeout(Duration.ofMillis(500))
                .onErrorResume(ex -> Mono.empty());
    }
}
