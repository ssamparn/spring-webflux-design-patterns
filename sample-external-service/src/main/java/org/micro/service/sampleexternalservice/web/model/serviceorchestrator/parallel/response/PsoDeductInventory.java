package org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PsoDeductInventory {
    private Integer productId;
    private Integer quantity;
    private Integer remainingQuantity;
    private String status;
}
