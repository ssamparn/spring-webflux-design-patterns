package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model;

import lombok.Data;
import lombok.ToString;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.request.SsoInventoryRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.request.SsoOrderRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.request.SsoPaymentRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.request.SsoShippingRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response.SsoInventoryResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response.SsoPaymentResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response.SsoShippingResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response.SsoStatus;

import java.util.UUID;

@Data
@ToString
public class SsoOrchestrationRequestContext {
    private final UUID orderId = UUID.randomUUID();
    private SsoOrderRequest ssoOrderRequest;
    private Integer productPrice;
    private SsoPaymentRequest ssoPaymentRequest;
    private SsoPaymentResponse ssoPaymentResponse;
    private SsoInventoryRequest inventoryRequest;
    private SsoInventoryResponse ssoInventoryResponse;
    private SsoShippingRequest ssoShippingRequest;
    private SsoShippingResponse ssoShippingResponse;
    private SsoStatus ssoStatus;

    public SsoOrchestrationRequestContext(SsoOrderRequest ssoOrderRequest) {
        this.ssoOrderRequest = ssoOrderRequest;
    }
}
