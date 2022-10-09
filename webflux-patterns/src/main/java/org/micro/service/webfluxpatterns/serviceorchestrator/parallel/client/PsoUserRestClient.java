package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.client;

import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.request.PsoPaymentRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.PsoPaymentResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.PsoStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PsoUserRestClient {

    private static final String DEDUCT = "deduct";
    private static final String REFUND = "refund";

    private final WebClient webClient;

    public PsoUserRestClient(@Value("${base.url.pso-user}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<PsoPaymentResponse> deductAmount(PsoPaymentRequest psoPaymentRequest) {
        return callUserService(DEDUCT, psoPaymentRequest);
    }

    public Mono<PsoPaymentResponse> refundAmount(PsoPaymentRequest psoPaymentRequest) {
        return callUserService(REFUND, psoPaymentRequest);
    }

    public Mono<PsoPaymentResponse> callUserService(String endpoint, PsoPaymentRequest psoPaymentRequest) {
        return this.webClient
                .post()
                .uri("/" + endpoint)
                .bodyValue(psoPaymentRequest)
                .retrieve()
                .bodyToMono(PsoPaymentResponse.class)
                .onErrorReturn(this.buildErrorPaymentResponse(psoPaymentRequest));
    }

    private PsoPaymentResponse buildErrorPaymentResponse(PsoPaymentRequest request) {
        return PsoPaymentResponse.create(
                request.getUserId(),
                null,
                request.getAmount(),
                PsoStatus.FAILED
        );
    }

}
