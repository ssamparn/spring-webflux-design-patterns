package org.micro.service.sampleexternalservice.web.model.serviceorchestrator.sequential.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SsoCancelShippingRequest {
    private String orderId;
    private Integer quantity;
    private Integer userId;
}
