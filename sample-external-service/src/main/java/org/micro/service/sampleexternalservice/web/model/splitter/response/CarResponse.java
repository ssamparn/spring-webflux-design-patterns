package org.micro.service.sampleexternalservice.web.model.splitter.response;

import lombok.Builder;
import lombok.Data;
import org.micro.service.sampleexternalservice.web.model.splitter.Category;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class CarResponse {
    private UUID reservationId;
    private String city;
    private LocalDate pickupTime;
    private LocalDate dropTime;
    private Category category;
    private Integer price;
}
