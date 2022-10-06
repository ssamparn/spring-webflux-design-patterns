package org.micro.service.webfluxpatterns.serviceorchestrator.util;

import org.micro.service.webfluxpatterns.serviceorchestrator.model.OrchestrationRequestContext;
import org.micro.service.webfluxpatterns.serviceorchestrator.model.request.InventoryRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.model.request.PaymentRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.model.request.ShippingRequest;

public class OrchestrationUtil {

    public static void buildRequestContext(OrchestrationRequestContext ctx) {
        buildPaymentRequest(ctx);
        buildInventoryRequest(ctx);
        buildShippingRequest(ctx);
    }

    private static void buildPaymentRequest(OrchestrationRequestContext ctx) {
        PaymentRequest paymentRequest = PaymentRequest.create(
                ctx.getPaymentRequest().getUserId(),
                ctx.getProductPrice() * ctx.getOrderRequest().getQuantity(),
                ctx.getOrderId()
        );
        ctx.setPaymentRequest(paymentRequest);
    }

    private static void buildInventoryRequest(OrchestrationRequestContext ctx) {
        InventoryRequest inventoryRequest = InventoryRequest.create(
                ctx.getOrderId(),
                ctx.getOrderRequest().getProductId(),
                ctx.getOrderRequest().getQuantity()
        );
        ctx.setInventoryRequest(inventoryRequest);
    }

    private static void buildShippingRequest(OrchestrationRequestContext ctx) {
        ShippingRequest shippingRequest = ShippingRequest.create(
                ctx.getOrderRequest().getQuantity(),
                ctx.getOrderRequest().getUserId(),
                ctx.getOrderId()
        );
        ctx.setShippingRequest(shippingRequest);
    }

}
