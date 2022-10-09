package org.micro.service.sampleexternalservice.web.model.serviceorchestrator.sequential.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SsoDeductPayment {
    private UUID paymentId;
    private Integer userId;
    private String userName;
    private Integer balance;
    private String status;
}
