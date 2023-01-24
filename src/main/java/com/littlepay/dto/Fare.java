package com.littlepay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fare {
    private String source;
    private String destination;
    private BigDecimal cost;
    private String currency;
    private BigDecimal multiplier;
}
