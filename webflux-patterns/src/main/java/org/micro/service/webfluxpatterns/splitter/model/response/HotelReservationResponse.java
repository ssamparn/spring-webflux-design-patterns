package org.micro.service.webfluxpatterns.splitter.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class HotelReservationResponse {
    private UUID reservationId;
    private String city;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String category;
    private Integer price;
}
