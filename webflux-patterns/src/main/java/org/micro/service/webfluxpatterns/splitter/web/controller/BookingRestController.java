package org.micro.service.webfluxpatterns.splitter.web.controller;

import org.micro.service.webfluxpatterns.splitter.service.BookingService;
import org.micro.service.webfluxpatterns.splitter.web.model.BookingResponse;
import org.micro.service.webfluxpatterns.splitter.web.model.ItemBookingRequest;
import org.micro.service.webfluxpatterns.splitter.web.model.ItemBookingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
public class BookingRestController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book-item")
    public Mono<BookingResponse> bookItem(@RequestBody Flux<ItemBookingRequest> itemBookingRequestFlux) {
        return bookingService.book(itemBookingRequestFlux)
                .collectList()
                .map(this::toResponse);
    }

    private BookingResponse toResponse(List<ItemBookingResponse> itemBookingResponses) {
        return BookingResponse.create(
                UUID.randomUUID(),
                itemBookingResponses.stream().mapToInt(ItemBookingResponse::getPrice).sum(),
                itemBookingResponses
        );
    }

}
