package org.micro.service.webfluxpatterns.splitter.client;

import org.micro.service.webfluxpatterns.splitter.model.request.HotelReservationRequest;
import org.micro.service.webfluxpatterns.splitter.model.response.HotelReservationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class HotelRestClient {

    private final WebClient hotelClient;

    public HotelRestClient(@Value("${base.url.hotel}") String baseUrl) {
        this.hotelClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Flux<HotelReservationResponse> reserve(Flux<HotelReservationRequest> hotelRequestFlux) {
        return this.hotelClient
                .post()
                .body(hotelRequestFlux, HotelReservationRequest.class)
                .retrieve()
                .bodyToFlux(HotelReservationResponse.class)
                .timeout(Duration.ofMillis(500))
                .onErrorResume(ex -> Mono.empty());
    }
}
