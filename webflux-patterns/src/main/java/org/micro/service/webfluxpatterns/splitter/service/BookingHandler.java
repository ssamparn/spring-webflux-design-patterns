package org.micro.service.webfluxpatterns.splitter.service;

import org.micro.service.webfluxpatterns.splitter.web.model.BookingType;
import org.micro.service.webfluxpatterns.splitter.web.model.ItemBookingRequest;
import org.micro.service.webfluxpatterns.splitter.web.model.ItemBookingResponse;
import reactor.core.publisher.Flux;

public interface BookingHandler {
    BookingType getType();
    Flux<ItemBookingResponse> reserve(Flux<ItemBookingRequest> itemBookingRequestFlux);
}
