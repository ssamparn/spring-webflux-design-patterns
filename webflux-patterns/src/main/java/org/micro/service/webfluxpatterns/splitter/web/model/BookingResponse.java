package org.micro.service.webfluxpatterns.splitter.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class BookingResponse {
    private UUID bookingId;
    private Integer totalPrice;
    private List<ItemBookingResponse> items;
}
