package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.service.order;

import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.client.PosProductRestClient;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.OrchestrationRequestContext;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.request.OrderRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.OrderResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.SoProductResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.Status;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.util.DebugUtil;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.util.OrchestrationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OrderServiceOrchestrator {

    @Autowired
    private PosProductRestClient productRestClient;

    @Autowired
    private OrderFulfillmentService orderFulfillmentService;

    @Autowired
    private OrderCancellationService orderCancellationService;

    public Mono<OrderResponse> placeOrder(Mono<OrderRequest> orderRequestMono) {
        return orderRequestMono
                .map(OrchestrationRequestContext::new)
                .flatMap(this::getProduct)
                .doOnNext(OrchestrationUtil::buildRequestContext)
                .flatMap(orderFulfillmentService::fulfillOrder)
                .doOnNext(this::processCancelledOrderIfAny)
                .doOnNext(DebugUtil::print)
                .map(this::toOrderResponse);
    }

    private Mono<OrchestrationRequestContext> getProduct(OrchestrationRequestContext ctx) {
        return this.productRestClient.getProduct(ctx.getOrderRequest().getProductId())
                .map(SoProductResponse::getProductPrice)
                .doOnNext(ctx::setProductPrice)
                .thenReturn(ctx);
    }

    private void processCancelledOrderIfAny(OrchestrationRequestContext ctx) {
        if (Status.FAILED == ctx.getStatus()) {
            this.orderCancellationService.cancelOrder(ctx);
        }
    }

    private OrderResponse toOrderResponse(OrchestrationRequestContext ctx) {
        boolean isSuccess = Status.SUCCESS == ctx.getStatus();
        var address = isSuccess ? ctx.getShippingResponse().getAddress() : null;
        var expectedDeliveryDate = isSuccess ? ctx.getShippingResponse().getExpectedDelivery() : null;

        return OrderResponse.create(
                ctx.getOrderRequest().getUserId(),
                ctx.getOrderRequest().getProductId(),
                ctx.getOrderId(),
                ctx.getStatus(),
                address,
                expectedDeliveryDate
        );
    }

}
