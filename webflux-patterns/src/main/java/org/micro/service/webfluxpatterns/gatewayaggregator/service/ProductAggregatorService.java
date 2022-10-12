package org.micro.service.webfluxpatterns.gatewayaggregator.service;

import lombok.RequiredArgsConstructor;
import org.micro.service.webfluxpatterns.gatewayaggregator.client.GaProductRestClient;
import org.micro.service.webfluxpatterns.gatewayaggregator.client.GaPromotionRestClient;
import org.micro.service.webfluxpatterns.gatewayaggregator.client.GaReviewRestClient;
import org.micro.service.webfluxpatterns.gatewayaggregator.model.Price;
import org.micro.service.webfluxpatterns.gatewayaggregator.web.model.ProductAggregate;
import org.micro.service.webfluxpatterns.gatewayaggregator.model.ProductResponse;
import org.micro.service.webfluxpatterns.gatewayaggregator.model.PromotionResponse;
import org.micro.service.webfluxpatterns.gatewayaggregator.model.ReviewResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAggregatorService {

    private final GaProductRestClient gaProductRestClient;
    private final GaPromotionRestClient gaPromotionRestClient;
    private final GaReviewRestClient gaReviewRestClient;

    public Mono<ProductAggregate> aggregateProduct(Integer productId) {

        Mono<ProductResponse> productResponseMono = this.gaProductRestClient.getProduct(productId);
        Mono<PromotionResponse> promotionResponseMono = this.gaPromotionRestClient.getPromotion(productId);
        Mono<List<ReviewResponse>> reviewsResponseMono = this.gaReviewRestClient.getReviews(productId);

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
