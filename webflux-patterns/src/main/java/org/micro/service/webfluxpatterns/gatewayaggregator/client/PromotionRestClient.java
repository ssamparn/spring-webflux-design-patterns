package org.micro.service.webfluxpatterns.gatewayaggregator.client;

import org.micro.service.webfluxpatterns.gatewayaggregator.model.PromotionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PromotionRestClient {

    private final WebClient webClient;

    public PromotionRestClient(@Value("${base.url.promotion}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<PromotionResponse> getPromotion(Integer productId) {
        return this.webClient
                .get()
                .uri("/{promotionId}", productId)
                .retrieve()
                .bodyToMono(PromotionResponse.class);
    }
}
