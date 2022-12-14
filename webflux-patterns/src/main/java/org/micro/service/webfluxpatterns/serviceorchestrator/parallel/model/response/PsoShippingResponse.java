package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class PsoShippingResponse {
    private UUID orderId;
    private Integer quantity;
    private PsoStatus psoStatus;
    private String expectedDelivery;
    private PsoAddress psoAddress;

}
