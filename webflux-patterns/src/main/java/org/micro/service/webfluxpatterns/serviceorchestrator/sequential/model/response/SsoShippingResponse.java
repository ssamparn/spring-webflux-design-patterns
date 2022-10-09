package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class SsoShippingResponse {
    private UUID shippingId;
    private Integer quantity;
    private SsoStatus psoStatus;
    private String expectedDelivery;
    private SsoAddress ssoAddress;
}
