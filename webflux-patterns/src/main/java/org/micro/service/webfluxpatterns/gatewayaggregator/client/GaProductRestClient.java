package org.micro.service.webfluxpatterns.gatewayaggregator.client;

import org.micro.service.webfluxpatterns.gatewayaggregator.model.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class GaProductRestClient {

    private final WebClient webClient;

    public GaProductRestClient(@Value("${base.url.ga-product}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<ProductResponse> getProduct(Integer productId) {
        return this.webClient
                .get()
                .uri("/{productId}", productId)
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .timeout(Duration.ofMillis(500))
                .onErrorResume(ex -> Mono.empty());
    }
}
