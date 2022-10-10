package org.micro.service.sampleexternalservice.web.model.splitter.request;

import lombok.Builder;
import lombok.Data;
import org.micro.service.sampleexternalservice.web.model.splitter.Category;

import java.time.LocalDate;

@Data
@Builder
public class HotelRequest {
    private Category category;
    private String city;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
