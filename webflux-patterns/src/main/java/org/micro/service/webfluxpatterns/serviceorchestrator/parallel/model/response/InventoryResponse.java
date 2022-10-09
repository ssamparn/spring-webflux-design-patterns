package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class InventoryResponse {
    private Integer productId;
    private Integer quantity;
    private Integer remainingQuantity;
    private Status status;
}
