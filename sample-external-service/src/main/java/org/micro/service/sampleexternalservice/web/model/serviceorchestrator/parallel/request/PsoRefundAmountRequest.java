package org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PsoRefundAmountRequest {
    private Integer userId;
    private String orderId;
    private Integer amount;
}
