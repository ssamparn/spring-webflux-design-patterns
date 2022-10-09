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
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.PsoDeduct;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.PsoDeductInventory;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.PsoRefund;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.PsoRestoreInventory;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.PsoScheduleShipping;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.PsoProduct;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.PsoUser;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.request.*;
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

    @GetMapping("/serviceorchestrator-parallel/product/{productId}")
    public Mono<PsoProduct> getProductWithServiceOrchestrator(@PathVariable(name = "productId") int productId) {
        return serviceOrchestratorService.createProductForServiceOrchestrator(productId);
    }

    @GetMapping("/serviceorchestrator-parallel/user/{userId}")
    public Mono<PsoUser> getUser(@PathVariable(name = "userId") int userId) {
        return serviceOrchestratorService.createUser(userId);
    }

    @PostMapping("/serviceorchestrator-parallel/user/deduct")
    public Mono<PsoDeduct> deductAmount(@RequestBody PsoDeductPaymentRequest psoDeductPaymentRequest) {
        return serviceOrchestratorService.deduct(psoDeductPaymentRequest);
    }

    @PostMapping("/serviceorchestrator-parallel/user/refund")
    public Mono<PsoRefund> refundAmount(@RequestBody PsoRefundPaymentRequest psoRefundPaymentRequest) {
        return serviceOrchestratorService.refund(psoRefundPaymentRequest);
    }

    @GetMapping("/serviceorchestrator-parallel/inventory/{productId}")
    public Integer getInventory(@PathVariable(name = "productId") int productId) {
        return serviceOrchestratorService.countInventoryItems(productId);
    }

    @PostMapping("/serviceorchestrator-parallel/inventory/deduct")
    public Mono<PsoDeductInventory> deductInventory(@RequestBody PsoDeductInventoryRequest psoDeductInventoryRequest) {
        return serviceOrchestratorService.createDeductedInventory(psoDeductInventoryRequest);
    }

    @PostMapping("/serviceorchestrator-parallel/inventory/restore")
    public Mono<PsoRestoreInventory> restoreInventory(@RequestBody PsoRestoreInventoryRequest psoRestoreInventoryRequest) {
        return serviceOrchestratorService.createRestoredInventory(psoRestoreInventoryRequest);
    }

    @PostMapping("/serviceorchestrator-parallel/shipping/schedule")
    public Mono<PsoScheduleShipping> scheduleShipping(@RequestBody PsoScheduleShippingRequest psoScheduleShippingRequest) {
        return serviceOrchestratorService.createScheduleShipping(psoScheduleShippingRequest);
    }

    @PostMapping("/serviceorchestrator-parallel/shipping/cancel")
    public Mono<ResponseEntity> cancelShipping(@RequestBody PsoCancelShippingRequest psoCancelShippingRequest) {
        return Mono.just(new ResponseEntity<>(HttpStatus.OK));
    }
}
