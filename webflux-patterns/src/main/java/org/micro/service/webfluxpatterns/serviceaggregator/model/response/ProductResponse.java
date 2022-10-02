package org.micro.service.webfluxpatterns.serviceaggregator.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ProductResponse {
    private Integer productId;
    private String productCategory;
    private String productDescription;
    private Integer price;
}
