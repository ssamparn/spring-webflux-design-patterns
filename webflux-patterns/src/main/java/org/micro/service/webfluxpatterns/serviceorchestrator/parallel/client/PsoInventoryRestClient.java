package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.client;

import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.request.PsoInventoryRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.PsoInventoryResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.PsoStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class PsoInventoryRestClient {

    private static final String DEDUCT = "deduct";
    private static final String RESTORE = "restore";

    private final WebClient webClient;

    public PsoInventoryRestClient(@Value("${base.url.pso-inventory}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<PsoInventoryResponse> deductInventory(PsoInventoryRequest inventoryRequest) {
        return callInventoryService(DEDUCT, inventoryRequest);
    }

    public Mono<PsoInventoryResponse> restoreInventory(PsoInventoryRequest inventoryRequest) {
        return callInventoryService(RESTORE, inventoryRequest);
    }

    public Mono<PsoInventoryResponse> callInventoryService(String endpoint, PsoInventoryRequest inventoryRequest) {
        return this.webClient
                .post()
                .uri("/" + endpoint)
                .bodyValue(inventoryRequest)
                .retrieve()
                .bodyToMono(PsoInventoryResponse.class)
                .timeout(Duration.ofMillis(500))
                .onErrorReturn(this.buildErrorInventoryResponse(inventoryRequest));
    }

    private PsoInventoryResponse buildErrorInventoryResponse(PsoInventoryRequest request) {
        return PsoInventoryResponse.create(
                request.getProductId(),
                request.getQuantity(),
                null,
                PsoStatus.FAILED
        );
    }
}
