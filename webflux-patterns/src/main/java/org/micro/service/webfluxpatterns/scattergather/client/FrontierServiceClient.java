package org.micro.service.webfluxpatterns.scattergather.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.micro.service.webfluxpatterns.scattergather.model.FlightResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class FrontierServiceClient {

    private final WebClient webClient;

    public FrontierServiceClient(@Value("${base.url.frontier}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<FlightResult> gatherFlights(String source, String destination) {
        return this.webClient
                .post()
                .bodyValue(FrontierRequest.create(source, destination))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
                .bodyToFlux(FlightResult.class)
                .retry(3)
                .timeout(Duration.ofMillis(250))
                .onErrorResume(ex -> Mono.empty());
    }

    @Data
    @AllArgsConstructor(staticName = "create")
    private static class FrontierRequest {
        private String source;
        private String destination;
    }
}
