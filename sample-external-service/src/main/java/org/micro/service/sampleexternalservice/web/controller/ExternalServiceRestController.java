package org.micro.service.sampleexternalservice.web.controller;

import lombok.RequiredArgsConstructor;
import org.micro.service.sampleexternalservice.service.GatewayAggregatorService;
import org.micro.service.sampleexternalservice.service.ScatterGatherService;
import org.micro.service.sampleexternalservice.service.ServiceOrchestratorParallelService;
import org.micro.service.sampleexternalservice.service.ServiceOrchestratorSequentialService;
import org.micro.service.sampleexternalservice.web.model.gatewayaggregator.Product;
import org.micro.service.sampleexternalservice.web.model.gatewayaggregator.Promotion;
import org.micro.service.sampleexternalservice.web.model.gatewayaggregator.Review;
import org.micro.service.sampleexternalservice.web.model.scattergather.Delta;
import org.micro.service.sampleexternalservice.web.model.scattergather.Frontier;
import org.micro.service.sampleexternalservice.web.model.scattergather.JetBlue;
import org.micro.service.sampleexternalservice.web.model.scattergather.request.FrontierRequest;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.response.PsoDeductPayment;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.response.PsoDeductInventory;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.response.PsoRefundPayment;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.response.PsoRestoreInventory;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.response.PsoScheduleShipping;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.response.PsoProduct;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.response.PsoUser;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.request.*;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.sequential.response.SsoDeductPayment;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.sequential.response.SsoDeductInventory;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.sequential.response.SsoProduct;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.sequential.response.SsoRefundPayment;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.sequential.response.SsoRestoreInventory;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.sequential.response.SsoScheduleShipping;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.sequential.response.SsoUser;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.sequential.request.SsoDeductInventoryRequest;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.sequential.request.SsoDeductPaymentRequest;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.sequential.request.SsoRefundPaymentRequest;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.sequential.request.SsoRestoreInventoryRequest;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.sequential.request.SsoScheduleShippingRequest;
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
    private final ServiceOrchestratorParallelService serviceOrchestratorParallelService;
    private final ServiceOrchestratorSequentialService serviceOrchestratorSequentialService;

    // Gateway Aggregator

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

    // Scatter Gather

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

    // Service Orchestrator - Parallel

    @GetMapping("/serviceorchestrator-parallel/product/{productId}")
    public Mono<PsoProduct> getProductWithServiceOrchestratorParallel(@PathVariable(name = "productId") int productId) {
        return serviceOrchestratorParallelService.createProductForServiceOrchestrator(productId);
    }

    @GetMapping("/serviceorchestrator-parallel/user/{userId}")
    public Mono<PsoUser> getUserWithServiceOrchestratorParallel(@PathVariable(name = "userId") int userId) {
        return serviceOrchestratorParallelService.createUserForServiceOrchestrator(userId);
    }

    @PostMapping("/serviceorchestrator-parallel/user/deduct")
    public Mono<PsoDeductPayment> deductAmountWithServiceOrchestratorParallel(@RequestBody PsoDeductPaymentRequest psoDeductPaymentRequest) {
        return serviceOrchestratorParallelService.deductForServiceOrchestrator(psoDeductPaymentRequest);
    }

    @PostMapping("/serviceorchestrator-parallel/user/refund")
    public Mono<PsoRefundPayment> refundAmountWithServiceOrchestratorParallel(@RequestBody PsoRefundPaymentRequest psoRefundPaymentRequest) {
        return serviceOrchestratorParallelService.refundForServiceOrchestrator(psoRefundPaymentRequest);
    }

    @GetMapping("/serviceorchestrator-parallel/inventory/{productId}")
    public Integer getInventoryWithServiceOrchestratorParallel(@PathVariable(name = "productId") int productId) {
        return serviceOrchestratorParallelService.countInventoryItemsForServiceOrchestrator(productId);
    }

    @PostMapping("/serviceorchestrator-parallel/inventory/deduct")
    public Mono<PsoDeductInventory> deductInventoryWithServiceOrchestratorParallel(@RequestBody PsoDeductInventoryRequest psoDeductInventoryRequest) {
        return serviceOrchestratorParallelService.createDeductedInventoryForServiceOrchestrator(psoDeductInventoryRequest);
    }

    @PostMapping("/serviceorchestrator-parallel/inventory/restore")
    public Mono<PsoRestoreInventory> restoreInventoryWithServiceOrchestratorParallel(@RequestBody PsoRestoreInventoryRequest psoRestoreInventoryRequest) {
        return serviceOrchestratorParallelService.createRestoredInventoryForServiceOrchestrator(psoRestoreInventoryRequest);
    }

    @PostMapping("/serviceorchestrator-parallel/shipping/schedule")
    public Mono<PsoScheduleShipping> scheduleShippingWithServiceOrchestratorParallel(@RequestBody PsoScheduleShippingRequest psoScheduleShippingRequest) {
        return serviceOrchestratorParallelService.createScheduleShippingForServiceOrchestrator(psoScheduleShippingRequest);
    }

    @PostMapping("/serviceorchestrator-parallel/shipping/cancel")
    public Mono<ResponseEntity> cancelShippingWithServiceOrchestratorParallel(@RequestBody PsoCancelShippingRequest psoCancelShippingRequest) {
        return Mono.just(new ResponseEntity<>(HttpStatus.OK));
    }

    // Service Orchestrator - Sequential

    @GetMapping("/serviceorchestrator-sequential/product/{productId}")
    public Mono<SsoProduct> getProductWithServiceOrchestratorSequential(@PathVariable(name = "productId") int productId) {
        return serviceOrchestratorSequentialService.createProductForServiceOrchestrator(productId);
    }

    @GetMapping("/serviceorchestrator-sequential/user/{userId}")
    public Mono<SsoUser> getUserWithServiceOrchestratorSequential(@PathVariable(name = "userId") int userId) {
        return serviceOrchestratorSequentialService.createUserForServiceOrchestrator(userId);
    }

    @PostMapping("/serviceorchestrator-sequential/user/deduct")
    public Mono<SsoDeductPayment> deductAmountWithServiceOrchestratorSequential(@RequestBody SsoDeductPaymentRequest ssoDeductPaymentRequest) {
        return serviceOrchestratorSequentialService.deductForServiceOrchestrator(ssoDeductPaymentRequest);
    }

    @PostMapping("/serviceorchestrator-sequential/user/refund")
    public Mono<SsoRefundPayment> refundAmountWithServiceOrchestratorSequential(@RequestBody SsoRefundPaymentRequest ssoRefundPaymentRequest) {
        return serviceOrchestratorSequentialService.refundForServiceOrchestrator(ssoRefundPaymentRequest);
    }

    @GetMapping("/serviceorchestrator-sequential/inventory/{productId}")
    public Integer getInventoryWithServiceOrchestratorSequential(@PathVariable(name = "productId") int productId) {
        return serviceOrchestratorSequentialService.countInventoryItemsForServiceOrchestrator(productId);
    }

    @PostMapping("/serviceorchestrator-sequential/inventory/deduct")
    public Mono<SsoDeductInventory> deductInventoryWithServiceOrchestratorSequential(@RequestBody SsoDeductInventoryRequest ssoDeductInventoryRequest) {
        return serviceOrchestratorSequentialService.createDeductedInventoryForServiceOrchestrator(ssoDeductInventoryRequest);
    }

    @PostMapping("/serviceorchestrator-sequential/inventory/restore")
    public Mono<SsoRestoreInventory> restoreInventoryWithServiceOrchestratorSequential(@RequestBody SsoRestoreInventoryRequest ssoRestoreInventoryRequest) {
        return serviceOrchestratorSequentialService.createRestoredInventoryForServiceOrchestrator(ssoRestoreInventoryRequest);
    }

    @PostMapping("/serviceorchestrator-sequential/shipping/schedule")
    public Mono<SsoScheduleShipping> scheduleShippingWithServiceOrchestratorSequential(@RequestBody SsoScheduleShippingRequest ssoScheduleShippingRequest) {
        return serviceOrchestratorSequentialService.createScheduleShippingForServiceOrchestrator(ssoScheduleShippingRequest);
    }

    @PostMapping("/serviceorchestrator-sequential/shipping/cancel")
    public Mono<ResponseEntity> cancelShippingWithServiceOrchestratorSequential(@RequestBody PsoCancelShippingRequest psoCancelShippingRequest) {
        return Mono.just(new ResponseEntity<>(HttpStatus.OK));
    }
}
