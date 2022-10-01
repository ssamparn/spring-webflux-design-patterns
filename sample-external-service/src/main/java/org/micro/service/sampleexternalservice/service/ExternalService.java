package org.micro.service.sampleexternalservice.service;

import com.github.javafaker.Faker;
import org.micro.service.sampleexternalservice.web.model.gatewayaggregator.Product;
import org.micro.service.sampleexternalservice.web.model.gatewayaggregator.Promotion;
import org.micro.service.sampleexternalservice.web.model.gatewayaggregator.Review;
import org.micro.service.sampleexternalservice.web.model.scattergather.Delta;
import org.micro.service.sampleexternalservice.web.model.scattergather.Frontier;
import org.micro.service.sampleexternalservice.web.model.scattergather.JetBlue;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Service
public class ExternalService {

    private Faker faker = new Faker();

    public Mono<Product> createProduct(Integer productId) {

        Product product = Product.builder()
                .productId(productId)
                .productDescription(faker.commerce().productName())
                .productCategory(faker.commerce().material())
                .productPrice(faker.number().randomDigit())
                .build();

        return Mono.just(product);
    }

    public Mono<Promotion> createPromotion(Integer productId) {
        Promotion promotion = Promotion.builder()
                .productId(productId)
                .promotionCode(faker.commerce().promotionCode())
                .productType(faker.commerce().material())
                .discount(faker.number().randomDigit())
                .endDate(faker.date().future(27, TimeUnit.DAYS).toString())
                .build();

        return Mono.just(promotion);
    }

    public Mono<List<Review>> createReviews(Integer productId) {
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

    public Flux<Delta> streamDeltaAirlineFlights(String from, String to) {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

        Flux<Delta> deltaFlightEvents = Flux.fromStream(Stream.generate(() ->
                Delta.create(
                "DELTA",
                        from,
                        to,
                        (double) faker.number().randomDigit(),
                        LocalDate.now())));

        return Flux.zip(deltaFlightEvents, interval, (key, value) -> key);
    }

    public Flux<Frontier> streamFrontierAirlineFlights(String from, String to) {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

        Flux<Frontier> deltaFlightEvents = Flux.fromStream(Stream.generate(() ->
                Frontier.create(
                        "Frontier",
                        from,
                        to,
                        (double) faker.number().randomDigit(),
                        LocalDate.now())));

        return Flux.zip(deltaFlightEvents, interval, (key, value) -> key);
    }

    public Flux<JetBlue> streamJetBlueAirlineFlights(String from, String to) {
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));

        Flux<JetBlue> jetBlueFlightEvents = Flux.fromStream(Stream.generate(() ->
                JetBlue.create(
                        (double) faker.number().randomDigit(),
                        LocalDate.now())));

        return Flux.zip(jetBlueFlightEvents, interval, (key, value) -> key);
    }
}
