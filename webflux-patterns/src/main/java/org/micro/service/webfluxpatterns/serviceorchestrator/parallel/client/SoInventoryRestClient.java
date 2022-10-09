package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.client;

import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.request.InventoryRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.InventoryResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class SoInventoryRestClient {

    private static final String DEDUCT = "deduct";
    private static final String RESTORE = "restore";

    private final WebClient webClient;

    public SoInventoryRestClient(@Value("${base.url.sop-inventory}") String baseUrl) {
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
