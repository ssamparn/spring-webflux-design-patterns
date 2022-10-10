package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.service.order;

import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.client.SsoProductRestClient;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.SsoOrchestrationRequestContext;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response.SsoProductResponse;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response.SsoStatus;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.service.SsoInventoryServiceOrchestrator;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.service.SsoPaymentServiceOrchestrator;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.service.SsoShippingServiceOrchestrator;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.util.SsoOrchestrationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SsoOrderFulfillmentService {

    @Autowired
    private SsoProductRestClient productRestClient;

    @Autowired
    private SsoPaymentServiceOrchestrator paymentServiceOrchestrator;

    @Autowired
    private SsoInventoryServiceOrchestrator inventoryServiceOrchestrator;

    @Autowired
    private SsoShippingServiceOrchestrator shippingServiceOrchestrator;

    public Mono<SsoOrchestrationRequestContext> fulfillOrder(SsoOrchestrationRequestContext ctx) {
        return this.getProduct(ctx)
                .doOnNext(SsoOrchestrationUtil::buildPaymentRequest)
                .flatMap(this.paymentServiceOrchestrator::create)
                .doOnNext(SsoOrchestrationUtil::buildInventoryRequest)
                .flatMap(this.inventoryServiceOrchestrator::create)
                .doOnNext(SsoOrchestrationUtil::buildShippingRequest)
                .flatMap(this.shippingServiceOrchestrator::create)
                .doOnNext(s -> s.setSsoStatus(SsoStatus.SUCCESS))
                .doOnError(ex -> ctx.setSsoStatus(SsoStatus.FAILED))
                .onErrorReturn(ctx);
    }

    private Mono<SsoOrchestrationRequestContext> getProduct(SsoOrchestrationRequestContext ctx) {
        return this.productRestClient.getProduct(ctx.getSsoOrderRequest().getProductId())
                .map(SsoProductResponse::getProductPrice)
                .doOnNext(ctx::setProductPrice)
                .thenReturn(ctx);
    }

}
