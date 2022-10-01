package org.micro.service.webfluxpatterns.scattergather.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class FlightResult {
    private String airLine;
    private String source;
    private String destination;
    private double price;
    private LocalDate date;
}
