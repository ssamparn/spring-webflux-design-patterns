package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.client;

import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.request.SsoInventoryRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response.SsoInventoryResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response.SsoStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class SsoInventoryRestClient {

    private static final String DEDUCT = "deduct";
    private static final String RESTORE = "restore";

    private final WebClient webClient;

    public SsoInventoryRestClient(@Value("${base.url.sso-inventory}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<SsoInventoryResponse> deductInventory(SsoInventoryRequest inventoryRequest) {
        return callInventoryService(DEDUCT, inventoryRequest);
    }

    public Mono<SsoInventoryResponse> restoreInventory(SsoInventoryRequest inventoryRequest) {
        return callInventoryService(RESTORE, inventoryRequest);
    }

    public Mono<SsoInventoryResponse> callInventoryService(String endpoint, SsoInventoryRequest inventoryRequest) {
        return this.webClient
                .post()
                .uri("/" + endpoint)
                .bodyValue(inventoryRequest)
                .retrieve()
                .bodyToMono(SsoInventoryResponse.class)
                .onErrorReturn(this.buildErrorInventoryResponse(inventoryRequest));
    }

    private SsoInventoryResponse buildErrorInventoryResponse(SsoInventoryRequest request) {
        return SsoInventoryResponse.create(
                null,
                request.getProductId(),
                request.getQuantity(),
                null,
                SsoStatus.FAILED
        );
    }
}
