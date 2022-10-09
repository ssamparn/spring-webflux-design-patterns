package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.service.order;

import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.PsoOrchestrationRequestContext;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.PsoStatus;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.service.PsoServiceOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PsoOrderFulfillmentService {

    @Autowired
    private List<PsoServiceOrchestrator> psoServiceOrchestrators;

    public Mono<PsoOrchestrationRequestContext> fulfillOrder(PsoOrchestrationRequestContext ctx) {
        List<Mono<PsoOrchestrationRequestContext>> publisherList = psoServiceOrchestrators.stream()
                .map(orchestrator -> orchestrator.create(ctx))
                .collect(Collectors.toList());

        return Mono.zip(publisherList, array -> array[0])
                .cast(PsoOrchestrationRequestContext.class)
                .doOnNext(this::updateStatus);
    }

    private void updateStatus(PsoOrchestrationRequestContext ctx) {
        boolean allSuccess = this.psoServiceOrchestrators.stream().allMatch(orchestrator -> orchestrator.isSuccess().test(ctx));
        PsoStatus psoStatus = allSuccess ? PsoStatus.SUCCESS : PsoStatus.FAILED;
        ctx.setPsoStatus(psoStatus);
    }
}
