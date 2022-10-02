package org.micro.service.webfluxpatterns.serviceaggregator.client;

import org.micro.service.webfluxpatterns.serviceaggregator.model.request.InventoryRequest;
import org.micro.service.webfluxpatterns.serviceaggregator.model.request.PaymentRequest;
import org.micro.service.webfluxpatterns.serviceaggregator.model.response.InventoryResponse;
import org.micro.service.webfluxpatterns.serviceaggregator.model.response.PaymentResponse;
import org.micro.service.webfluxpatterns.serviceaggregator.model.response.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class SoInventoryClient {

    private static final String DEDUCT = "deduct";
    private static final String RESTORE = "restore";

    private final WebClient webClient;

    public SoInventoryClient(@Value("${base.url.so-inventory}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<InventoryResponse> deductInventory(InventoryRequest inventoryRequest) {
        return callInventoryService(DEDUCT, inventoryRequest);
    }

    public Mono<InventoryResponse> restoreInventory(InventoryRequest inventoryRequest) {
        return callInventoryService(RESTORE, inventoryRequest);
    }

    public Mono<InventoryResponse> callInventoryService(String endpoint, InventoryRequest inventoryRequest) {
        return this.webClient
                .post()
                .uri("/" + endpoint)
                .bodyValue(inventoryRequest)
                .retrieve()
                .bodyToMono(InventoryResponse.class)
                .onErrorReturn(this.buildErrorInventoryResponse(inventoryRequest));
    }

    private InventoryResponse buildErrorInventoryResponse(InventoryRequest request) {
        return InventoryResponse.create(
                request.getProductId(),
                request.getQuantity(),
                null,
                Status.FAILED
        );
    }
}
