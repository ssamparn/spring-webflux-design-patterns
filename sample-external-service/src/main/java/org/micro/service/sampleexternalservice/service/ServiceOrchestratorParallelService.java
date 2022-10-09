package org.micro.service.sampleexternalservice.service;

import com.github.javafaker.Faker;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.PsoDeduct;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.PsoDeductInventory;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.PsoProduct;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.PsoRefund;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.PsoRestoreInventory;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.PsoScheduleShipping;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.PsoUser;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.request.PsoDeductInventoryRequest;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.request.PsoDeductPaymentRequest;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.request.PsoRefundPaymentRequest;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.request.PsoRestoreInventoryRequest;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.request.PsoScheduleShippingRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Service
public class ServiceOrchestratorParallelService {

    private Faker faker = new Faker();

    private String userName = faker.name().fullName();
    private Integer balance = faker.number().numberBetween(100, 200);
    private Integer inventoryItems = faker.number().numberBetween(500, 1000);

    public Mono<PsoProduct> createProductForServiceOrchestrator(Integer productId) {

        PsoProduct product = PsoProduct.builder()
                .productId(productId)
                .productDescription(faker.commerce().productName())
                .productCategory(faker.commerce().material())
                .productPrice(faker.number().numberBetween(10, 100))
                .build();

        return Mono.just(product);
    }

    public Mono<PsoUser> createUserForServiceOrchestrator(Integer userId) {

        PsoUser psoUser = PsoUser.builder()
                .userId(userId)
                .userName(userName)
                .balance(balance)
                .address(PsoUser.Address.create(
                        faker.address().streetAddress(),
                        faker.address().cityName(),
                        faker.address().state(),
                        faker.address().zipCode()
                ))
                .build();

        return Mono.just(psoUser);
    }

    public Mono<PsoDeduct> deductForServiceOrchestrator(PsoDeductPaymentRequest psoDeductPaymentRequest) {
        balance = balance - psoDeductPaymentRequest.getAmount();

        PsoDeduct psoDeduct = PsoDeduct.builder()
                .userId(psoDeductPaymentRequest.getUserId())
                .userName(userName)
                .balance(balance)
                .status("SUCCESS")
                .build();

        return Mono.just(psoDeduct);
    }

    public Mono<PsoRefund> refundForServiceOrchestrator(PsoRefundPaymentRequest psoRefundPaymentRequest) {
        balance = balance + psoRefundPaymentRequest.getAmount();

        PsoRefund psoRefund = PsoRefund.builder()
                .userId(psoRefundPaymentRequest.getUserId())
                .userName(userName)
                .balance(balance)
                .status("SUCCESS")
                .build();

        return Mono.just(psoRefund);
    }

    public Integer countInventoryItemsForServiceOrchestrator(int productId) {
        return inventoryItems;
    }

    public Mono<PsoDeductInventory> createDeductedInventoryForServiceOrchestrator(PsoDeductInventoryRequest psoDeductInventoryRequest) {
        inventoryItems = inventoryItems - psoDeductInventoryRequest.getQuantity();

        PsoDeductInventory psoDeductInventory = PsoDeductInventory.builder()
                .productId(psoDeductInventoryRequest.getProductId())
                .quantity(psoDeductInventoryRequest.getQuantity())
                .remainingQuantity(inventoryItems)
                .status("SUCCESS")
                .build();

        return Mono.just(psoDeductInventory);
    }

    public Mono<PsoRestoreInventory> createRestoredInventoryForServiceOrchestrator(PsoRestoreInventoryRequest psoRestoreInventoryRequest) {
        inventoryItems = inventoryItems + psoRestoreInventoryRequest.getQuantity();

        PsoRestoreInventory psoRestoreInventory = PsoRestoreInventory.builder()
                .productId(psoRestoreInventoryRequest.getProductId())
                .quantity(psoRestoreInventoryRequest.getQuantity())
                .remainingQuantity(inventoryItems)
                .status("SUCCESS")
                .build();

        return Mono.just(psoRestoreInventory);
    }

    public Mono<PsoScheduleShipping> createScheduleShippingForServiceOrchestrator(PsoScheduleShippingRequest psoScheduleShippingRequest) {
        PsoScheduleShipping psoScheduleShipping = PsoScheduleShipping.builder()
                .orderId(psoScheduleShippingRequest.getOrderId())
                .quantity(psoScheduleShippingRequest.getQuantity())
                .status("SUCCESS")
                .expectedDelivery(faker.date().future(20, TimeUnit.DAYS).toString())
                .address(PsoUser.Address.create(
                        faker.address().streetAddress(),
                        faker.address().cityName(),
                        faker.address().state(),
                        faker.address().zipCode()
                ))
                .build();
        return Mono.just(psoScheduleShipping);
    }
}
