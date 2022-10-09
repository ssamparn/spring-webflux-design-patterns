package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.service.order;

import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.client.SsoProductRestClient;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.SsoOrchestrationRequestContext;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.request.SsoOrderRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response.SsoOrderResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response.SsoProductResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response.SsoStatus;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.util.SsoDebugUtil;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.util.SsoOrchestrationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SsoOrderServiceOrchestrator {

    @Autowired
    private SsoProductRestClient productRestClient;

    @Autowired
    private SsoOrderFulfillmentService ssoOrderFulfillmentService;

    @Autowired
    private SsoOrderCancellationService ssoOrderCancellationService;

    public Mono<SsoOrderResponse> placeOrder(Mono<SsoOrderRequest> orderRequestMono) {
        return orderRequestMono
                .map(SsoOrchestrationRequestContext::new)
                .flatMap(this::getProduct)
                .doOnNext(SsoOrchestrationUtil::buildRequestContext)
                .flatMap(ssoOrderFulfillmentService::fulfillOrder)
                .doOnNext(this::processCancelledOrderIfAny)
                .doOnNext(SsoDebugUtil::print)
                .map(this::toOrderResponse);
    }

    private Mono<SsoOrchestrationRequestContext> getProduct(SsoOrchestrationRequestContext ctx) {
        return this.productRestClient.getProduct(ctx.getSsoOrderRequest().getProductId())
                .map(SsoProductResponse::getProductPrice)
                .doOnNext(ctx::setProductPrice)
                .thenReturn(ctx);
    }

    private void processCancelledOrderIfAny(SsoOrchestrationRequestContext ctx) {
        if (SsoStatus.FAILED == ctx.getSsoStatus()) {
            this.ssoOrderCancellationService.cancelOrder(ctx);
        }
    }

    private SsoOrderResponse toOrderResponse(SsoOrchestrationRequestContext ctx) {
        boolean isSuccess = SsoStatus.SUCCESS == ctx.getSsoStatus();
        var address = isSuccess ? ctx.getSsoShippingResponse().getSsoAddress() : null;
        var expectedDeliveryDate = isSuccess ? ctx.getSsoShippingResponse().getExpectedDelivery() : null;

        return SsoOrderResponse.create(
                ctx.getSsoOrderRequest().getUserId(),
                ctx.getSsoOrderRequest().getProductId(),
                ctx.getOrderId(),
                ctx.getSsoStatus(),
                address,
                expectedDeliveryDate
        );
    }

}
