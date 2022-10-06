package org.micro.service.webfluxpatterns.serviceorchestrator.model;

import lombok.Data;
import lombok.ToString;
import org.micro.service.webfluxpatterns.serviceorchestrator.model.request.InventoryRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.model.request.OrderRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.model.request.PaymentRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.model.request.ShippingRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.model.response.InventoryResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.model.response.PaymentResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.model.response.ShippingResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.model.response.Status;

import java.util.UUID;

@Data
@ToString
public class OrchestrationRequestContext {
    private final UUID orderId = UUID.randomUUID();
    private OrderRequest orderRequest;
    private Integer productPrice;
    private PaymentRequest paymentRequest;
    private PaymentResponse paymentResponse;
    private InventoryRequest inventoryRequest;
    private InventoryResponse inventoryResponse;
    private ShippingRequest shippingRequest;
    private ShippingResponse shippingResponse;
    private Status status;

    public OrchestrationRequestContext(OrderRequest orderRequest) {
        this.orderRequest = orderRequest;
    }
}
