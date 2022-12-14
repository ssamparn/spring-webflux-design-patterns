package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class PsoInventoryRequest {
    private UUID orderId;
    private Integer productId;
    private Integer quantity;
}
