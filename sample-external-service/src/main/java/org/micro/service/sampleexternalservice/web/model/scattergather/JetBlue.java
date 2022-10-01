package org.micro.service.sampleexternalservice.web.model.scattergather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class JetBlue {
    private double price;
    private LocalDate date;
}
