package org.micro.service.sampleexternalservice.web.api;

import org.micro.service.sampleexternalservice.service.ExternalService;
import org.micro.service.sampleexternalservice.web.model.Product;
import org.micro.service.sampleexternalservice.web.model.Promotion;
import org.micro.service.sampleexternalservice.web.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ExternalServiceRestController {

    @Autowired
    private ExternalService externalService;

    @GetMapping("/product/{productId}")
    public Mono<Product> getProduct(@PathVariable(name = "productId") int productId) {
        return externalService.createProduct(productId);
    }

    @GetMapping("/promotion/{promotionId}")
    public Mono<Promotion> getPromotion(@PathVariable(name = "promotionId") int promotionId) {
        return externalService.createPromotion(promotionId);
    }

    @GetMapping("/reviews/{reviewId}")
    public Flux<Review> getAllReviews(@PathVariable(name = "reviewId") int reviewId) {
        return externalService.createReviews(reviewId);
    }
}
