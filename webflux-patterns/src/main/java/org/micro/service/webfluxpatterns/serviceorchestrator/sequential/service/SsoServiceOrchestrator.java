package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.service;

import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.exception.OrderFulfillmentFailure;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.SsoOrchestrationRequestContext;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface SsoServiceOrchestrator {
    Mono<SsoOrchestrationRequestContext> create(SsoOrchestrationRequestContext ctx);
    Predicate<SsoOrchestrationRequestContext> isSuccess();
    Consumer<SsoOrchestrationRequestContext> cancel();

    default BiConsumer<SsoOrchestrationRequestContext, SynchronousSink<SsoOrchestrationRequestContext>> statusHandler() {
        return (ctx, sink) -> {
            if (isSuccess().test(ctx)) {
                sink.next(ctx);
            } else {
                sink.error(new OrderFulfillmentFailure());
            }
        };
    }
}
