package org.micro.service.webfluxpatterns.gatewayaggregator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Integer productId;
    private String productCategory;
    private String productDescription;
    private Integer productPrice;
}
