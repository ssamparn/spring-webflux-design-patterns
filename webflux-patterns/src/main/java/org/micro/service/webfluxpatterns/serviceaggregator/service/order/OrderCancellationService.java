package org.micro.service.webfluxpatterns.serviceaggregator.service.order;

import org.micro.service.webfluxpatterns.serviceaggregator.model.OrchestrationRequestContext;
import org.micro.service.webfluxpatterns.serviceaggregator.service.ServiceOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class OrderCancellationService {

    private Sinks.Many<OrchestrationRequestContext> oCtxSink;
    private Flux<OrchestrationRequestContext> oCtxFlux;

    @Autowired
    private List<ServiceOrchestrator> serviceOrchestrators;

    @PostConstruct
    public void init() {
        this.oCtxSink = Sinks
                .many()
                .multicast()
                .onBackpressureBuffer();

        this.oCtxFlux = this.oCtxSink
                .asFlux()
                .publishOn(Schedulers.boundedElastic());

        serviceOrchestrators
                .forEach(orchestrator -> this.oCtxFlux.subscribe(orchestrator.cancel()));
    }

    public void cancelOrder(OrchestrationRequestContext ctx) {
        this.oCtxSink.tryEmitNext(ctx);
    }
}
