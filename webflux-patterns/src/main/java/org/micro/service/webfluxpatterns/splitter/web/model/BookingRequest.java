package org.micro.service.webfluxpatterns.splitter.web.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {

    private BookingType bookingType;
    private String category;
    private String city;
    private LocalDate fromDate;
    private LocalDate toDate;

}
