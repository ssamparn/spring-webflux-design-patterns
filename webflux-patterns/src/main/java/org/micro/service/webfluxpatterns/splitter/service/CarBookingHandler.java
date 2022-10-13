package org.micro.service.webfluxpatterns.splitter.service;

import org.micro.service.webfluxpatterns.splitter.client.CarRestClient;
import org.micro.service.webfluxpatterns.splitter.model.request.CarReservationRequest;
import org.micro.service.webfluxpatterns.splitter.model.response.CarReservationResponse;
import org.micro.service.webfluxpatterns.splitter.web.model.BookingType;
import org.micro.service.webfluxpatterns.splitter.web.model.ItemBookingRequest;
import org.micro.service.webfluxpatterns.splitter.web.model.ItemBookingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CarBookingHandler implements BookingHandler {

    @Autowired
    private CarRestClient carRestClient;

    @Override
    public BookingType getType() {
        return BookingType.CAR;
    }

    @Override
    public Flux<ItemBookingResponse> reserveBooking(Flux<ItemBookingRequest> itemBookingRequestFlux) {
        return itemBookingRequestFlux
                .map(this::toCarReservationRequest)
                .transform(carReservationRequestFlux -> this.carRestClient.reserve(carReservationRequestFlux))
                .map(this::toItemBookingResponse);
    }

    private CarReservationRequest toCarReservationRequest(ItemBookingRequest itemBookingRequest) {
        return CarReservationRequest.create(
                itemBookingRequest.getCategory(),
                itemBookingRequest.getCity(),
                itemBookingRequest.getFromDate(),
                itemBookingRequest.getToDate()
        );
    }

    private ItemBookingResponse toItemBookingResponse(CarReservationResponse carReservationResponse) {
        return ItemBookingResponse.create(
                carReservationResponse.getReservationId(),
                this.getType(),
                carReservationResponse.getCategory(),
                carReservationResponse.getCity(),
                carReservationResponse.getPickupDate(),
                carReservationResponse.getDropDate(),
                carReservationResponse.getPrice()
        );
    }
}
