package org.micro.service.webfluxpatterns.serviceaggregator.service;

import org.micro.service.webfluxpatterns.serviceaggregator.client.SoShippingRestClient;
import org.micro.service.webfluxpatterns.serviceaggregator.model.OrchestrationRequestContext;
import org.micro.service.webfluxpatterns.serviceaggregator.model.response.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Service
public class ShippingOrchestrator implements ServiceOrchestrator {

    @Autowired
    private SoShippingRestClient shippingRestClient;

    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext ctx) {
        return this.shippingRestClient.scheduleShipping(ctx.getShippingRequest())
                .doOnNext(ctx::setShippingResponse)
                .thenReturn(ctx);
    }

    @Override
    public Predicate<OrchestrationRequestContext> isSuccess() {
        return ctx -> Status.SUCCESS.equals(ctx.getStatus());
    }

    @Override
    public Consumer<OrchestrationRequestContext> cancel() {
        return ctx -> Mono.just(ctx)
                .filter(isSuccess())
                .map(OrchestrationRequestContext::getShippingRequest)
                .flatMap(this.shippingRestClient::cancelShipping)
                .subscribe();
    }
}
