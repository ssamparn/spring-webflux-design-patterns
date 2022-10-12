package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.web.controller;

import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.web.model.PsoOrderRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.web.model.PsoOrderResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.service.order.PsoOrderServiceOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class PsoOrderRestController {

    @Autowired
    private PsoOrderServiceOrchestrator psoOrderServiceOrchestrator;

    @PostMapping(value = "/place-order-parallel")
    public Mono<ResponseEntity<PsoOrderResponse>> placeOrder(@RequestBody Mono<PsoOrderRequest> orderRequestMono) {
        return psoOrderServiceOrchestrator.placeOrder(orderRequestMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
