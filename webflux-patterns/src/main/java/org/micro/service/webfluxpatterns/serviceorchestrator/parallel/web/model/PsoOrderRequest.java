package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class PsoOrderRequest {
    private Integer userId;
    private Integer productId;
    private Integer quantity;
}
