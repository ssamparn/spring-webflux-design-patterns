package org.micro.service.sampleexternalservice.web.model.serviceorchestrator.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelShippingRequest {
    private String orderId;
    private Integer quantity;
    private Integer userId;
}
