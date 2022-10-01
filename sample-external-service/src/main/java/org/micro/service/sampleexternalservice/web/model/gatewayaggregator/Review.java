package org.micro.service.sampleexternalservice.web.model.gatewayaggregator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    private Integer productId;
    private String userName;
    private String comment;
    private Integer rating;
}
