package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.client;

import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.PsoProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class PsoProductRestClient {

    private final WebClient webClient;

    public PsoProductRestClient(@Value("${base.url.pso-product}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<PsoProductResponse> getProduct(Integer productId) {
        return this.webClient
                .get()
                .uri("/{productId}", productId)
                .retrieve()
                .bodyToMono(PsoProductResponse.class)
                .timeout(Duration.ofMillis(500))
                .onErrorResume(ex -> Mono.empty());
    }

}
