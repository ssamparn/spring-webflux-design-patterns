package org.micro.service.sampleexternalservice.web.model.serviceorchestrator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestoreInventory {
    private Integer productId;
    private Integer quantity;
    private Integer remainingQuantity;
    private String status;
}