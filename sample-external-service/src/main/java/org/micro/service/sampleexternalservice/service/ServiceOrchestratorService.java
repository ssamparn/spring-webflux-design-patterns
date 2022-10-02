package org.micro.service.sampleexternalservice.service;

import com.github.javafaker.Faker;
import org.micro.service.sampleexternalservice.web.model.gatewayaggregator.Product;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.*;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.request.ScheduleShippingRequest;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.request.DeductAmountRequest;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.request.DeductInventoryRequest;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.request.RefundAmountRequest;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.request.RestoreInventoryRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Service
public class ServiceOrchestratorService {

    private Faker faker = new Faker();

    public Mono<Product> createProductForServiceOrchestrator(Integer productId) {

        Product product = Product.builder()
                .productId(productId)
                .productDescription(faker.commerce().productName())
                .productCategory(faker.commerce().material())
                .productPrice(faker.number().randomDigit())
                .build();

        return Mono.just(product);
    }

    public Mono<User> createUser(Integer userId) {

        User user = User.builder()
                .userId(userId)
                .userName(faker.name().firstName())
                .balance(faker.number().numberBetween(100, 200))
                .address(User.Address.create(
                        faker.address().streetAddress(),
                        faker.address().cityName(),
                        faker.address().state(),
                        faker.address().zipCode()
                ))
                .build();

        return Mono.just(user);
    }

    public Mono<Deduct> deduct(DeductAmountRequest deductAmountRequest) {

        Deduct deduct = Deduct.builder()
                .userId(deductAmountRequest.getUserId())
                .userName(faker.name().firstName())
                .balance(faker.number().numberBetween(100, 200))
                .status("SUCCESS")
                .build();

        return Mono.just(deduct);
    }

    public Mono<Refund> refund(RefundAmountRequest refundAmountRequest) {

        Refund refund = Refund.builder()
                .userId(refundAmountRequest.getUserId())
                .userName(faker.name().firstName())
                .balance(faker.number().numberBetween(100, 200))
                .status("SUCCESS")
                .build();

        return Mono.just(refund);
    }


    public Integer countInventoryItems(int productId) {
        return 10;
    }


    public Mono<DeductInventory> createDeductedInventory(DeductInventoryRequest deductInventoryRequest) {
        DeductInventory deductInventory = DeductInventory.builder()
                .productId(deductInventoryRequest.getProductId())
                .quantity(deductInventoryRequest.getQuantity())
                .remainingQuantity(deductInventoryRequest.getQuantity())
                .status("SUCCESS")
                .build();

        return Mono.just(deductInventory);
    }

    public Mono<RestoreInventory> createRestoredInventory(RestoreInventoryRequest restoreInventoryRequest) {
        RestoreInventory restoreInventory = RestoreInventory.builder()
                .productId(restoreInventoryRequest.getProductId())
                .quantity(restoreInventoryRequest.getQuantity())
                .remainingQuantity(restoreInventoryRequest.getQuantity())
                .status("SUCCESS")
                .build();

        return Mono.just(restoreInventory);
    }

    public Mono<ScheduleShipping> createScheduleShipping(ScheduleShippingRequest scheduleShippingRequest) {
        ScheduleShipping scheduleShipping = ScheduleShipping.builder()
                .orderId(scheduleShippingRequest.getOrderId())
                .quantity(scheduleShippingRequest.getQuantity())
                .status("SUCCESS")
                .expectedDelivery(faker.date().future(20, TimeUnit.DAYS).toString())
                .address(User.Address.create(
                        faker.address().streetAddress(),
                        faker.address().cityName(),
                        faker.address().state(),
                        faker.address().zipCode()
                ))
                .build();
        return Mono.just(scheduleShipping);
    }
}
