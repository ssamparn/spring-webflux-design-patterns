package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.service;

import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.PsoOrchestrationRequestContext;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface PsoServiceOrchestrator {
    Mono<PsoOrchestrationRequestContext> create(PsoOrchestrationRequestContext ctx);
    Predicate<PsoOrchestrationRequestContext> isSuccess();
    Consumer<PsoOrchestrationRequestContext> cancel();
}
