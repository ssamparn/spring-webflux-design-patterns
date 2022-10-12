package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model;

import lombok.Data;
import lombok.ToString;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.request.PsoInventoryRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.web.model.PsoOrderRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.request.PsoPaymentRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.request.PsoShippingRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.PsoInventoryResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.PsoPaymentResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.PsoShippingResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.PsoStatus;

import java.util.UUID;

@Data
@ToString
public class PsoOrchestrationRequestContext {
    private final UUID orderId = UUID.randomUUID();
    private PsoOrderRequest psoOrderRequest;
    private Integer productPrice;
    private PsoPaymentRequest psoPaymentRequest;
    private PsoPaymentResponse psoPaymentResponse;
    private PsoInventoryRequest inventoryRequest;
    private PsoInventoryResponse psoInventoryResponse;
    private PsoShippingRequest psoShippingRequest;
    private PsoShippingResponse psoShippingResponse;
    private PsoStatus psoStatus;

    public PsoOrchestrationRequestContext(PsoOrderRequest psoOrderRequest) {
        this.psoOrderRequest = psoOrderRequest;
    }
}
