package org.micro.service.webfluxpatterns.gatewayaggregator.client;

import org.micro.service.webfluxpatterns.gatewayaggregator.model.ReviewResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ReviewRestClient {

    private final WebClient webClient;

    public ReviewRestClient(@Value("${base.url.review}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<List<ReviewResponse>> getReviews(Integer productId) {
        return this.webClient
                .get()
                .uri("/{reviewId}", productId)
                .retrieve()
                .bodyToFlux(ReviewResponse.class)
                .collectList();
    }
}
