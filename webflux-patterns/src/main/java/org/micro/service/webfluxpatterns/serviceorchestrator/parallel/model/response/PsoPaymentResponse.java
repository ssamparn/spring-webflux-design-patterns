package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class PsoPaymentResponse {
    private Integer userId;
    private String userName;
    private Integer balance;
    private PsoStatus psoStatus;
}
