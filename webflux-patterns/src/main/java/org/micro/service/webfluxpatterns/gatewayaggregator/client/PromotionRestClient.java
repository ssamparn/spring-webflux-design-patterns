package org.micro.service.webfluxpatterns.gatewayaggregator.client;

import org.micro.service.webfluxpatterns.gatewayaggregator.model.PromotionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

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
                .bodyToMono(PromotionResponse.class)
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
