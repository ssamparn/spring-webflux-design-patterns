package org.micro.service.webfluxpatterns.scattergather.client;

import org.micro.service.webfluxpatterns.scattergather.model.FlightResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class JetBlueServiceClient {

    private static final String JETBLUE = "JETBLUE";

    private final WebClient webClient;

    public JetBlueServiceClient(@Value("${base.url.jet-blue}") String baseUrl) {
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
                .doOnNext(flightResult -> this.normalizeResponse(flightResult, source, destination))
                .retry(3)
                .onErrorResume(ex -> Mono.empty());
    }

    private void normalizeResponse(FlightResult flightResult, String source, String destination) {
        flightResult.setSource(source);
        flightResult.setDestination(destination);
        flightResult.setAirLine(JETBLUE);
    }
}
