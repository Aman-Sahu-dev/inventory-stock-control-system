package com.example.ics.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockMovementRequest {
    private Long productId;
    private Long warehouseId;
    private int quantity;
    private String reason;
}
