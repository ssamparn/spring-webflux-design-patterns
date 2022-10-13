package org.micro.service.webfluxpatterns.splitter.service;

import org.micro.service.webfluxpatterns.splitter.client.HotelRestClient;
import org.micro.service.webfluxpatterns.splitter.model.request.HotelReservationRequest;
import org.micro.service.webfluxpatterns.splitter.model.response.HotelReservationResponse;
import org.micro.service.webfluxpatterns.splitter.web.model.BookingType;
import org.micro.service.webfluxpatterns.splitter.web.model.ItemBookingRequest;
import org.micro.service.webfluxpatterns.splitter.web.model.ItemBookingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class HotelBookingHandler implements BookingHandler {

    @Autowired
    private HotelRestClient hotelRestClient;

    @Override
    public BookingType getType() {
        return BookingType.ROOM;
    }

    @Override
    public Flux<ItemBookingResponse> reserveBooking(Flux<ItemBookingRequest> itemBookingRequestFlux) {
        return itemBookingRequestFlux
                .map(this::toHotelReservationRequest)
                .transform(hotelReservationRequestFlux -> this.hotelRestClient.reserve(hotelReservationRequestFlux))
                .map(this::toItemBookingResponse);
    }

    private HotelReservationRequest toHotelReservationRequest(ItemBookingRequest itemBookingRequest) {
        return HotelReservationRequest.create(
                itemBookingRequest.getCategory(),
                itemBookingRequest.getCity(),
                itemBookingRequest.getFromDate(),
                itemBookingRequest.getToDate()
        );
    }

    private ItemBookingResponse toItemBookingResponse(HotelReservationResponse hotelReservationResponse) {
        return ItemBookingResponse.create(
                hotelReservationResponse.getReservationId(),
                this.getType(),
                hotelReservationResponse.getCategory(),
                hotelReservationResponse.getCity(),
                hotelReservationResponse.getCheckInDate(),
                hotelReservationResponse.getCheckOutDate(),
                hotelReservationResponse.getPrice()
        );
    }
}
