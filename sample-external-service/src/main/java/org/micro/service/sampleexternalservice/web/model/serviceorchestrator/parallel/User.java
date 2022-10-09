package org.micro.service.sampleexternalservice.web.model.serviceorchestrator.parallel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer userId;
    private String userName;
    private Integer balance;
    private Address address;

    @Data
    @AllArgsConstructor(staticName = "create")
    public static class Address {
        private String street;
        private String city;
        private String state;
        private String zipCode;
    }
}
