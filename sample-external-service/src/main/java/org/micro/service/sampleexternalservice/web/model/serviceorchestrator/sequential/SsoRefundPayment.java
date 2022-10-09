package org.micro.service.sampleexternalservice.web.model.serviceorchestrator.sequential;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SsoRefundPayment {
    private Integer userId;
    private String userName;
    private Integer balance;
    private String status;
}
