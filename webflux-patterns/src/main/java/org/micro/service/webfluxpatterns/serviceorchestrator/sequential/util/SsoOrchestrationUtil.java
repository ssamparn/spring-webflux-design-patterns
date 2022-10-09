package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.util;

import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.SsoOrchestrationRequestContext;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.request.SsoInventoryRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.request.SsoPaymentRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.request.SsoShippingRequest;

public class SsoOrchestrationUtil {

    public static void buildRequestContext(SsoOrchestrationRequestContext ctx) {
        buildPaymentRequest(ctx);
        buildInventoryRequest(ctx);
        buildShippingRequest(ctx);
    }

    private static void buildPaymentRequest(SsoOrchestrationRequestContext ctx) {
        SsoPaymentRequest paymentRequest = SsoPaymentRequest.create(
                ctx.getSsoOrderRequest().getUserId(),
                ctx.getProductPrice() * ctx.getSsoOrderRequest().getQuantity(),
                ctx.getOrderId()
        );
        ctx.setSsoPaymentRequest(paymentRequest);
    }

    private static void buildInventoryRequest(SsoOrchestrationRequestContext ctx) {
        SsoInventoryRequest inventoryRequest = SsoInventoryRequest.create(
                ctx.getOrderId(),
                ctx.getSsoOrderRequest().getProductId(),
                ctx.getSsoOrderRequest().getQuantity()
        );
        ctx.setInventoryRequest(inventoryRequest);
    }

    private static void buildShippingRequest(SsoOrchestrationRequestContext ctx) {
        SsoShippingRequest shippingRequest = SsoShippingRequest.create(
                ctx.getSsoOrderRequest().getQuantity(),
                ctx.getSsoOrderRequest().getUserId(),
                ctx.getOrderId()
        );
        ctx.setSsoShippingRequest(shippingRequest);
    }

}
