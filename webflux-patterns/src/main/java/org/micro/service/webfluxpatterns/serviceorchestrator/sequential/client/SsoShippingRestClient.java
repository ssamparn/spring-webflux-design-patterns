package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.client;

import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.request.SsoShippingRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response.SsoShippingResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response.SsoStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class SsoShippingRestClient {

    private static final String SCHEDULE = "schedule";
    private static final String CANCEL = "cancel";

    private final WebClient webClient;

    public SsoShippingRestClient(@Value("${base.url.sso-shipping}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<SsoShippingResponse> scheduleShipping(SsoShippingRequest psoShippingRequest) {
        return callShippingService(SCHEDULE, psoShippingRequest);
    }

    public Mono<SsoShippingResponse> cancelShipping(SsoShippingRequest psoShippingRequest) {
        return callShippingService(CANCEL, psoShippingRequest);
    }

    public Mono<SsoShippingResponse> callShippingService(String endpoint, SsoShippingRequest ssoShippingRequest) {
        return this.webClient
                .post()
                .uri("/" + endpoint)
                .bodyValue(ssoShippingRequest)
                .retrieve()
                .bodyToMono(SsoShippingResponse.class)
                .onErrorReturn(this.buildErrorShippingResponse(ssoShippingRequest));
    }

    private SsoShippingResponse buildErrorShippingResponse(SsoShippingRequest request) {
        return SsoShippingResponse.create(
                request.getOrderId(),
                request.getQuantity(),
                SsoStatus.FAILED,
                null,
                null
        );
    }
}
