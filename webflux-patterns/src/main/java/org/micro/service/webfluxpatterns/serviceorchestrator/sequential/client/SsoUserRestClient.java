package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.client;

import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.request.SsoPaymentRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response.SsoPaymentResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response.SsoStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class SsoUserRestClient {

    private static final String DEDUCT = "deduct";
    private static final String REFUND = "refund";

    private final WebClient webClient;

    public SsoUserRestClient(@Value("${base.url.sso-user}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<SsoPaymentResponse> deductAmount(SsoPaymentRequest psoPaymentRequest) {
        return callUserService(DEDUCT, psoPaymentRequest);
    }

    public Mono<SsoPaymentResponse> refundAmount(SsoPaymentRequest psoPaymentRequest) {
        return callUserService(REFUND, psoPaymentRequest);
    }

    public Mono<SsoPaymentResponse> callUserService(String endpoint, SsoPaymentRequest ssoPaymentRequest) {
        return this.webClient
                .post()
                .uri("/" + endpoint)
                .bodyValue(ssoPaymentRequest)
                .retrieve()
                .bodyToMono(SsoPaymentResponse.class)
                .onErrorReturn(this.buildErrorPaymentResponse(ssoPaymentRequest));
    }

    private SsoPaymentResponse buildErrorPaymentResponse(SsoPaymentRequest request) {
        return SsoPaymentResponse.create(
                null,
                request.getUserId(),
                null,
                request.getAmount(),
                SsoStatus.FAILED
        );
    }

}
