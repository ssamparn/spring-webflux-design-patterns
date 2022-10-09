package org.micro.service.sampleexternalservice.web.model.serviceorchestrator.sequential.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SsoRestoreInventoryRequest {
    private String orderId;
    private Integer productId;
    private Integer quantity;
}
