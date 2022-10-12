package org.micro.service.webfluxpatterns.splitter.client;

import org.micro.service.webfluxpatterns.splitter.model.request.CarReservationRequest;
import org.micro.service.webfluxpatterns.splitter.model.response.CarReservationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CarRestClient {

    private final WebClient carClinet;

    public CarRestClient(@Value("${base.url.car}") String baseUrl) {
        this.carClinet = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<CarReservationResponse> reserve(Flux<CarReservationRequest> carRequestFlux) {
        return this.carClinet
                .post()
                .body(carRequestFlux, CarReservationRequest.class)
                .retrieve()
                .bodyToFlux(CarReservationResponse.class)
                .onErrorResume(ex -> Mono.empty());
    }
}
