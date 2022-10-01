package org.micro.service.webfluxpatterns.gatewayaggregator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
@Builder
public class PromotionResponse {
    private Integer productId;
    private String promotionCode;
    private String productType;
    private Integer discount;
    private String endDate;
}
