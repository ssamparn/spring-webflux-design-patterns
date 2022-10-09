package org.micro.service.sampleexternalservice.service;

import com.github.javafaker.Faker;

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
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Service
public class ServiceOrchestratorSequentialService {

    private Faker faker = new Faker();

    private String userName = faker.name().fullName();
    private Integer balance = faker.number().numberBetween(100, 200);
    private Integer inventoryItems = faker.number().numberBetween(500, 1000);

    public Mono<SsoProduct> createProductForServiceOrchestrator(Integer productId) {

        SsoProduct product = SsoProduct.builder()
                .productId(productId)
                .productDescription(faker.commerce().productName())
                .productCategory(faker.commerce().material())
                .productPrice(faker.number().numberBetween(10, 100))
                .build();

        return Mono.just(product);
    }

    public Mono<SsoUser> createUserForServiceOrchestrator(Integer userId) {

        SsoUser ssoUser = SsoUser.builder()
                .userId(userId)
                .userName(userName)
                .balance(balance)
                .address(SsoUser.Address.create(
                        faker.address().streetAddress(),
                        faker.address().cityName(),
                        faker.address().state(),
                        faker.address().zipCode()
                ))
                .build();

        return Mono.just(ssoUser);
    }

    public Mono<SsoDeductPayment> deductForServiceOrchestrator(SsoDeductPaymentRequest ssoDeductPaymentRequest) {
        balance = balance - ssoDeductPaymentRequest.getAmount();

        SsoDeductPayment ssoDeductPayment = SsoDeductPayment.builder()
                .paymentId(ssoDeductPaymentRequest.getPaymentId())
                .userId(ssoDeductPaymentRequest.getUserId())
                .userName(userName)
                .balance(balance)
                .status("SUCCESS")
                .build();

        return Mono.just(ssoDeductPayment);
    }

    public Mono<SsoRefundPayment> refundForServiceOrchestrator(SsoRefundPaymentRequest ssoRefundPaymentRequest) {
        balance = balance + ssoRefundPaymentRequest.getAmount();

        SsoRefundPayment ssoRefundPayment = SsoRefundPayment.builder()
                .userId(ssoRefundPaymentRequest.getUserId())
                .userName(userName)
                .balance(balance)
                .status("SUCCESS")
                .build();

        return Mono.just(ssoRefundPayment);
    }

    public Integer countInventoryItemsForServiceOrchestrator(int productId) {
        return inventoryItems;
    }

    public Mono<SsoDeductInventory> createDeductedInventoryForServiceOrchestrator(SsoDeductInventoryRequest ssoDeductInventoryRequest) {
        inventoryItems = inventoryItems - ssoDeductInventoryRequest.getQuantity();

        SsoDeductInventory ssoDeductInventory = SsoDeductInventory.builder()
                .inventoryId(ssoDeductInventoryRequest.getPaymentId())
                .productId(ssoDeductInventoryRequest.getProductId())
                .quantity(ssoDeductInventoryRequest.getQuantity())
                .remainingQuantity(inventoryItems)
                .status("SUCCESS")
                .build();

        return Mono.just(ssoDeductInventory);
    }

    public Mono<SsoRestoreInventory> createRestoredInventoryForServiceOrchestrator(SsoRestoreInventoryRequest ssoRestoreInventoryRequest) {
        inventoryItems = inventoryItems + ssoRestoreInventoryRequest.getQuantity();

        SsoRestoreInventory ssoRestoreInventory = SsoRestoreInventory.builder()
                .productId(ssoRestoreInventoryRequest.getProductId())
                .quantity(ssoRestoreInventoryRequest.getQuantity())
                .remainingQuantity(inventoryItems)
                .status("SUCCESS")
                .build();

        return Mono.just(ssoRestoreInventory);
    }

    public Mono<SsoScheduleShipping> createScheduleShippingForServiceOrchestrator(SsoScheduleShippingRequest ssoScheduleShippingRequest) {
        SsoScheduleShipping psoScheduleShipping = SsoScheduleShipping.builder()
                .shippingId(ssoScheduleShippingRequest.getInventoryId())
                .quantity(ssoScheduleShippingRequest.getQuantity())
                .status("SUCCESS")
                .expectedDelivery(faker.date().future(20, TimeUnit.DAYS).toString())
                .address(SsoUser.Address.create(
                        faker.address().streetAddress(),
                        faker.address().cityName(),
                        faker.address().state(),
                        faker.address().zipCode()
                ))
                .build();
        return Mono.just(psoScheduleShipping);
    }
}
