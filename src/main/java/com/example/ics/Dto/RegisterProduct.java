package com.example.ics.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class RegisterProduct {
    @NotEmpty
    private String name;
    @NotEmpty
    private String sku;
    private String description;
    @NotEmpty
    private BigDecimal price;

}
