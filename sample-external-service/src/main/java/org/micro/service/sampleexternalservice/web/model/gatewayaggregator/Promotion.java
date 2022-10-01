package org.micro.service.sampleexternalservice.web.model.gatewayaggregator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Promotion {
    private Integer productId;
    private String promotionCode;
    private String productType;
    private Integer discount;
    private String endDate;
}
