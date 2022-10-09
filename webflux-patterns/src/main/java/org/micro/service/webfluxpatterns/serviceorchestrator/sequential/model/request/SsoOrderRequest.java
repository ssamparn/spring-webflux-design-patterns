package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class SsoOrderRequest {
    private Integer userId;
    private Integer productId;
    private Integer quantity;
}
