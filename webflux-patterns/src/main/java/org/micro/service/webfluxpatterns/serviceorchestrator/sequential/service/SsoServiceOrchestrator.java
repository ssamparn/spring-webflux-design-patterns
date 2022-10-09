package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.service;

import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.SsoOrchestrationRequestContext;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface SsoServiceOrchestrator {
    Mono<SsoOrchestrationRequestContext> create(SsoOrchestrationRequestContext ctx);
    Predicate<SsoOrchestrationRequestContext> isSuccess();
    Consumer<SsoOrchestrationRequestContext> cancel();
}
