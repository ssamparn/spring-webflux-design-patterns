package org.micro.service.sampleexternalservice.web.model.serviceorchestrator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleShipping {
    private String orderId;
    private Integer quantity;
    private String status;
    private String expectedDelivery;
    private User.Address address;
}
