package org.micro.service.sampleexternalservice.web.model.serviceorchestrator.sequential.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SsoScheduleShippingRequest {
    private UUID inventoryId;
    private Integer quantity;
    private Integer userId;
}
