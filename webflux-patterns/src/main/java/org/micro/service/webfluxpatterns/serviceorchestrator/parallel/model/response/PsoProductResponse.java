package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class PsoProductResponse {
    private Integer productId;
    private String productCategory;
    private String productDescription;
    private Integer productPrice;
}
