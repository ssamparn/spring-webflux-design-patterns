package org.micro.service.webfluxpatterns.gatewayaggregator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {
    private Integer productId;
    private String userName;
    private String comment;
    private Integer rating;
}
