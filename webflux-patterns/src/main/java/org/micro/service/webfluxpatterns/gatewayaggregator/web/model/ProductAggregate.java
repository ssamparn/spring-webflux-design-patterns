package org.micro.service.webfluxpatterns.gatewayaggregator.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.micro.service.webfluxpatterns.gatewayaggregator.model.Price;
import org.micro.service.webfluxpatterns.gatewayaggregator.model.ReviewResponse;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ProductAggregate {
    private Integer productId;
    private String productCategory;
    private String productDescription;
    private Price productPrice;
    private List<ReviewResponse> reviews;
}
