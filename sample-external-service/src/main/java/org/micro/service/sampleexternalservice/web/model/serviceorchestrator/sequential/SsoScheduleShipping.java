package org.micro.service.sampleexternalservice.web.model.serviceorchestrator.sequential;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SsoScheduleShipping {
    private UUID shippingId;
    private Integer quantity;
    private String status;
    private String expectedDelivery;
    private SsoUser.Address address;
}
