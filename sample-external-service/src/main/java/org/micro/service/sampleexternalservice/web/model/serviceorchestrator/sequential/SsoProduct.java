package org.micro.service.sampleexternalservice.web.model.serviceorchestrator.sequential;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SsoProduct {
    private Integer productId;
    private String productCategory;
    private String productDescription;
    private Integer productPrice;
}
