package org.micro.service.sampleexternalservice.web.model.scattergather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class Frontier {
    private String airLine;
    private String source;
    private String destination;
    private double price;
    private LocalDate date;
}
