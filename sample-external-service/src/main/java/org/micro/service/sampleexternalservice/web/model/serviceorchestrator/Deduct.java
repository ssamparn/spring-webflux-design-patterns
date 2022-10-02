package org.micro.service.sampleexternalservice.web.model.serviceorchestrator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Deduct {
    private Integer userId;
    private String userName;
    private Integer balance;
    private String status;
}
