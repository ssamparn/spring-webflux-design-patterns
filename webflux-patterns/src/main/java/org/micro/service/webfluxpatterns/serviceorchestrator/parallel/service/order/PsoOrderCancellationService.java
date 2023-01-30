package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.service.order;

import jakarta.annotation.PostConstruct;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.PsoOrchestrationRequestContext;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.service.PsoServiceOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
public class PsoOrderCancellationService {

    private Sinks.Many<PsoOrchestrationRequestContext> oCtxSink;
    private Flux<PsoOrchestrationRequestContext> oCtxFlux;

    @Autowired
    private List<PsoServiceOrchestrator> psoServiceOrchestrators;

    @PostConstruct
    public void init() {
        this.oCtxSink = Sinks
                .many()
                .multicast()
                .onBackpressureBuffer();

        this.oCtxFlux = this.oCtxSink
                .asFlux()
                .publishOn(Schedulers.boundedElastic());

        psoServiceOrchestrators
                .forEach(orchestrator -> this.oCtxFlux.subscribe(orchestrator.cancel()));
    }

    public void cancelOrder(PsoOrchestrationRequestContext ctx) {
        this.oCtxSink.tryEmitNext(ctx);
    }
}
