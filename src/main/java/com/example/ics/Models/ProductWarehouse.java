package com.example.ics.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "product_warehouse")
public class ProductWarehouse {
    @EmbeddedId
    private ProductWarehouseId id;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("warehouseId")
    @JoinColumn
    private Warehouse warehouse;
    @Column(name = "current_stock",nullable = false)
    @Builder.Default
    private int currentStock = 0;

    @Version
    private Long version;

    @UpdateTimestamp
    private LocalDateTime updated_at;
}
