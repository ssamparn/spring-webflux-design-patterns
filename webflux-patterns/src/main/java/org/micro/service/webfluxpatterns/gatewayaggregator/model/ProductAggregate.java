package org.micro.service.webfluxpatterns.gatewayaggregator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ProductAggregate {
    private Integer productId;
    private String productCategory;
    private String productDescription;
    private Price price;
    private List<ReviewResponse> reviews;
}
