package org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PsoRefundPayment {
    private Integer userId;
    private String userName;
    private Integer balance;
    private String status;
}
