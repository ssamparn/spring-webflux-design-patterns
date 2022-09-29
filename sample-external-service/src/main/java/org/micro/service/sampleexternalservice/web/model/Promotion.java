package org.micro.service.sampleexternalservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Promotion {
    private int promotionId;
    private String promotionCode;
    private String productType;
    private int discount;
    private String endDate;
}
