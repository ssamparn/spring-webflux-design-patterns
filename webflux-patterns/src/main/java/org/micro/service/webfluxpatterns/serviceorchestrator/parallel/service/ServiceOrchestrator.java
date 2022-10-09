package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.service;

import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.OrchestrationRequestContext;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface ServiceOrchestrator {
    Mono<OrchestrationRequestContext> create(OrchestrationRequestContext ctx);
    Predicate<OrchestrationRequestContext> isSuccess();
    Consumer<OrchestrationRequestContext> cancel();
}
