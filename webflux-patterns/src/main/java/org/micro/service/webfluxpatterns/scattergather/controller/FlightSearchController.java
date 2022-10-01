package org.micro.service.webfluxpatterns.scattergather.controller;

import org.micro.service.webfluxpatterns.scattergather.model.FlightResult;
import org.micro.service.webfluxpatterns.scattergather.service.FlightSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping
public class FlightSearchController {

    @Autowired
    private FlightSearchService flightSearchService;

    @GetMapping(value = "/search-flights/{source}/{destination}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<FlightResult> getFlights(@PathVariable(name = "source") String source,
                                         @PathVariable(name = "destination") String destination) {
        return flightSearchService.searchFlights(source, destination);
    }
}
