package org.micro.service.webfluxpatterns.scattergather.service;

import lombok.RequiredArgsConstructor;
import org.micro.service.webfluxpatterns.scattergather.client.DeltaServiceClient;
import org.micro.service.webfluxpatterns.scattergather.client.FrontierServiceClient;
import org.micro.service.webfluxpatterns.scattergather.client.JetBlueServiceClient;
import org.micro.service.webfluxpatterns.scattergather.model.FlightResult;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class FlightSearchService {

    private final DeltaServiceClient deltaServiceClient;
    private final FrontierServiceClient frontierServiceClient;
    private final JetBlueServiceClient jetBlueServiceClient;

    public Flux<FlightResult> searchFlights(String source, String destination) {
        Flux<FlightResult> deltaResultFlux = this.deltaServiceClient.gatherFlights(source, destination);
        Flux<FlightResult> frontierResultFlux = this.frontierServiceClient.gatherFlights(source, destination);
        Flux<FlightResult> jetBlueResultFlux = this.jetBlueServiceClient.gatherFlights(source, destination);
        
        return Flux.merge(
                deltaResultFlux,
                frontierResultFlux,
                jetBlueResultFlux
        ).takeUntil(flightResult -> flightResult.getPrice() > 50);
    }
}