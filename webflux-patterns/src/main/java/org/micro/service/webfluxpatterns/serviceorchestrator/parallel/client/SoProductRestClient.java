package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.client;

import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.SoProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class SoProductRestClient {

    private final WebClient webClient;

    public SoProductRestClient(@Value("${base.url.so-product}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<SoProductResponse> getProduct(Integer productId) {
        return this.webClient
                .get()
                .uri("/{productId}", productId)
                .retrieve()
                .bodyToMono(SoProductResponse.class)
                .onErrorResume(ex -> Mono.empty());
    }

}
