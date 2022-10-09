package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.service.order;

import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.SsoOrchestrationRequestContext;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.service.SsoServiceOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class SsoOrderCancellationService {

    private Sinks.Many<SsoOrchestrationRequestContext> oCtxSink;
    private Flux<SsoOrchestrationRequestContext> oCtxFlux;

    @Autowired
    private List<SsoServiceOrchestrator> psoServiceOrchestrators;

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

    public void cancelOrder(SsoOrchestrationRequestContext ctx) {
        this.oCtxSink.tryEmitNext(ctx);
    }
}
