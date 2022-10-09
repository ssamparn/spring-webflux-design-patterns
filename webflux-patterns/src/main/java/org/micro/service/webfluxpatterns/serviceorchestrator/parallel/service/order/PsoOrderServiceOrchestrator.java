package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.service.order;

import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.client.PsoProductRestClient;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.PsoOrchestrationRequestContext;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.request.PsoOrderRequest;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.PsoOrderResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.PsoProductResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.PsoStatus;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.util.PsoDebugUtil;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.util.PsoOrchestrationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PsoOrderServiceOrchestrator {

    @Autowired
    private PsoProductRestClient productRestClient;

    @Autowired
    private PsoOrderFulfillmentService psoOrderFulfillmentService;

    @Autowired
    private PsoOrderCancellationService psoOrderCancellationService;

    public Mono<PsoOrderResponse> placeOrder(Mono<PsoOrderRequest> orderRequestMono) {
        return orderRequestMono
                .map(PsoOrchestrationRequestContext::new)
                .flatMap(this::getProduct)
                .doOnNext(PsoOrchestrationUtil::buildRequestContext)
                .flatMap(psoOrderFulfillmentService::fulfillOrder)
                .doOnNext(this::processCancelledOrderIfAny)
                .doOnNext(PsoDebugUtil::print)
                .map(this::toOrderResponse);
    }

    private Mono<PsoOrchestrationRequestContext> getProduct(PsoOrchestrationRequestContext ctx) {
        return this.productRestClient.getProduct(ctx.getPsoOrderRequest().getProductId())
                .map(PsoProductResponse::getProductPrice)
                .doOnNext(ctx::setProductPrice)
                .thenReturn(ctx);
    }

    private void processCancelledOrderIfAny(PsoOrchestrationRequestContext ctx) {
        if (PsoStatus.FAILED == ctx.getPsoStatus()) {
            this.psoOrderCancellationService.cancelOrder(ctx);
        }
    }

    private PsoOrderResponse toOrderResponse(PsoOrchestrationRequestContext ctx) {
        boolean isSuccess = PsoStatus.SUCCESS == ctx.getPsoStatus();
        var address = isSuccess ? ctx.getPsoShippingResponse().getPsoAddress() : null;
        var expectedDeliveryDate = isSuccess ? ctx.getPsoShippingResponse().getExpectedDelivery() : null;

        return PsoOrderResponse.create(
                ctx.getPsoOrderRequest().getUserId(),
                ctx.getPsoOrderRequest().getProductId(),
                ctx.getOrderId(),
                ctx.getPsoStatus(),
                address,
                expectedDeliveryDate
        );
    }

}
