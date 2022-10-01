package org.micro.service.sampleexternalservice.web.model.scattergather.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FrontierRequest {
    private String source;
    private String destination;
}
