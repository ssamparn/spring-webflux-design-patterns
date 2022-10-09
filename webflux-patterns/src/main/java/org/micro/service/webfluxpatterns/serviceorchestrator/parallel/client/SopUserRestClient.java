package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.client;

import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.request.PaymentRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.PaymentResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class SopUserRestClient {

    private static final String DEDUCT = "deduct";
    private static final String REFUND = "refund";

    private final WebClient webClient;

    public SopUserRestClient(@Value("${base.url.sop-user}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<PaymentResponse> deductAmount(PaymentRequest paymentRequest) {
        return callUserService(DEDUCT, paymentRequest);
    }

    public Mono<PaymentResponse> refundAmount(PaymentRequest paymentRequest) {
        return callUserService(REFUND, paymentRequest);
    }

    public Mono<PaymentResponse> callUserService(String endpoint, PaymentRequest paymentRequest) {
        return this.webClient
                .post()
                .uri("/" + endpoint)
                .bodyValue(paymentRequest)
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .onErrorReturn(this.buildErrorPaymentResponse(paymentRequest));
    }

    private PaymentResponse buildErrorPaymentResponse(PaymentRequest request) {
        return PaymentResponse.create(
                request.getUserId(),
                null,
                request.getAmount(),
                Status.FAILED
        );
    }

}
