package com.example.ics.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String sku;
    private String description;
    private BigDecimal price;
    private String status;
    private LocalDateTime createdAt;
}
