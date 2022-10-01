package org.micro.service.sampleexternalservice.service;

import com.github.javafaker.Faker;
import org.micro.service.sampleexternalservice.web.model.scattergather.Delta;
import org.micro.service.sampleexternalservice.web.model.scattergather.Frontier;
import org.micro.service.sampleexternalservice.web.model.scattergather.JetBlue;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDate;
import java.util.stream.Stream;

@Service
public class ScatterGatherService {

    private Faker faker = new Faker();

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
