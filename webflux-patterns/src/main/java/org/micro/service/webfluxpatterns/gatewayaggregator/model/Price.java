package org.micro.service.webfluxpatterns.gatewayaggregator.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Price {
    private Integer listPrice;
    private Integer discount;
    private Integer discountedPrice;
    private Integer amountSaved;
    private String endDate;
}
