package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.service;

import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.client.SsoUserRestClient;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.SsoOrchestrationRequestContext;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response.SsoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Service
public class SsoPaymentServiceOrchestrator implements SsoServiceOrchestrator {

    @Autowired
    private SsoUserRestClient userRestClient;

    @Override
    public Mono<SsoOrchestrationRequestContext> create(SsoOrchestrationRequestContext ctx) {
        return this.userRestClient.deductAmount(ctx.getSsoPaymentRequest())
                .doOnNext(ctx::setSsoPaymentResponse)
                .thenReturn(ctx)
                .handle(this.statusHandler());
    }

    @Override
    public Predicate<SsoOrchestrationRequestContext> isSuccess() {
        return ctx -> Objects.nonNull(ctx.getSsoPaymentResponse()) && SsoStatus.SUCCESS.equals(ctx.getSsoStatus());
    }

    @Override
    public Consumer<SsoOrchestrationRequestContext> cancel() {
        return ctx -> Mono.just(ctx)
                .filter(isSuccess())
                .map(SsoOrchestrationRequestContext::getSsoPaymentRequest)
                .flatMap(this.userRestClient::refundAmount)
                .subscribe();
    }
}
