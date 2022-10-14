package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.client;

import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response.SsoProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class SsoProductRestClient {

    private final WebClient webClient;

    public SsoProductRestClient(@Value("${base.url.sso-product}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<SsoProductResponse> getProduct(Integer productId) {
        return this.webClient
                .get()
                .uri("/{productId}", productId)
                .retrieve()
                .bodyToMono(SsoProductResponse.class)
                .timeout(Duration.ofMillis(500))
                .onErrorResume(ex -> Mono.empty());
    }

}
