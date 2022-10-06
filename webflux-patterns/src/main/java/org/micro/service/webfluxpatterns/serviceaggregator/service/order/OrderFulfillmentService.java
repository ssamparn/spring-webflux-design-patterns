package org.micro.service.webfluxpatterns.serviceaggregator.service.order;

import org.micro.service.webfluxpatterns.serviceaggregator.model.OrchestrationRequestContext;
import org.micro.service.webfluxpatterns.serviceaggregator.model.response.Status;
import org.micro.service.webfluxpatterns.serviceaggregator.service.ServiceOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderFulfillmentService {

    @Autowired
    private List<ServiceOrchestrator> serviceOrchestrators;

    public Mono<OrchestrationRequestContext> placeOrder(OrchestrationRequestContext ctx) {
        List<Mono<OrchestrationRequestContext>> publisherList = serviceOrchestrators.stream()
                .map(orchestrator -> orchestrator.create(ctx))
                .collect(Collectors.toList());

        return Mono.zip(publisherList, array -> array[0])
                .cast(OrchestrationRequestContext.class)
                .doOnNext(this::updateStatus);
    }

    private void updateStatus(OrchestrationRequestContext ctx) {
        boolean allSuccess = this.serviceOrchestrators.stream().allMatch(orchestrator -> orchestrator.isSuccess().test(ctx));
        Status status = allSuccess ? Status.SUCCESS : Status.FAILED;
        ctx.setStatus(status);
    }
}
