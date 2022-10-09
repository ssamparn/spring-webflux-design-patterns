package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.controller;

import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.request.SsoOrderRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response.SsoOrderResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.service.order.SsoOrderServiceOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SsoOrderRestController {

    @Autowired
    private SsoOrderServiceOrchestrator ssoOrderServiceOrchestrator;

    @PostMapping(value = "/place-order-sequential")
    public Mono<ResponseEntity<SsoOrderResponse>> placeOrder(@RequestBody Mono<SsoOrderRequest> orderRequestMono) {
        return ssoOrderServiceOrchestrator.placeOrder(orderRequestMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
