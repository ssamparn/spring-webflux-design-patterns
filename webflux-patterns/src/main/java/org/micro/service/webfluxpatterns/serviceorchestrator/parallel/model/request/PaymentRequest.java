package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class PaymentRequest {
    private Integer userId;
    private Integer amount;
    private UUID orderId;
}
