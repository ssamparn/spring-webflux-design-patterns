package org.micro.service.webfluxpatterns.gatewayaggregator.client;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.micro.service.webfluxpatterns.gatewayaggregator.model.ReviewResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Component
public class GaReviewRestClient {

    private final WebClient webClient;

    public GaReviewRestClient(@Value("${base.url.review}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @RateLimiter(name = "review-service", fallbackMethod = "fallback")
    public Mono<List<ReviewResponse>> getReviews(Integer productId) {
        return this.webClient
                .get()
                .uri("/{reviewId}", productId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.empty())
                .bodyToFlux(ReviewResponse.class)
                .collectList()
                .retryWhen(Retry.fixedDelay(3, Duration.ofMillis(50)))
                .timeout(Duration.ofMillis(500))
                .onErrorReturn(Collections.emptyList());
    }

    public Mono<List<ReviewResponse>> fallback(Integer productId, Throwable t) {
        return Mono.just(Collections.emptyList());
    }
}
