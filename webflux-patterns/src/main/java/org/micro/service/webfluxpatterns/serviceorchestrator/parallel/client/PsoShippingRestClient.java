package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.client;

import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.request.PsoShippingRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.PsoShippingResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.PsoStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class PsoShippingRestClient {

    private static final String SCHEDULE = "schedule";
    private static final String CANCEL = "cancel";

    private final WebClient webClient;

    public PsoShippingRestClient(@Value("${base.url.pso-shipping}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<PsoShippingResponse> scheduleShipping(PsoShippingRequest psoShippingRequest) {
        return callShippingService(SCHEDULE, psoShippingRequest);
    }

    public Mono<PsoShippingResponse> cancelShipping(PsoShippingRequest psoShippingRequest) {
        return callShippingService(CANCEL, psoShippingRequest);
    }

    public Mono<PsoShippingResponse> callShippingService(String endpoint, PsoShippingRequest psoShippingRequest) {
        return this.webClient
                .post()
                .uri("/" + endpoint)
                .bodyValue(psoShippingRequest)
                .retrieve()
                .bodyToMono(PsoShippingResponse.class)
                .timeout(Duration.ofMillis(500))
                .onErrorReturn(this.buildErrorShippingResponse(psoShippingRequest));
    }

    private PsoShippingResponse buildErrorShippingResponse(PsoShippingRequest request) {
        return PsoShippingResponse.create(
                request.getOrderId(),
                request.getQuantity(),
                PsoStatus.FAILED,
                null,
                null
        );
    }
}
