package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.service.order;

import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.SsoOrchestrationRequestContext;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response.SsoStatus;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.service.SsoServiceOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SsoOrderFulfillmentService {

    @Autowired
    private List<SsoServiceOrchestrator> ssoServiceOrchestrators;

    public Mono<SsoOrchestrationRequestContext> fulfillOrder(SsoOrchestrationRequestContext ctx) {
        List<Mono<SsoOrchestrationRequestContext>> publisherList = ssoServiceOrchestrators.stream()
                .map(orchestrator -> orchestrator.create(ctx))
                .collect(Collectors.toList());

        return Mono.zip(publisherList, array -> array[0])
                .cast(SsoOrchestrationRequestContext.class)
                .doOnNext(this::updateStatus);
    }

    private void updateStatus(SsoOrchestrationRequestContext ctx) {
        boolean allSuccess = this.ssoServiceOrchestrators.stream().allMatch(orchestrator -> orchestrator.isSuccess().test(ctx));
        SsoStatus psoStatus = allSuccess ? SsoStatus.SUCCESS : SsoStatus.FAILED;
        ctx.setSsoStatus(psoStatus);
    }
}
