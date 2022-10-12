package org.micro.service.webfluxpatterns.gatewayaggregator.web.controller;

import org.micro.service.webfluxpatterns.gatewayaggregator.web.model.ProductAggregate;
import org.micro.service.webfluxpatterns.gatewayaggregator.service.ProductAggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
public class ProductAggregateController {

    @Autowired
    private ProductAggregatorService productAggregatorService;

    @GetMapping("/product-aggregate/{productId}")
    public Mono<ResponseEntity<ProductAggregate>> getProductAggregate(@PathVariable Integer productId) {

        return this.productAggregatorService.aggregateProduct(productId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
