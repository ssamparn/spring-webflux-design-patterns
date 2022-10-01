package org.micro.service.webfluxpatterns.gatewayaggregator.service;

import lombok.RequiredArgsConstructor;
import org.micro.service.webfluxpatterns.gatewayaggregator.client.ProductRestClient;
import org.micro.service.webfluxpatterns.gatewayaggregator.client.PromotionRestClient;
import org.micro.service.webfluxpatterns.gatewayaggregator.client.ReviewRestClient;
import org.micro.service.webfluxpatterns.gatewayaggregator.model.Price;
import org.micro.service.webfluxpatterns.gatewayaggregator.model.ProductAggregate;
import org.micro.service.webfluxpatterns.gatewayaggregator.model.ProductResponse;
import org.micro.service.webfluxpatterns.gatewayaggregator.model.PromotionResponse;
import org.micro.service.webfluxpatterns.gatewayaggregator.model.ReviewResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAggregatorService {

    private final ProductRestClient productRestClient;
    private final PromotionRestClient promotionRestClient;
    private final ReviewRestClient reviewRestClient;

    public Mono<ProductAggregate> aggregateProduct(Integer productId) {

        Mono<ProductResponse> productResponseMono = this.productRestClient.getProduct(productId);
        Mono<PromotionResponse> promotionResponseMono = this.promotionRestClient.getPromotion(productId);
        Mono<List<ReviewResponse>> reviewsResponseMono = this.reviewRestClient.getReviews(productId);

        return Mono.zip(
                productResponseMono,
                promotionResponseMono,
                reviewsResponseMono
        ).map(productTuple -> createProductAggregator(productTuple.getT1(), productTuple.getT2(), productTuple.getT3()));
    }

    private ProductAggregate createProductAggregator(ProductResponse productResponse,
                                                     PromotionResponse promotionResponse,
                                                     List<ReviewResponse> reviewsResponse) {

        Price price = createPrice(productResponse, promotionResponse);

        return ProductAggregate.create(
                productResponse.getProductId(),
                productResponse.getProductCategory(),
                productResponse.getProductDescription(),
                price,
                reviewsResponse
        );
    }

    private Price createPrice(ProductResponse productResponse,
                              PromotionResponse promotionResponse) {
        var amountSaved = productResponse.getProductPrice() * promotionResponse.getDiscount() / 100;
        var discountedPrice = productResponse.getProductPrice() - amountSaved;

        return Price.builder()
                        .listPrice(productResponse.getProductPrice())
                        .discount(promotionResponse.getDiscount())
                        .discountedPrice(discountedPrice)
                        .amountSaved(amountSaved)
                        .endDate(promotionResponse.getEndDate())
                .build();
    }
}
