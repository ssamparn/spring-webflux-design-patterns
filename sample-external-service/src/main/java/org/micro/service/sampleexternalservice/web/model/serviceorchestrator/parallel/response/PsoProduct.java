package org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PsoProduct {
    private Integer productId;
    private String productCategory;
    private String productDescription;
    private Integer productPrice;
}
