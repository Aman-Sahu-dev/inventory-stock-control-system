package com.example.ics.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class StockMovementsResponse {
    private Long stockMovementId;
    private Long productId;
    private String productName;
    private Long warehouseId;
    private String warehouseName;
    private String movementsType;
    private int quantity;
    private String reason;
    private LocalDateTime createdAt;
}
