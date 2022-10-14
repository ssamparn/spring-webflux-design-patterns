package org.micro.service.webfluxpatterns.scattergather.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.micro.service.webfluxpatterns.gatewayaggregator.model.ProductResponse;
import org.micro.service.webfluxpatterns.scattergather.model.FlightResult;
import org.springframework.beans.factory.annotation.Value;
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
                .bodyToFlux(FlightResult.class)
                .timeout(Duration.ofMillis(500))
                .onErrorResume(ex -> Mono.empty());
    }

    @Data
    @AllArgsConstructor(staticName = "create")
    private static class FrontierRequest {
        private String source;
        private String destination;
    }
}
