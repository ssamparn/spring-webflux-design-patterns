package org.micro.service.sampleexternalservice.web.controller;

import lombok.RequiredArgsConstructor;
import org.micro.service.sampleexternalservice.service.GatewayAggregatorService;
import org.micro.service.sampleexternalservice.service.ScatterGatherService;
import org.micro.service.sampleexternalservice.service.ServiceOrchestratorService;
import org.micro.service.sampleexternalservice.web.model.gatewayaggregator.Product;
import org.micro.service.sampleexternalservice.web.model.gatewayaggregator.Promotion;
import org.micro.service.sampleexternalservice.web.model.gatewayaggregator.Review;
import org.micro.service.sampleexternalservice.web.model.scattergather.Delta;
import org.micro.service.sampleexternalservice.web.model.scattergather.Frontier;
import org.micro.service.sampleexternalservice.web.model.scattergather.JetBlue;
import org.micro.service.sampleexternalservice.web.model.scattergather.request.FrontierRequest;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.*;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.request.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExternalServiceRestController {

    private final GatewayAggregatorService gatewayAggregatorService;
    private final ScatterGatherService scatterGatherService;
    private final ServiceOrchestratorService serviceOrchestratorService;

    @GetMapping("/gatewayaggregator/product/{productId}")
    public Mono<Product> getProductWithGatewayAggregator(@PathVariable(name = "productId") int productId) {
        return gatewayAggregatorService.createProductForGatewayAggregator(productId);
    }

    @GetMapping("/gatewayaggregator/promotion/{promotionId}")
    public Mono<Promotion> getPromotion(@PathVariable(name = "promotionId") int promotionId) {
        return gatewayAggregatorService.createPromotion(promotionId);
    }

    @GetMapping("/gatewayaggregator/reviews/{reviewId}")
    public Mono<List<Review>> getAllReviews(@PathVariable(name = "reviewId") int reviewId) {
        return gatewayAggregatorService.createReviews(reviewId);
    }

    @GetMapping(value = "/scattergather/delta/{source}/{destination}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Delta> getDeltaFlights(@PathVariable(name = "source") String source,
                                       @PathVariable(name = "destination") String destination) {
        return scatterGatherService.streamDeltaAirlineFlights(source, destination);
    }

    @PostMapping(value = "/scattergather/frontier", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Frontier> getFrontierFlights(@RequestBody FrontierRequest request) {
        return scatterGatherService.streamFrontierAirlineFlights(request.getSource(), request.getDestination());
    }

    @GetMapping(value = "/scattergather/jetblue/{source}/{destination}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<JetBlue> getJetBlueFlights(@PathVariable(name = "source") String source,
                                         @PathVariable(name = "destination") String destination) {
        return scatterGatherService.streamJetBlueAirlineFlights(source, destination);
    }

    @GetMapping("/serviceorchestrator/product/{productId}")
    public Mono<SoProduct> getProductWithServiceOrchestrator(@PathVariable(name = "productId") int productId) {
        return serviceOrchestratorService.createProductForServiceOrchestrator(productId);
    }

    @GetMapping("/serviceorchestrator/user/{userId}")
    public Mono<User> getUser(@PathVariable(name = "userId") int userId) {
        return serviceOrchestratorService.createUser(userId);
    }

    @PostMapping("/serviceorchestrator/user/deduct")
    public Mono<Deduct> deductAmount(@RequestBody DeductAmountRequest deductAmountRequest) {
        return serviceOrchestratorService.deduct(deductAmountRequest);
    }

    @PostMapping("/serviceorchestrator/user/refund")
    public Mono<Refund> refundAmount(@RequestBody RefundAmountRequest refundAmountRequest) {
        return serviceOrchestratorService.refund(refundAmountRequest);
    }

    @GetMapping("/serviceorchestrator/inventory/{productId}")
    public Integer getInventory(@PathVariable(name = "productId") int productId) {
        return serviceOrchestratorService.countInventoryItems(productId);
    }

    @PostMapping("/serviceorchestrator/inventory/deduct")
    public Mono<DeductInventory> deductInventory(@RequestBody DeductInventoryRequest deductInventoryRequest) {
        return serviceOrchestratorService.createDeductedInventory(deductInventoryRequest);
    }

    @PostMapping("/serviceorchestrator/inventory/restore")
    public Mono<RestoreInventory> restoreInventory(@RequestBody RestoreInventoryRequest restoreInventoryRequest) {
        return serviceOrchestratorService.createRestoredInventory(restoreInventoryRequest);
    }

    @PostMapping("/serviceorchestrator/shipping/schedule")
    public Mono<ScheduleShipping> scheduleShipping(@RequestBody ScheduleShippingRequest scheduleShippingRequest) {
        return serviceOrchestratorService.createScheduleShipping(scheduleShippingRequest);
    }

    @PostMapping("/serviceorchestrator/shipping/cancel")
    public Mono<ResponseEntity> cancelShipping(@RequestBody CancelShippingRequest cancelShippingRequest) {
        return Mono.just(new ResponseEntity<>(HttpStatus.OK));
    }
}
