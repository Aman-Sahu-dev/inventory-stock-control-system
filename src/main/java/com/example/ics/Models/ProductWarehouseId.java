package com.example.ics.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ProductWarehouseId implements Serializable {
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "warehouse_id")
    private Long warehouseId;
}
