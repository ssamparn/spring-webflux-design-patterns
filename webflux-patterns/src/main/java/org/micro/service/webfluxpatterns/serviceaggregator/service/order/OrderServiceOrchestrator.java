package org.micro.service.webfluxpatterns.serviceaggregator.service.order;

import org.micro.service.webfluxpatterns.serviceaggregator.client.SoProductRestClient;
import org.micro.service.webfluxpatterns.serviceaggregator.model.OrchestrationRequestContext;
import org.micro.service.webfluxpatterns.serviceaggregator.model.request.OrderRequest;
import org.micro.service.webfluxpatterns.serviceaggregator.model.response.OrderResponse;
import org.micro.service.webfluxpatterns.serviceaggregator.model.response.SoProductResponse;
import org.micro.service.webfluxpatterns.serviceaggregator.model.response.Status;
import org.micro.service.webfluxpatterns.serviceaggregator.util.OrchestrationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OrderServiceOrchestrator {

    @Autowired
    private SoProductRestClient productRestClient;

    @Autowired
    private OrderFulfillmentService orderFulfillmentService;

    @Autowired
    private OrderCancellationService orderCancellationService;

    public Mono<OrderResponse> placeOrder(Mono<OrderRequest> orderRequestMono) {
        return orderRequestMono
                .map(OrchestrationRequestContext::new)
                .flatMap(this::getProduct)
                .doOnNext(OrchestrationUtil::buildRequestContext)
                .flatMap(orderFulfillmentService::placeOrder)
                .doOnNext(this::processCancelledOrder)
                .map(this::toOrderResponse);
    }

    private Mono<OrchestrationRequestContext> getProduct(OrchestrationRequestContext ctx) {
        return this.productRestClient.getProduct(ctx.getOrderRequest().getProductId())
                .map(SoProductResponse::getPrice)
                .doOnNext(ctx::setProductPrice)
                .thenReturn(ctx);
    }

    private void processCancelledOrder(OrchestrationRequestContext ctx) {
        if (Status.FAILED == ctx.getStatus()) {
            this.orderCancellationService.cancelOrder(ctx);
        }
    }

    private OrderResponse toOrderResponse(OrchestrationRequestContext ctx) {
        boolean isSuccess = Status.SUCCESS == ctx.getStatus();
        var address = isSuccess ? ctx.getShippingResponse().getAddress() : null;
        var expectedDeliveryDate = isSuccess ? ctx.getShippingResponse().getExpectedDeliveryDate() : null;

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
