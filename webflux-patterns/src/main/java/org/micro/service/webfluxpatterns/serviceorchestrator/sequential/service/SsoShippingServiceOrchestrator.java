package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.service;

import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.client.SsoShippingRestClient;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.SsoOrchestrationRequestContext;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response.SsoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Service
public class SsoShippingServiceOrchestrator implements SsoServiceOrchestrator {

    @Autowired
    private SsoShippingRestClient shippingRestClient;

    @Override
    public Mono<SsoOrchestrationRequestContext> create(SsoOrchestrationRequestContext ctx) {
        return this.shippingRestClient.scheduleShipping(ctx.getSsoShippingRequest())
                .doOnNext(ctx::setSsoShippingResponse)
                .thenReturn(ctx);
    }

    @Override
    public Predicate<SsoOrchestrationRequestContext> isSuccess() {
        return ctx -> SsoStatus.SUCCESS.equals(ctx.getSsoStatus());
    }

    @Override
    public Consumer<SsoOrchestrationRequestContext> cancel() {
        return ctx -> Mono.just(ctx)
                .filter(isSuccess())
                .map(SsoOrchestrationRequestContext::getSsoShippingRequest)
                .flatMap(this.shippingRestClient::cancelShipping)
                .subscribe();
    }
}
