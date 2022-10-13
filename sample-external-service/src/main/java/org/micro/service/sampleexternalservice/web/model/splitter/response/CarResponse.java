package org.micro.service.sampleexternalservice.web.model.splitter.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.micro.service.sampleexternalservice.web.model.splitter.Category;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor(staticName = "create")
public class CarResponse {
    private UUID reservationId;
    private String city;
    private LocalDate pickupDate;
    private LocalDate dropDate;
    private Category category;
    private Integer price;
}
