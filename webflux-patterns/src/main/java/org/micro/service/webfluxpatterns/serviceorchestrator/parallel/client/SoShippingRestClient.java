package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.client;

import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.request.ShippingRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.ShippingResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class SoShippingRestClient {

    private static final String SCHEDULE = "schedule";
    private static final String CANCEL = "cancel";

    private final WebClient webClient;

    public SoShippingRestClient(@Value("${base.url.so-shipping}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<ShippingResponse> scheduleShipping(ShippingRequest shippingRequest) {
        return callShippingService(SCHEDULE, shippingRequest);
    }

    public Mono<ShippingResponse> cancelShipping(ShippingRequest shippingRequest) {
        return callShippingService(CANCEL, shippingRequest);
    }

    public Mono<ShippingResponse> callShippingService(String endpoint, ShippingRequest shippingRequest) {
        return this.webClient
                .post()
                .uri("/" + endpoint)
                .bodyValue(shippingRequest)
                .retrieve()
                .bodyToMono(ShippingResponse.class)
                .onErrorReturn(this.buildErrorShippingResponse(shippingRequest));
    }

    private ShippingResponse buildErrorShippingResponse(ShippingRequest request) {
        return ShippingResponse.create(
                request.getOrderId(),
                request.getQuantity(),
                Status.FAILED,
                null,
                null
        );
    }
}
