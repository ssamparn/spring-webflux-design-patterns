package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response.SsoAddress;
import org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response.SsoStatus;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class SsoOrderResponse {
    private Integer userId;
    private Integer productId;
    private UUID orderId;
    private SsoStatus status;
    private SsoAddress address;
    private String expectedDeliveryDate;
}