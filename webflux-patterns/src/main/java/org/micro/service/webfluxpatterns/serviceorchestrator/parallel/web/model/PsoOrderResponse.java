package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.PsoAddress;
import org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response.PsoStatus;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class PsoOrderResponse {
    private Integer userId;
    private Integer productId;
    private UUID orderId;
    private PsoStatus psoStatus;
    private PsoAddress psoAddress;
    private String expectedDeliveryDate;
}