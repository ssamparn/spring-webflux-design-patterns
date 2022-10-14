package org.micro.service.webfluxpatterns.gatewayaggregator.client;

import org.micro.service.webfluxpatterns.gatewayaggregator.model.PromotionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.LocalDate;

@Component
public class GaPromotionRestClient {

    private final WebClient webClient;

    public GaPromotionRestClient(@Value("${base.url.promotion}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<PromotionResponse> getPromotion(Integer productId) {
        return this.webClient
                .get()
                .uri("/{promotionId}", productId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.empty())
                .bodyToMono(PromotionResponse.class)
                .retryWhen(Retry.fixedDelay(3, Duration.ofMillis(50)))
                .timeout(Duration.ofMillis(500))
                .onErrorReturn(noPromotionResponse());
    }

    private PromotionResponse noPromotionResponse() {
        return PromotionResponse.create(
                -1,
                "no promotion code",
                "product type none",
                0,
                LocalDate.now().toString()
        );
    }
}
