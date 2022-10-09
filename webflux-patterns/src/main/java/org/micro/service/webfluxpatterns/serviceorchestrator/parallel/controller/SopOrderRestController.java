package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.controller;

import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.request.OrderRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.OrderResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.service.order.OrderServiceOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class SopOrderRestController {

    @Autowired
    private OrderServiceOrchestrator orderServiceOrchestrator;

    @PostMapping(value = "/place-order")
    public Mono<ResponseEntity<OrderResponse>> placeOrder(@RequestBody Mono<OrderRequest> orderRequestMono) {
        return orderServiceOrchestrator.placeOrder(orderRequestMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
