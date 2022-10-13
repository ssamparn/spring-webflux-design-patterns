package org.micro.service.sampleexternalservice.service;

import com.github.javafaker.Faker;
import org.micro.service.sampleexternalservice.web.model.splitter.request.CarRequest;
import org.micro.service.sampleexternalservice.web.model.splitter.request.HotelRequest;
import org.micro.service.sampleexternalservice.web.model.splitter.response.CarResponse;
import org.micro.service.sampleexternalservice.web.model.splitter.response.HotelResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Service
public class SplitterService {

    private Faker faker = new Faker();

    public Flux<CarResponse> bookCar(Flux<CarRequest> carRequestFlux) {
        return carRequestFlux
                .map(this::toCarResponse);
    }

    private CarResponse toCarResponse(CarRequest carRequest) {
        return CarResponse.create(
                UUID.randomUUID(),
                carRequest.getCity(),
                carRequest.getPickupDate(),
                carRequest.getDropDate(),
                carRequest.getCategory(),
                faker.number().numberBetween(100, 200)
        );
    }

    public Flux<HotelResponse> bookHotelRoom(Flux<HotelRequest> hotelRequestFlux) {
        return hotelRequestFlux
                .map(this::toHotelResponse);
    }

    private HotelResponse toHotelResponse(HotelRequest hotelRequest) {
        return HotelResponse.create(
                UUID.randomUUID(),
                hotelRequest.getCity(),
                hotelRequest.getCheckInDate(),
                hotelRequest.getCheckOutDate(),
                hotelRequest.getCategory(),
                faker.number().numberBetween(1000, 2000)
        );
    }
}
