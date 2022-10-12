package org.micro.service.webfluxpatterns.splitter.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ItemBookingRequest {

    private BookingType bookingType;
    private String category;
    private String city;
    private LocalDate fromDate;
    private LocalDate toDate;

}
