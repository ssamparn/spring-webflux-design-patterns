package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.service;

import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.client.PsoInventoryRestClient;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.PsoOrchestrationRequestContext;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.PsoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Service
public class PsoInventoryServiceOrchestrator implements PsoServiceOrchestrator {

    @Autowired
    private PsoInventoryRestClient inventoryRestClient;

    @Override
    public Mono<PsoOrchestrationRequestContext> create(PsoOrchestrationRequestContext ctx) {
        return this.inventoryRestClient.deductInventory(ctx.getInventoryRequest())
                .doOnNext(ctx::setPsoInventoryResponse)
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
                .map(PsoOrchestrationRequestContext::getInventoryRequest)
                .flatMap(this.inventoryRestClient::restoreInventory)
                .subscribe();
    }
}
