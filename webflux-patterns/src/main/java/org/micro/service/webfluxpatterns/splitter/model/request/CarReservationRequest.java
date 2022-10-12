package org.micro.service.webfluxpatterns.splitter.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class CarReservationRequest {
    private String category;
    private String city;
    private LocalDate pickupTime;
    private LocalDate dropTime;
}
