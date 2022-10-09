package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class SsoPaymentResponse {
    private UUID paymentId;
    private Integer userId;
    private String userName;
    private Integer balance;
    private SsoStatus ssoStatus;
}
