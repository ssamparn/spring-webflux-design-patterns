package org.micro.service.webfluxpatterns.serviceorchestrator.sequential.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class SsoPaymentResponse {
    private Integer userId;
    private String userName;
    private Integer balance;
    private SsoStatus ssoStatus;
}
