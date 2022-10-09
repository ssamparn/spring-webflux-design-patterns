package org.micro.service.webfluxpatterns.serviceorchestrator.parallel.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class PsoAddress {
    private String streetName;
    private String cityName;
    private String state;
    private String zipCode;
}
