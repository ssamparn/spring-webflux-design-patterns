package org.micro.service.webfluxpatterns.serviceaggregator.service;

import org.micro.service.webfluxpatterns.serviceaggregator.client.SoInventoryRestClient;
import org.micro.service.webfluxpatterns.serviceaggregator.model.OrchestrationRequestContext;
import org.micro.service.webfluxpatterns.serviceaggregator.model.response.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Service
public class InventoryOrchestrator implements ServiceOrchestrator {

    @Autowired
    private SoInventoryRestClient inventoryRestClient;

    @Override
    public Mono<OrchestrationRequestContext> create(OrchestrationRequestContext ctx) {
        return this.inventoryRestClient.deductInventory(ctx.getInventoryRequest())
                .doOnNext(ctx::setInventoryResponse)
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
                .map(OrchestrationRequestContext::getInventoryRequest)
                .flatMap(this.inventoryRestClient::restoreInventory)
                .subscribe();
    }
}
