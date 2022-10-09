package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class SsoInventoryResponse {
    private Integer productId;
    private Integer quantity;
    private Integer remainingQuantity;
    private SsoStatus ssoStatus;
}
