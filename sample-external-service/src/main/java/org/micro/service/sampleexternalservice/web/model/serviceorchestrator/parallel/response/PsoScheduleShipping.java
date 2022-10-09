package org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PsoScheduleShipping {
    private String orderId;
    private Integer quantity;
    private String status;
    private String expectedDelivery;
    private PsoUser.Address address;
}
