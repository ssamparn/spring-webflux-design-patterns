package org.micro.service.sampleexternalservice.service;

import com.github.javafaker.Faker;
import org.micro.service.sampleexternalservice.web.model.gatewayaggregator.Product;
import org.micro.service.sampleexternalservice.web.model.gatewayaggregator.Promotion;
import org.micro.service.sampleexternalservice.web.model.gatewayaggregator.Review;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class GatewayAggregatorService {

    private Faker faker = new Faker();

    public Mono<Product> createProductForGatewayAggregator(Integer productId) {

        Product product = Product.builder()
                .productId(productId)
                .productDescription(faker.commerce().productName())
                .productCategory(faker.commerce().material())
                .productPrice(faker.number().randomDigit())
                .build();

        return Mono.just(product);
    }

    public Mono<Promotion> createPromotionForGatewayAggregator(Integer productId) {
        Promotion promotion = Promotion.builder()
                .productId(productId)
                .promotionCode(faker.commerce().promotionCode())
                .productType(faker.commerce().material())
                .discount(faker.number().randomDigit())
                .endDate(faker.date().future(27, TimeUnit.DAYS).toString())
                .build();

        return Mono.just(promotion);
    }

    public Mono<List<Review>> createReviewsForGatewayAggregator(Integer productId) {
        Review review1 = Review.builder()
                .productId(productId)
                .userName(faker.funnyName().name())
                .comment(faker.letterify("Comment1"))
                .rating(faker.number().randomDigit())
                .build();

        Review review2 = Review.builder()
                .productId(productId)
                .userName(faker.funnyName().name())
                .comment(faker.letterify("Comment2"))
                .rating(faker.number().randomDigit())
                .build();

        return Flux.just(review1, review2).collectList();
    }

}
