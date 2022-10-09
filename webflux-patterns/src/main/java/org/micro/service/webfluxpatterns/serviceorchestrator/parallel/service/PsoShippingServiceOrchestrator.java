package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.service;

import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.client.PsoShippingRestClient;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.PsoOrchestrationRequestContext;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.PsoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Service
public class PsoShippingServiceOrchestrator implements PsoServiceOrchestrator {

    @Autowired
    private PsoShippingRestClient shippingRestClient;

    @Override
    public Mono<PsoOrchestrationRequestContext> create(PsoOrchestrationRequestContext ctx) {
        return this.shippingRestClient.scheduleShipping(ctx.getPsoShippingRequest())
                .doOnNext(ctx::setPsoShippingResponse)
                .thenReturn(ctx);
    }

    @Override
    public Predicate<PsoOrchestrationRequestContext> isSuccess() {
        return ctx -> PsoStatus.SUCCESS.equals(ctx.getPsoStatus());
    }

    @Override
    public Consumer<PsoOrchestrationRequestContext> cancel() {
        return ctx -> Mono.just(ctx)
                .filter(isSuccess())
                .map(PsoOrchestrationRequestContext::getPsoShippingRequest)
                .flatMap(this.shippingRestClient::cancelShipping)
                .subscribe();
    }
}
