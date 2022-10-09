package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.util;

import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.PsoOrchestrationRequestContext;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.request.PsoInventoryRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.request.PsoPaymentRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.request.PsoShippingRequest;

public class PsoOrchestrationUtil {

    public static void buildRequestContext(PsoOrchestrationRequestContext ctx) {
        buildPaymentRequest(ctx);
        buildInventoryRequest(ctx);
        buildShippingRequest(ctx);
    }

    private static void buildPaymentRequest(PsoOrchestrationRequestContext ctx) {
        PsoPaymentRequest psoPaymentRequest = PsoPaymentRequest.create(
                ctx.getPsoOrderRequest().getUserId(),
                ctx.getProductPrice() * ctx.getPsoOrderRequest().getQuantity(),
                ctx.getOrderId()
        );
        ctx.setPsoPaymentRequest(psoPaymentRequest);
    }

    private static void buildInventoryRequest(PsoOrchestrationRequestContext ctx) {
        PsoInventoryRequest inventoryRequest = PsoInventoryRequest.create(
                ctx.getOrderId(),
                ctx.getPsoOrderRequest().getProductId(),
                ctx.getPsoOrderRequest().getQuantity()
        );
        ctx.setInventoryRequest(inventoryRequest);
    }

    private static void buildShippingRequest(PsoOrchestrationRequestContext ctx) {
        PsoShippingRequest psoShippingRequest = PsoShippingRequest.create(
                ctx.getPsoOrderRequest().getQuantity(),
                ctx.getPsoOrderRequest().getUserId(),
                ctx.getOrderId()
        );
        ctx.setPsoShippingRequest(psoShippingRequest);
    }

}
