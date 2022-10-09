package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class SsoShippingRequest {
    private Integer quantity;
    private Integer userId;
    private UUID orderId;
}
