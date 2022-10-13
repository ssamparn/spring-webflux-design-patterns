package org.micro.service.webfluxpatterns.splitter.service;

import org.micro.service.webfluxpatterns.splitter.web.model.BookingType;
import org.micro.service.webfluxpatterns.splitter.web.model.ItemBookingRequest;
import org.micro.service.webfluxpatterns.splitter.web.model.ItemBookingResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final Map<BookingType, BookingHandler> bookingTypeHandlerMap;

    public BookingService(List<BookingHandler> bookingHandlers) {
        this.bookingTypeHandlerMap = bookingHandlers
                .stream()
                .collect(Collectors.toMap(BookingHandler::getType, Function.identity()));
    }

    public Flux<ItemBookingResponse> book(Flux<ItemBookingRequest> itemBookingRequestFlux) {
        return itemBookingRequestFlux.groupBy(ItemBookingRequest::getBookingType)
                .flatMap(this::aggregator);
    }

    private Flux<ItemBookingResponse> aggregator(GroupedFlux<BookingType, ItemBookingRequest> groupedFlux) {
        var bookingTypeKey = groupedFlux.key();
        var bookingHandler = bookingTypeHandlerMap.get(bookingTypeKey);

        return bookingHandler.reserveBooking(groupedFlux);
    }
}
