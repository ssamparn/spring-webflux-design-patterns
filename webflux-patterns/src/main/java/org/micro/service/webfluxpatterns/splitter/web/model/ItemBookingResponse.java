package org.micro.service.webfluxpatterns.splitter.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ItemBookingResponse {
    private UUID itemId;
    private BookingType bookingType;
    private String category;
    private String city;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Integer price;
}
