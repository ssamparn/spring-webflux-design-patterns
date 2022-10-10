package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.util;

import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.SsoOrchestrationRequestContext;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.request.SsoInventoryRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.request.SsoPaymentRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.request.SsoShippingRequest;

public class SsoOrchestrationUtil {

    public static void buildPaymentRequest(SsoOrchestrationRequestContext ctx) {
        SsoPaymentRequest paymentRequest = SsoPaymentRequest.create(
                ctx.getSsoOrderRequest().getUserId(),
                ctx.getProductPrice() * ctx.getSsoOrderRequest().getQuantity(),
                ctx.getOrderId()
        );
        ctx.setSsoPaymentRequest(paymentRequest);
    }

    public static void buildInventoryRequest(SsoOrchestrationRequestContext ctx) {
        SsoInventoryRequest inventoryRequest = SsoInventoryRequest.create(
                ctx.getSsoPaymentResponse().getPaymentId(),
                ctx.getSsoOrderRequest().getProductId(),
                ctx.getSsoOrderRequest().getQuantity()
        );
        ctx.setInventoryRequest(inventoryRequest);
    }

    public static void buildShippingRequest(SsoOrchestrationRequestContext ctx) {
        SsoShippingRequest shippingRequest = SsoShippingRequest.create(
                ctx.getSsoInventoryResponse().getInventoryId(),
                ctx.getSsoOrderRequest().getQuantity(),
                ctx.getSsoOrderRequest().getUserId()
        );
        ctx.setSsoShippingRequest(shippingRequest);
    }

}
