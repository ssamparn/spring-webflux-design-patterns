package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.service;

import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.client.SsoInventoryRestClient;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.SsoOrchestrationRequestContext;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response.SsoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Service
public class SsoInventoryServiceOrchestrator implements SsoServiceOrchestrator {

    @Autowired
    private SsoInventoryRestClient inventoryRestClient;

    @Override
    public Mono<SsoOrchestrationRequestContext> create(SsoOrchestrationRequestContext ctx) {
        return this.inventoryRestClient.deductInventory(ctx.getInventoryRequest())
                .doOnNext(ctx::setSsoInventoryResponse)
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
                .map(SsoOrchestrationRequestContext::getInventoryRequest)
                .flatMap(this.inventoryRestClient::restoreInventory)
                .subscribe();
    }
}
