package org.micro.service.sampleexternalservice.service;

import com.github.javafaker.Faker;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.response.PsoDeductPayment;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.response.PsoDeductInventory;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.response.PsoProduct;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.response.PsoRefundPayment;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.response.PsoRestoreInventory;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.response.PsoScheduleShipping;
import org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.response.PsoUser;
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

    public Mono<PsoDeductPayment> deductForServiceOrchestrator(PsoDeductPaymentRequest psoDeductPaymentRequest) {
        balance = balance - psoDeductPaymentRequest.getAmount();

        PsoDeductPayment psoDeductPayment = PsoDeductPayment.builder()
                .userId(psoDeductPaymentRequest.getUserId())
                .userName(userName)
                .balance(balance)
                .status("SUCCESS")
                .build();

        return Mono.just(psoDeductPayment);
    }

    public Mono<PsoRefundPayment> refundForServiceOrchestrator(PsoRefundPaymentRequest psoRefundPaymentRequest) {
        balance = balance + psoRefundPaymentRequest.getAmount();

        PsoRefundPayment psoRefundPayment = PsoRefundPayment.builder()
                .userId(psoRefundPaymentRequest.getUserId())
                .userName(userName)
                .balance(balance)
                .status("SUCCESS")
                .build();

        return Mono.just(psoRefundPayment);
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
