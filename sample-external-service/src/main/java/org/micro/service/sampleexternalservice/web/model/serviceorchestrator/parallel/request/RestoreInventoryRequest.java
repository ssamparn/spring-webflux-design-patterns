package org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestoreInventoryRequest {
    private String orderId;
    private Integer productId;
    private Integer quantity;
}
