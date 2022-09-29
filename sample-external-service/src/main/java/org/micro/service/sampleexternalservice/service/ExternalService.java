package org.micro.service.sampleexternalservice.service;

import com.github.javafaker.Faker;
import org.micro.service.sampleexternalservice.web.model.Product;
import org.micro.service.sampleexternalservice.web.model.Promotion;
import org.micro.service.sampleexternalservice.web.model.Review;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Service
public class ExternalService {

    private Faker faker = new Faker();

    public Mono<Product> createProduct(int productId) {

        Product product = Product.builder()
                .productId(productId)
                .description(faker.commerce().productName())
                .category(faker.commerce().material())
                .price(faker.commerce().price())
                .build();

        return Mono.just(product);
    }

    public Mono<Promotion> createPromotion(int promotionId) {
        Promotion promotion = Promotion.builder()
                .promotionId(promotionId)
                .promotionCode(faker.commerce().promotionCode())
                .productType(faker.commerce().material())
                .discount(faker.number().randomDigit())
                .endDate(faker.date().future(27, TimeUnit.DAYS).toString())
                .build();

        return Mono.just(promotion);
    }

    public Flux<Review> createReviews(int reviewId) {
        Review review1 = Review.builder()
                .reviewId(reviewId)
                .userName(faker.funnyName().name())
                .comment(faker.letterify("Comment1"))
                .rating(faker.number().randomDigit())
                .build();

        Review review2 = Review.builder()
                .reviewId(reviewId)
                .userName(faker.funnyName().name())
                .comment(faker.letterify("Comment2"))
                .rating(faker.number().randomDigit())
                .build();

        return Flux.just(review1, review2);
    }
}
