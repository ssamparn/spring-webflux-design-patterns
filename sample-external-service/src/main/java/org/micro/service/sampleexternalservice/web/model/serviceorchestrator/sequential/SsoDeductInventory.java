package org.micro.service.sampleexternalservice.web.model.serviceorchestrator.sequential;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SsoDeductInventory {
    private UUID inventoryId;
    private Integer productId;
    private Integer quantity;
    private Integer remainingQuantity;
    private String status;
}
