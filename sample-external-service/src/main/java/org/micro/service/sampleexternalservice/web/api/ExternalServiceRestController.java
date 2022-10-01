package org.micro.service.sampleexternalservice.web.api;

import org.micro.service.sampleexternalservice.service.ExternalService;
import org.micro.service.sampleexternalservice.web.model.gatewayaggregator.Product;
import org.micro.service.sampleexternalservice.web.model.gatewayaggregator.Promotion;
import org.micro.service.sampleexternalservice.web.model.gatewayaggregator.Review;
import org.micro.service.sampleexternalservice.web.model.scattergather.Delta;
import org.micro.service.sampleexternalservice.web.model.scattergather.Frontier;
import org.micro.service.sampleexternalservice.web.model.scattergather.JetBlue;
import org.micro.service.sampleexternalservice.web.model.scattergather.request.FrontierRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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
    public Mono<List<Review>> getAllReviews(@PathVariable(name = "reviewId") int reviewId) {
        return externalService.createReviews(reviewId);
    }

    @GetMapping(value = "/delta/{source}/{destination}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Delta> getDeltaFlights(@PathVariable(name = "source") String source,
                                       @PathVariable(name = "destination") String destination) {
        return externalService.streamDeltaAirlineFlights(source, destination);
    }

    @PostMapping(value = "/frontier", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Frontier> getFrontierFlights(@RequestBody FrontierRequest request) {
        return externalService.streamFrontierAirlineFlights(request.getSource(), request.getDestination());
    }

    @GetMapping(value = "/jetblue/{source}/{destination}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<JetBlue> getJetBlueFlights(@PathVariable(name = "source") String source,
                                         @PathVariable(name = "destination") String destination) {
        return externalService.streamJetBlueAirlineFlights(source, destination);
    }

}
